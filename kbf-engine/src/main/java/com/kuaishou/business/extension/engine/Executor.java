package com.kuaishou.business.extension.engine;

import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.reduce.ReduceType;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.core.session.KSessionScope;

/**
 * Executor for ext.
 * An instance of this class is not thread-safe.
 */
public abstract class Executor {

	public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, String methodName, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
		Reducer<T, R> reducer, P request) {
		if (!check(request)) {
			return reducer.reduce(Lists.newArrayList(defaultMethod.get()));
		}

		List<? extends KbfRealizeItem> currentProducts = recognize(request);

		List<KbfRealizeItem> kbfRealizeItemList = Lists.newArrayList();
		kbfRealizeItemList.add(KSessionScope.getCurrentBusiness());
		kbfRealizeItemList.addAll(currentProducts);

		List<Ext> instanceList = SimpleKExtPointManger.getInstance(kbfRealizeItemList, extClz, methodName);

		return execAndHandlerResults(extMethod, defaultMethod, reducer, instanceList);
	}

	protected static <Ext extends ExtPoint, T, R> R execAndHandlerResults(ExtCallback<Ext, T> extMethod,
		Supplier<T> defaultMethod, Reducer<T, R> reducer, List<Ext> instanceList) {
		if (CollectionUtils.isEmpty(instanceList)) {
			return reducer.reduce(Lists.newArrayList(defaultMethod.get()));
		}
		List<T> results = Lists.newArrayList();
		boolean reduceFirst = ReduceType.FIRST.equals(reducer.reduceType());
		for (Ext instance : instanceList) {
			T result = extMethod.apply(instance);
			if (reduceFirst) {
				if (reducer.predicate(result)) {
					return reducer.reduce(Lists.newArrayList(result));
				}
			} else {
				results.add(result);
			}
		}
		T defaultMethodResult = defaultMethod.get();
		results.add(defaultMethodResult);
		return reducer.reduce(results);
	}

	public abstract List<? extends KbfRealizeItem> recognize(Object request);

	public abstract boolean check(Object request);

}
