package com.kuaishou.business.extension.dimension.identity;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kuaishou.business.core.annotations.KBusiness;
import com.kuaishou.business.core.annotations.Priority;
import com.kuaishou.business.core.exception.BizRealizeNotFoundException;
import com.kuaishou.business.core.exception.IllegalExtPointException;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.extpoint.ExtPointMethodRealizeWrap;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.extension.dimension.BizScene;
import com.kuaishou.business.extension.dimension.BizSceneExtPointMethod;
import com.kuaishou.business.extension.dimension.BizSceneRealize;
import com.kuaishou.business.extension.dimension.MatchType;
import com.kuaishou.business.extension.dimension.identity.filter.BizSceneTypeFilter;
import com.kuaishou.business.extension.spring.KbfSpringUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-27 下午6:58
 *
 * bizScene扩展点实例收集器
 */
@Slf4j
public class BizSceneExtPointRealizeCollector implements SmartInitializingSingleton {

    @Setter
    private BizSceneSpecManager specManager;

	@Setter
	private BizSceneTypeFilter bizSceneTypeFilter;

    public void init() {
        Map<String, Object> realizationBeanMap = KbfSpringUtils.getBeansWithAnnotation(BizSceneRealize.class);
        if (Objects.isNull(realizationBeanMap)) {
            log.warn("run the biz-scene ext point realize collector, cannot find realize");
            return;
        }
        checkExtMethod(realizationBeanMap);
        registerExtPointRealize(realizationBeanMap);
		registerFilter(realizationBeanMap);
    }

	private void registerFilter(Map<String, Object> realizationBeanMap) {
		for (Object value : realizationBeanMap.values()) {
			for (Class<?> extInterface : value.getClass().getInterfaces()) {
				if (MapUtils.isNotEmpty(bizSceneTypeFilter.getExtTypes(extInterface))) {
					continue;
				}
				for (Method extMethod : extInterface.getMethods()) {
					BizSceneExtPointMethod bizSceneExtPointMethod =
						extMethod.getAnnotation(BizSceneExtPointMethod.class);
					bizSceneTypeFilter.register(extInterface, extMethod.getName(),
						Arrays.asList(bizSceneExtPointMethod.scenesTypes()));
				}
			}
		}
	}

    private void registerExtPointRealize(Map<String, Object> productBeanMap) {
        Map<String, ExtPointRealizeWrap> loadRealize = Maps.newHashMap();

        for (Entry<String, Object> dimensionEntry : productBeanMap.entrySet()) {
            Object value = dimensionEntry.getValue();

            BizSceneRealize bizRealize = value.getClass().getAnnotation(BizSceneRealize.class);
            ExtPointRealizeWrap extPointRealizeWrap = buildExtPointRealizeWrap(value, bizRealize.priority());
            loadRealize.put(dimensionEntry.getKey(), extPointRealizeWrap);

            // business
            if (!bizRealize.business().isInterface()) {
                KBusiness annotation = bizRealize.business().getAnnotation(KBusiness.class);
                BusinessItem businessItem = this.specManager.getBusinessSpec(annotation.code());
                businessItem.getExtPointRealizes().add(extPointRealizeWrap);
                continue;
            }

            fillingBizSceneExtPointRealize(bizRealize, extPointRealizeWrap);
        }
        log.info("[kbf] run the biz-scene ext point realize collector, all effect number is " + loadRealize.size()
                + ", realize is " + loadRealize);
    }

