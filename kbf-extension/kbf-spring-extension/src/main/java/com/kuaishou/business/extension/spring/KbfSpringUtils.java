package com.kuaishou.business.extension.spring;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.kuaishou.business.core.exception.ExtPointInitException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KbfSpringUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        KbfSpringUtils.context = applicationContext;
    }

    public static String[] getBeanNames() {
        return context.getBeanDefinitionNames();
    }

    public static <T> T getSpringBean(Class<T> beanClass) {
        T bean = null;
        try {
            if (null == context) {
                log.warn("spring application context is not injected");
                return null;
            }
            bean = (T) context.getBean(beanClass);

        } catch (BeansException e) {
            String beanName = StringUtils.uncapitalize(beanClass.getSimpleName());
            try {
                bean = getSpringBean(beanName);
            } catch (BeansException ex) {
                throw new ExtPointInitException(ex, beanClass.getCanonicalName());
            }
        }
        return bean;
    }

    public static <T> T getSpringBean(String beanName) {

        if (StringUtils.isBlank(beanName)) {
            throw new IllegalArgumentException("bean name is required");
        }

        if (null == context) {
            log.warn("spring application context is not injected");
            return null;
        }
        return (T) context.getBean(beanName);
    }

    public static <T> Map<String, T> getSpringBeansOfType(Class<T> requiredType) {
        if (Objects.isNull(requiredType)) {
            throw new IllegalArgumentException("requiredType is required");
        }
        if (null == context) {
            log.warn("spring application context is not injected");
            return null;
        }
        return context.getBeansOfType(requiredType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        if (Objects.isNull(annotationType)) {
            throw new IllegalArgumentException("requiredType is required");
        }
        if (null == context) {
            log.warn("spring application context is not injected");
            return null;
        }
        return context.getBeansWithAnnotation(annotationType);
    }
}
