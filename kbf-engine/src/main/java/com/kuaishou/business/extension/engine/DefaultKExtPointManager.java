package com.kuaishou.business.extension.engine;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.identity.product.ProductIdentityDefinition;
import com.kuaishou.business.core.exception.BizRealizeNotFoundException;
import com.kuaishou.business.core.extpoint.ExtPointManager;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.utils.ExtPointUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 支持动态识别以及上下文传递需求,ExtPointManager走代理执行的路径暂时搁置
 */
@Slf4j
public class DefaultKExtPointManager implements ExtPointManager {

    private final Map<String, List<ExtPointRealizeWrap>> bizCodeExtMap = new ConcurrentHashMap<>();

    private final Map<Class<? extends ProductIdentityDefinition>,
            List<ExtPointRealizeWrap>> productExtMap = new ConcurrentHashMap<>();

    @Override
    public void registerExtPointRealize(String bizCode, ExtPointRealizeWrap extPointRealizeWrap) {
        List<ExtPointRealizeWrap> extPointWraps = bizCodeExtMap.get(bizCode);
        if (Objects.isNull(extPointWraps)) {
            extPointWraps = Lists.newArrayList();
        }
        extPointWraps.add(extPointRealizeWrap);
        bizCodeExtMap.put(bizCode, extPointWraps);
    }

    @Override
    public void registerExtPointRealizeOfProducts(Class<? extends ProductIdentityDefinition> product,
            ExtPointRealizeWrap extPointRealizeWrap) {
        productExtMap
                .computeIfAbsent(product, l -> Lists.newArrayList())
                .add(extPointRealizeWrap);
    }

    @Override
    public <T> T getInstance(String bizCode, Class<T> clazz) {
        if (!ExtPointUtils.isExt(clazz) || StringUtils.isBlank(bizCode)) {
            throw new IllegalArgumentException();
        }
        final List<ExtPointRealizeWrap> extPointWraps = bizCodeExtMap.get(bizCode);
        if (CollectionUtils.isEmpty(extPointWraps)) {
            throw new BizRealizeNotFoundException(bizCode, clazz.getCanonicalName());
        }
        return (T) extPointWraps.stream().filter(
                e -> ClassUtils.isAssignable(e.getObject().getClass(), clazz)).map((e) -> e.getObject()
        ).findFirst().orElseThrow(() -> new BizRealizeNotFoundException(bizCode, clazz.getCanonicalName()));
    }

    @Override
    public <T> List<T> getInstanceList(String bizCode, List<Class<? extends ProductIdentityDefinition>> products,
            Class<T> clazz) {
        if (!ExtPointUtils.isExt(clazz)) {
            throw new IllegalArgumentException();
        }
        if (CollectionUtils.isEmpty(products)) {
            return Lists.newArrayList();
        }

        List<ExtPointRealizeWrap> extPointWraps = Lists.newArrayList();
        if (StringUtils.isNoneBlank(bizCode)) {
            extPointWraps.addAll(bizCodeExtMap.get(bizCode));
        }

        for (Class<? extends ProductIdentityDefinition> product : products) {
            Optional.ofNullable(productExtMap.get(product)).ifPresent(extPointWraps::addAll);
        }

        return (List<T>) extPointWraps.stream()
                .filter(e -> ClassUtils.isAssignable(e.getObject().getClass(), clazz))
                .sorted(Comparator.comparingInt(ExtPointRealizeWrap::getPriority))
                .map(e -> e.getObject())
                .collect(Collectors.toList());
    }

}