    public void fillingBizSceneExtPointRealize(BizSceneRealize bizRealize, ExtPointRealizeWrap extPointRealizeWrap) {
        if (bizRealize.scenes().length == 0) {
            String errMsg = "[kbf] realize biz-scenes length illegal, name : " + bizRealize.getClass().getName();
            log.error(errMsg);
            throw new IllegalExtPointException(errMsg);
        }

        if (MatchType.ALL.equals(bizRealize.matchType())) {
            List<String> dimensionCodeList = Arrays.stream(bizRealize.scenes())
                    .map(Class::getSimpleName)
                    .sorted()
                    .collect(Collectors.toList());
            String comboCode = String.join("-", dimensionCodeList);

            List<String> dimensionNameList = Arrays.stream(bizRealize.scenes())
                    .map(d -> d.getAnnotation(BizScene.class).name())
                    .sorted()
                    .collect(Collectors.toList());
            String comboName = String.join("-", dimensionNameList);

            BizSceneItem bizSceneSpec = specManager.getProductSpec(comboCode);
            if (Objects.isNull(bizSceneSpec)) {
                boolean isResource = dimensionCodeList.stream()
                        .map(c -> specManager.getProductSpec(c))
                        .anyMatch(p -> EffectScope.RESOURCE.equals(p.getEffectScope()));
                EffectScope effectScope = isResource ? EffectScope.RESOURCE : EffectScope.REQUEST;
                ComboBizSceneIdentityDefinition comboDimension =
                        new ComboBizSceneIdentityDefinition(dimensionCodeList);
                ComboBizSceneItem comboDimensionSpec = new ComboBizSceneItem(comboName,
                        comboCode, comboDimension, effectScope);
                comboDimensionSpec.getExtPointRealizes().add(extPointRealizeWrap);
                specManager.registerProductSpec(comboDimensionSpec);
            } else {
                bizSceneSpec.getExtPointRealizes().add(extPointRealizeWrap);
            }
        }
        if (MatchType.ANY.equals(bizRealize.matchType())) {
            Class<? extends BizSceneIdentityDefinition>[] dimensions = bizRealize.scenes();
            for (Class<? extends BizSceneIdentityDefinition> dimension : dimensions) {
                BizSceneItem bizSceneSpec = specManager.getProductSpec(dimension.getSimpleName());
                bizSceneSpec.getExtPointRealizes().add(extPointRealizeWrap);
            }
        }
    }

    private ExtPointRealizeWrap buildExtPointRealizeWrap(Object instance, Integer classPriority) {
        ExtPointRealizeWrap extPointRealizeWrap = new ExtPointRealizeWrap().setObject(instance);
        extPointRealizeWrap.setPriority(classPriority);
        extPointRealizeWrap.setExtPointClass(instance.getClass());
        Priority classPriorityAnnotation = instance.getClass().getAnnotation(Priority.class);
        if (Objects.nonNull(classPriorityAnnotation)) {
            extPointRealizeWrap.setPriority(classPriorityAnnotation.value());
        }
        Method[] declaredMethods = instance.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Priority annotation = method.getAnnotation(Priority.class);
            int priority = (annotation == null) ? extPointRealizeWrap.getPriority() : annotation.value();
            ExtPointMethodRealizeWrap extPointMethodRealizeWrap = new ExtPointMethodRealizeWrap(method);
            extPointMethodRealizeWrap.setPriority(priority);
            extPointRealizeWrap.getMethods().put(method.getName(), extPointMethodRealizeWrap);
        }
        return extPointRealizeWrap;
    }

    public void checkExtMethod(Map<String, Object> productBeanMap) {
        Set<Class<?>> hasCheckExtClassSet = Sets.newHashSet();
        Set<String> hasCheckMethodSet = Sets.newHashSet();

        boolean invalid = false;

        for (Object value : productBeanMap.values()) {
            for (Class<?> extInterface : value.getClass().getInterfaces()) {
                if (!hasCheckExtClassSet.contains(extInterface)) {
                    boolean b = checkExtInterface(extInterface, hasCheckMethodSet);
                    invalid |= b;
                    hasCheckExtClassSet.add(extInterface);
                }
            }
        }

        if (invalid) {
            String errMsg = "[kbf] run the biz-scene ext point realize collector occurs error, please check log";
            log.error(errMsg);
            throw new BizRealizeNotFoundException("", errMsg);
        }
    }

    private boolean checkExtInterface(Class<?> extInterface, Set<String> hasCheckMethodSet) {
        boolean invalid = false;

        String interfaceName = extInterface.getName();

        if (!ExtPoint.class.isAssignableFrom(extInterface)) {
            String errMsg = "[kbf] the extpoint{" + interfaceName
                    + "} do not extends {ExtPoint} interface, please declare";
            log.error(errMsg);
            invalid = true;
        }

        List<String> invalidMethodList = Lists.newArrayList();
        for (Method method : extInterface.getMethods()) {
            String methodName = method.getName();
            if (hasCheckMethodSet.contains(methodName)) {
                continue;
            }
            if (Objects.isNull(method.getAnnotation(BizSceneExtPointMethod.class))) {
                invalidMethodList.add(methodName);
            }
            hasCheckMethodSet.add(methodName);
        }
        if (CollectionUtils.isNotEmpty(invalidMethodList)) {
            String errMsg = "[kbf] the method" + invalidMethodList + " of extpoint{" + interfaceName
                    + "} cannot find @BizSceneExtPointMethod, please declare";
            log.error(errMsg);
            invalid = true;
        }

        return invalid;
	}

	@Override
	public void afterSingletonsInstantiated() {
		init();
	}
}
