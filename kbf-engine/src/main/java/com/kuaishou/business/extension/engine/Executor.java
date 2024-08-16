package com.kuaishou.business.extension.engine;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.core.session.KSessionScope;

public abstract class Executor {

	public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, String methodName, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
		Reducer<T, R> reducer, P request) {
		if (!check(request)) {
			return reducer.reduce(Lists.newArrayList(defaultMethod.get()));
		}

		Set<NormalProductItem> currentProducts = recognize(request);

		List<KbfRealizeItem> kbfRealizeItemList = Lists.newArrayList();
		kbfRealizeItemList.add(KSessionScope.getCurrentBusiness());
		kbfRealizeItemList.addAll(currentProducts);

		List<Ext> instanceList = SimpleKExtPointManger.getInstance(kbfRealizeItemList, extClz, methodName);

		if (CollectionUtils.isEmpty(instanceList)) {
			return reducer.reduce(Lists.newArrayList(defaultMethod.get()));
		}
		switch (reducer.reduceType()) {
			case FIRST:
				//以扩展点优先
				return reducer.reduce(Lists.newArrayList(extMethod.apply(instanceList.get(0))));
			case ALL:
				List<T> results = instanceList.stream().map(extMethod).collect(Collectors.toList());
				T defaultMethodResult = defaultMethod.get();
				results.add(defaultMethodResult);
				return reducer.reduce(results);
			default:
				return null;
		}
	}

	public abstract <P> Set<NormalProductItem> recognize(P request);

	public abstract boolean check(Object request);

}
