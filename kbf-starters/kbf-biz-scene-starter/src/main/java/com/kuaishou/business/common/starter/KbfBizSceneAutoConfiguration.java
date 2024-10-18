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
import com.kuaishou.business.core.annotations.KBusiness;
import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.identity.biz.BizIdentityRecognizer;
import com.kuaishou.business.core.identity.biz.NormalBizIdentityDefinition;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.SpecManager;
import com.kuaishou.business.core.session.KSessionFactory;
import com.kuaishou.business.extension.dimension.BizScene;
import com.kuaishou.business.extension.dimension.BizSceneReentrantExtActuator;
import com.kuaishou.business.extension.dimension.identity.BizSceneExtPointRealizeCollector;
import com.kuaishou.business.extension.dimension.identity.BizSceneIdentityDefinition;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSpecManager;
import com.kuaishou.business.extension.dimension.identity.filter.BizSceneTypeFilter;
import com.kuaishou.business.extension.dimension.session.BizSceneKSessionFactory;
import com.kuaishou.business.extension.engine.DefaultBizIdentityRecognizer;
import com.kuaishou.business.extension.engine.ExtActuator;
import com.kuaishou.business.extension.spring.ExtUtils;
import com.kuaishou.business.extension.spring.KSessionAroundAspect;
import com.kuaishou.business.extension.spring.KbfSpringUtils;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@EnableConfigurationProperties(KbfCommonProperties.class)
@ConditionalOnProperty(name = "spring.kbf.common.enabled")
public class KbfBizSceneAutoConfiguration {

	@Bean
	public BizSceneTypeFilter bizSceneTypeFilter() {
		BizSceneTypeFilter bizSceneTypeFilter = new BizSceneTypeFilter();
		return bizSceneTypeFilter;
	}

	@Bean
	public SpecManager specManager(List<NormalBizIdentityDefinition> bizIdentityDefinitions, List<BizSceneIdentityDefinition> bizSceneIdentityDefinitions) {
		log.info("[kbf] load the bizScene item manager");
		BizSceneSpecManager bizSceneSpecManager = new BizSceneSpecManager();

		registerBusiness(bizIdentityDefinitions, bizSceneSpecManager);
		registerProduct(bizSceneIdentityDefinitions, bizSceneSpecManager);

		return bizSceneSpecManager;
	}


	private void registerBusiness(List<NormalBizIdentityDefinition> bizIdentityDefinitions,
		BizSceneSpecManager bizSceneSpecManager) {
		for (NormalBizIdentityDefinition bizIdentity : bizIdentityDefinitions) {
			BusinessItem businessItem = getBusinessSpec(bizIdentity);
			bizSceneSpecManager.registerBusinessItem(businessItem);
		}
	}

	private BusinessItem getBusinessSpec(NormalBizIdentityDefinition bizIdentity) {
		KBusiness annotation = ClassUtils.getUserClass(bizIdentity.getClass()).getAnnotation(KBusiness.class);
		if (Objects.isNull(annotation)) {
			String errMsg = "[kbf] business identity cannot find @KBusiness annotation, business : " + bizIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		if (StringUtils.isBlank(annotation.name()) || StringUtils.isBlank(annotation.code())) {
			String errMsg = "[kbf] business identity @KBusiness property invalid, business : " + bizIdentity;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		return new BusinessItem(annotation.name(), annotation.code(), bizIdentity);
	}

	private void registerProduct(List<BizSceneIdentityDefinition> productIdentityDefinitions,
		BizSceneSpecManager bizSceneSpecManager) {
		for (BizSceneIdentityDefinition bizSceneIdentityDefinition : productIdentityDefinitions) {
			BizSceneItem bizSceneSpec = getProductSpec(bizSceneIdentityDefinition);
			bizSceneSpecManager.registerProductItem(bizSceneSpec);
		}
	}

	private BizSceneItem getProductSpec(BizSceneIdentityDefinition dimension) {
		BizScene annotation = ClassUtils.getUserClass(dimension.getClass()).getAnnotation(BizScene.class);
		if (Objects.isNull(annotation)) {
			String errMsg = "[kbf] bizScene identity cannot find @BizScene annotation, product : " + dimension;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		if (StringUtils.isBlank(annotation.name())) {
			String errMsg = "[kbf] bizScene identity @BizScene property invalid, product : " + dimension;
			log.error(errMsg);
			throw new BizIdentityException(errMsg);
		}
		return new BizSceneItem(annotation.name(), dimension.getClass().getSimpleName(), dimension,
			annotation.type(), annotation.scope());
	}

	@Bean
	public BizIdentityRecognizer bizIdentityRecognizer(SpecManager<BizSceneItem> specManager) {
		DefaultBizIdentityRecognizer defaultBizIdentityRecognizer =
			new DefaultBizIdentityRecognizer(Lists.newArrayList(specManager.getAllBusinessItems()));
		return defaultBizIdentityRecognizer;
	}

	@Bean
	public KSessionFactory kSessionFactory(BizIdentityRecognizer bizIdentityRecognizer,
		SpecManager specManager) {
		BizSceneKSessionFactory bizSceneKSessionFactory = new BizSceneKSessionFactory();
		bizSceneKSessionFactory.setBizIdentityRecognizer(bizIdentityRecognizer);
		bizSceneKSessionFactory.setSpecManager(specManager);
		return bizSceneKSessionFactory;
	}

	@Bean
	public ExtActuator extActuator(BizSceneTypeFilter bizSceneTypeFilter) {
		BizSceneReentrantExtActuator bizSceneReentrantExtActuator = new BizSceneReentrantExtActuator();
		bizSceneReentrantExtActuator.setBizSceneTypeFilter(bizSceneTypeFilter);
		return bizSceneReentrantExtActuator;
	}

	@Bean
	public BizSceneExtPointRealizeCollector extPointRealizeCollector(SpecManager specManager,
		BizSceneTypeFilter bizSceneTypeFilter) {
		BizSceneExtPointRealizeCollector bizSceneExtPointRealizeCollector = new BizSceneExtPointRealizeCollector();
		bizSceneExtPointRealizeCollector.setSpecManager((BizSceneSpecManager) specManager);
		bizSceneExtPointRealizeCollector.setBizSceneTypeFilter(bizSceneTypeFilter);
		return bizSceneExtPointRealizeCollector;
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
	public KSessionAroundAspect kSessionAroundAspect(KSessionFactory kSessionFactory) {
		KSessionAroundAspect kSessionAroundAspect = new KSessionAroundAspect();
		kSessionAroundAspect.setkSessionFactory(kSessionFactory);
		return kSessionAroundAspect;
	}

}
