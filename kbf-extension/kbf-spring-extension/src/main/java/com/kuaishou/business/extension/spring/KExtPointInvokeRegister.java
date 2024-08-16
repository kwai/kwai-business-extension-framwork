package com.kuaishou.business.extension.spring;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.ReflectionUtils;

import com.kuaishou.business.extension.spring.annotations.KExtPointInvoke;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KExtPointInvokeRegister implements BeanFactoryPostProcessor, EnvironmentAware, InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private static List<String> EXCLUDES = Arrays.asList("org.springframework");

	public static final String INVOKER_BEAN_NAME = "invoker";

	private Environment environment;

	private ConfigurableListableBeanFactory beanFactory;

	@Override
    @SneakyThrows
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanDefinitionRegistry) throws BeansException {
		String[] kExtPointScanPaths = environment.getProperty(ExtUtils.kExtPointScanPathConfig, String[].class, new String[0]);
		final String[] beanDefinitionNames = beanDefinitionRegistry.getBeanDefinitionNames();
		log.info("postProcessBeanDefinitionRegistry beanDefinitionNames size is {}, scanPath : {}", beanDefinitionNames.length, kExtPointScanPaths);

		final List<String> beans = Arrays.stream(beanDefinitionNames)
			.filter((beanDefinitionName) -> {
				if (ArrayUtils.isNotEmpty(kExtPointScanPaths)) {
					return Arrays.stream(kExtPointScanPaths).anyMatch(e -> StringUtils.containsIgnoreCase(beanDefinitionName, e));
				} else {
					return true;
				}
			}).filter(n -> EXCLUDES.stream().noneMatch(e -> StringUtils.containsIgnoreCase(n, e)))
			.filter(n -> {
					final BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(n);
					final String beanClassName = beanDefinition.getBeanClassName();
					final String factoryBeanName = beanDefinition.getFactoryBeanName();
					return EXCLUDES.stream().noneMatch(e -> StringUtils.containsIgnoreCase(beanClassName, e))
						&& EXCLUDES.stream().noneMatch(e -> StringUtils.containsIgnoreCase(factoryBeanName, e));
				}
			).collect(Collectors.toList());

        for (String bean : beans) {
            final BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(bean);
            final String beanClassName = beanDefinition.getBeanClassName();
            //不支持定义在config中的bean
            if (StringUtils.isBlank(beanClassName)) {
                continue;
            }
            try {
				Class<?> beanClass = Class.forName(beanClassName);
				ReflectionUtils.doWithFields(beanClass, field -> {
					if (field.isAnnotationPresent(KExtPointInvoke.class)) {
						processField(field, beanDefinitionRegistry);
					}
				});
            } catch (NoClassDefFoundError noClassDefFoundError) {
                //ignore because maybe there is some bean project do not use
                log.warn("no class found error beanClassName is {}", beanClassName);
            } catch (Throwable t) {
                log.error("registerBean error beanClassName is {}", beanClassName, t);
            }
        }
    }

	private void processField(Field field, ConfigurableListableBeanFactory beanFactory) {
		KExtPointInvoke annotation = AnnotationUtils.getAnnotation(field, KExtPointInvoke.class);
		if (annotation != null) {
			Class<?> type = field.getType();
			String extPointBeanName = type.getName() + "_KBF_ExtPoint";
			if (!beanFactory.containsBean(extPointBeanName)) {
				BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ExtPointBean.class);
				beanDefinitionBuilder.addConstructorArgValue(type);
				beanDefinitionBuilder.addConstructorArgReference(INVOKER_BEAN_NAME);

				BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
				registry.registerBeanDefinition(extPointBeanName, beanDefinitionBuilder.getBeanDefinition());
			}
		}
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), field -> processField(bean, field));
		return pvs;
	}

	private void processField(Object bean, Field field) {
		KExtPointInvoke annotation = AnnotationUtils.getAnnotation(field, KExtPointInvoke.class);
		if (annotation != null) {
			Class<?> type = field.getType();
			String extPointBeanName = type.getName() + "_KBF_ExtPoint";
			Object extPointBean = beanFactory.getBean(extPointBeanName);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, bean, extPointBean);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException("KExtPointInvokeAnnotationBeanPostProcessor requires a ConfigurableListableBeanFactory");
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}
}
