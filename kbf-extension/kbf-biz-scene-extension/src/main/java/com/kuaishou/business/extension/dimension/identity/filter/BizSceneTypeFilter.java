package com.kuaishou.business.extension.dimension.identity.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import com.google.common.collect.Maps;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo07
 * Created on 2023-11-24
 */
@Slf4j
public class BizSceneTypeFilter {

	/**
	 * <ExtClass, <ExtMethod, SupportTypes>>
	 */
	private final Map<String, Map<String, List<String>>> bizSceneMethodSupportTypes = Maps.newHashMap();

	public void register(Class<?> extClazz, String methodName, List<String> types) {
		bizSceneMethodSupportTypes
			.computeIfAbsent(extClazz.getSimpleName(), ext -> new HashMap<>())
			.put(methodName, types);
	}

	public Map<String, List<String>> getExtTypes(Class<?> extClazz) {
		Map<String, List<String>> extMethodSupportTypes = bizSceneMethodSupportTypes.get(extClazz.getSimpleName());
		if (MapUtils.isEmpty(extMethodSupportTypes)) {
			return null;
		}
		return extMethodSupportTypes;
	}

	public List<String> getTypes(Class<?> extClazz, String methodName) {
		Map<String, List<String>> extMethodSupportTypes = bizSceneMethodSupportTypes.get(extClazz.getSimpleName());
		if (MapUtils.isEmpty(extMethodSupportTypes)) {
			return null;
		}

		List<String> supportTypes = extMethodSupportTypes.get(methodName);
		if (CollectionUtils.isEmpty(supportTypes)) {
			return null;
		}

		return supportTypes;
	}

	public <Ext extends ExtPoint> List<BizSceneSessionWrap> filter(Class<Ext> extClazz, String methodName,
		List<BizSceneSessionWrap> bizSceneSessionWraps) {
		Map<String, List<String>> extMethodSupportTypes = bizSceneMethodSupportTypes.get(extClazz.getSimpleName());
		if (MapUtils.isEmpty(extMethodSupportTypes)) {
			log.warn("[kbf] BizSceneTypeFilter cannot find clazz:{} method", extClazz.getSimpleName());
			return bizSceneSessionWraps;
		}

		List<String> supportTypes = extMethodSupportTypes.get(methodName);
		if (CollectionUtils.isEmpty(supportTypes)) {
			log.warn("[kbf] BizSceneTypeFilter cannot find clazz:{} method:{} supportTypes",
				extClazz.getSimpleName(), methodName);
			return bizSceneSessionWraps;
		}

		List<BizSceneSessionWrap> filterBizSceneSessionWrap = new ArrayList<>(bizSceneSessionWraps.size());
		for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
			BizSceneItem bizSceneItem = bizSceneSessionWrap.getBizSceneSpec();
			if (supportTypes.contains(bizSceneItem.getBizSceneType())) {
				filterBizSceneSessionWrap.add(bizSceneSessionWrap);
			}
		}

		return filterBizSceneSessionWrap;
	}
}
