package com.kuaishou.business.extension.spring;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kuaishou.business.core.annotations.KBizRealize;
import com.kuaishou.business.core.annotations.KExtPointMethod;
import com.kuaishou.business.core.annotations.Priority;
import com.kuaishou.business.core.constants.KbfConstants;
import com.kuaishou.business.core.exception.BizRealizeNotFoundException;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.extpoint.ExtPointMethodRealizeWrap;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.identity.manage.BaseProductItem;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.SpecManager;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 扩展点实例收集器
 */
@Slf4j
public class SpringExtPointRealizeCollector implements SmartInitializingSingleton {

    @Setter
    private SpecManager specManager;

    public void init() {
        Map<String, Object> bizBeanMap = KbfSpringUtils.getBeansWithAnnotation(KBizRealize.class);
        if (Objects.isNull(bizBeanMap)) {
            log.warn("[kbf] run the proto ext point realize collector, cannot find realize");
            return;
        }
        checkExtMethod(bizBeanMap);
        fillingExtPointRealize(bizBeanMap);
    }

    private void fillingExtPointRealize(Map<String, Object> bizBeanMap) {
        Map<String, ExtPointRealizeWrap> loadRealize = Maps.newHashMap();
        bizBeanMap.forEach((key, value) -> {
			Class<?> bizBeanClass = ClassUtils.getUserClass(value.getClass());
			KBizRealize bizRealize = bizBeanClass.getAnnotation(KBizRealize.class);
            ExtPointRealizeWrap extPointRealizeWrap = buildExtPointRealizeWrap(value);

            loadRealize.put(key, extPointRealizeWrap);

            for (String code : bizRealize.bizCode()) {
                BusinessItem businessItem = specManager.getBusinessItem(code);
                if (Objects.nonNull(businessItem)) {
                    businessItem.getExtPointRealizes().add(extPointRealizeWrap);
                    continue;
                }

                BaseProductItem productSpec = specManager.getProductItem(code);
                if (Objects.nonNull(productSpec)) {
                    productSpec.getExtPointRealizes().add(extPointRealizeWrap);
                    continue;
                }
                String errMsg =
                        "[kbf] cannot find the code of realize, code : " + code + " name : " + bizRealize.bizName()
                                + " instance : " + value;
                log.error(errMsg);
                throw new BizRealizeNotFoundException(code, errMsg);
            }
        });
		log.info("[kbf] item manager load business item : " + specManager.getAllBusinessItems());
		log.info("[kbf] item manager load product item : " + specManager.getAllProductItems());
        log.info("[kbf] run the proto ext point realize collector, all effect number is " + loadRealize.size()
                + ", realize is " + loadRealize);
    }

    private ExtPointRealizeWrap buildExtPointRealizeWrap(Object instance) {
		Class<?> instanceClass = ClassUtils.getUserClass(instance.getClass());
		ExtPointRealizeWrap extPointRealizeWrap = new ExtPointRealizeWrap().setObject(instance);
        extPointRealizeWrap.setExtPointClass(instanceClass);

        Priority classPriorityAnnotation = instanceClass.getAnnotation(Priority.class);
        if (Objects.nonNull(classPriorityAnnotation)) {
            extPointRealizeWrap.setPriority(classPriorityAnnotation.value());
        } else {
            extPointRealizeWrap.setPriority(KbfConstants.DEFAULT_PRIORITY);
        }
        Method[] declaredMethods = instanceClass.getDeclaredMethods();
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
			Class<?> productBean = ClassUtils.getUserClass(value.getClass());
			for (Class<?> extInterface : productBean.getInterfaces()) {
                if (!hasCheckExtClassSet.contains(extInterface)) {
                    boolean b = checkExtInterface(extInterface, hasCheckMethodSet);
                    invalid |= b;
                    hasCheckExtClassSet.add(extInterface);
                }
            }
        }

        if (invalid) {
            String errMsg = "[kbf] run the proto ext point realize collector occurs error, please check log";
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
            if (Objects.isNull(method.getAnnotation(KExtPointMethod.class))) {
                invalidMethodList.add(methodName);
            }
            hasCheckMethodSet.add(methodName);
        }
        if (CollectionUtils.isNotEmpty(invalidMethodList)) {
            String errMsg = "[kbf] the method" + invalidMethodList + " of extpoint{" + interfaceName
                    + "} cannot find @KExtPointMethod, please declare";
            log.error(errMsg);
            invalid = true;
        }

        return invalid;
    }


    @Override
    public void afterSingletonsInstantiated() {
        if (ExtUtils.PROTO.equals(ExtUtils.extensionType)) {
            init();
        }
    }
}
