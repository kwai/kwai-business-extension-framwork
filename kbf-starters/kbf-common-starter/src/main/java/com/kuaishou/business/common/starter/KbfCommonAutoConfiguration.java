package com.kuaishou.business.common.starter;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;
import com.kuaishou.business.common.starter.conifg.KbfCommonProperties;
import com.kuaishou.business.core.Invoker;
import com.kuaishou.business.core.annotations.KBusiness;
import com.kuaishou.business.core.annotations.KProduct;
import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.identity.biz.BizIdentityRecognizer;
import com.kuaishou.business.core.identity.biz.NormalBizIdentityDefinition;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.manage.SpecManager;
import com.kuaishou.business.core.identity.product.NormalProductIdentityDefinition;
import com.kuaishou.business.extension.engine.DefaultBizIdentityRecognizer;
import com.kuaishou.business.extension.engine.DefaultKSessionFactory;
import com.kuaishou.business.extension.engine.DefaultSpecManager;
import com.kuaishou.business.extension.engine.ExtActuator;
import com.kuaishou.business.extension.spring.ExtUtils;
import com.kuaishou.business.extension.spring.KExtPointInvokeRegister;
import com.kuaishou.business.extension.spring.KSessionAroundAspect;
import com.kuaishou.business.extension.spring.KbfSpringUtils;
import com.kuaishou.business.extension.spring.LocalInvoke;
import com.kuaishou.business.extension.spring.SimpleExtActuator;
import com.kuaishou.business.extension.spring.SpringExtPointRealizeCollector;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Created by wuhao on 2021/2/19.
 */
@Configuration
@Slf4j
@EnableConfigurationProperties(KbfCommonProperties.class)
@ConditionalOnProperty(name = "spring.kbf.common.enabled")
public class KbfCommonAutoConfiguration {


	public static final String INVOKER_BEAN_NAME = "invoker";

	@Bean
	public SpecManager specManager(List<NormalBizIdentityDefinition> bizIdentityDefinitions, List<NormalProductIdentityDefinition> productIdentityDefinitions) {
		log.info("[kbf] load the proto spec manager");
		DefaultSpecManager defaultSpecManager = new DefaultSpecManager();

		registerBusiness(bizIdentityDefinitions, defaultSpecManager);
		registerProduct(productIdentityDefinitions, defaultSpecManager);

		log.info("[kbf] spec manager load business spec : " + defaultSpecManager.getAllBusinessSpecs());
		log.info("[kbf] spec manager load product spec : " + defaultSpecManager.getAllProductSpecs());
		return defaultSpecManager;
	}


	private void registerBusiness(List<NormalBizIdentityDefinition> bizIdentityDefinitions,
		DefaultSpecManager defaultSpecManager) {
		for (NormalBizIdentityDefinition bizIdentity : bizIdentityDefinitions) {
			BusinessItem businessItem = getBusinessSpec(bizIdentity);
			defaultSpecManager.registerBusinessSpec(businessItem);
		}
	}

	private BusinessItem getBusinessSpec(NormalBizIdentityDefinition bizIdentity) {
		KBusiness annotation = ClassUtils.getUserClass(bizIdentity.getClass()).getAnnotation(KBusiness.class);
		if (Objects.isNull(annotation)) {
			String errMsg = "[kbf] business identity cannot find @Business annotation, business : " + bizIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		if (StringUtils.isBlank(annotation.name()) || StringUtils.isBlank(annotation.code())) {
			String errMsg = "[kbf] business identity @Business property invalid, business : " + bizIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		return new BusinessItem(annotation.name(), annotation.code(), bizIdentity);
	}

	private void registerProduct(List<NormalProductIdentityDefinition> productIdentityDefinitions,
		DefaultSpecManager defaultSpecManager) {
		for (NormalProductIdentityDefinition productIdentity : productIdentityDefinitions) {
			NormalProductItem productSpec = getProductSpec(productIdentity);
			defaultSpecManager.registerProductSpec(productSpec);
		}
	}

	private NormalProductItem getProductSpec(NormalProductIdentityDefinition productIdentity) {
		KProduct annotation = ClassUtils.getUserClass(productIdentity.getClass()).getAnnotation(KProduct.class);
		if (Objects.isNull(annotation)) {
			String errMsg = "[kbf] product identity cannot find @Product annotation, product : " + productIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		if (StringUtils.isBlank(annotation.name())) {
			String errMsg = "[kbf] product identity @Product property invalid, product : " + productIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		return new NormalProductItem(annotation.name(), annotation.code(), productIdentity);
	}

	@Bean
	public BizIdentityRecognizer bizIdentityRecognizer(SpecManager specManager) {
		DefaultBizIdentityRecognizer defaultBizIdentityRecognizer =
			new DefaultBizIdentityRecognizer(Lists.newArrayList(specManager.getAllBusinessSpecs()));
		return defaultBizIdentityRecognizer;
	}

	@Bean
	public DefaultKSessionFactory defaultKSessionFactory(BizIdentityRecognizer bizIdentityRecognizer,
		SpecManager specManager) {
		DefaultKSessionFactory defaultKSessionFactory = new DefaultKSessionFactory();
		defaultKSessionFactory.setBizIdentityRecognizer(bizIdentityRecognizer);
		defaultKSessionFactory.setSpecManager(specManager);
		return defaultKSessionFactory;
	}

	@Bean
	public ExtActuator extActuator() {
		return new SimpleExtActuator();
	}

	@Bean
	public SpringExtPointRealizeCollector extPointRealizeCollector(SpecManager specManager) {
		SpringExtPointRealizeCollector springExtPointRealizeCollector = new SpringExtPointRealizeCollector();
		springExtPointRealizeCollector.setSpecManager(specManager);
		return springExtPointRealizeCollector;
	}

	@Bean
	public ExtUtils extUtils(SpecManager specManager, ExtActuator extActuator, KbfCommonProperties kbfCommonProperties) {
		ExtUtils extUtils = new ExtUtils();
		extUtils.setExtActuator(extActuator);
		extUtils.setSpecManager(specManager);
		extUtils.setEnableExtGray(kbfCommonProperties.getEnableExtGrayscale());
		extUtils.setExtensionType(kbfCommonProperties.getExtensionType());
		extUtils.setRecognizeCommand(kbfCommonProperties.getRecognizeCommand());
		return extUtils;
	}

	@Bean
	public KbfSpringUtils kbfSpringUtils() {
		KbfSpringUtils kbfSpringUtils = new KbfSpringUtils();
		return kbfSpringUtils;
	}

	@Bean
	public KExtPointInvokeRegister KExtPointInvokeRegister() {
		return new KExtPointInvokeRegister();
	}

	@Bean
	public KSessionAroundAspect kSessionAroundAspect(DefaultKSessionFactory defaultKSessionFactory) {
		KSessionAroundAspect kSessionAroundAspect = new KSessionAroundAspect();
		kSessionAroundAspect.setkSessionFactory(defaultKSessionFactory);
		return kSessionAroundAspect;
	}

	@Bean
	public SimpleExtActuator simpleExtActuator() {
		return new SimpleExtActuator();
	}

	@Bean(INVOKER_BEAN_NAME)
	public Invoker invoke(SimpleExtActuator simpleExtActuator) {
		LocalInvoke localInvoke = new LocalInvoke();
		localInvoke.setSimpleExtActuator(simpleExtActuator);
		return localInvoke;
	}

}
