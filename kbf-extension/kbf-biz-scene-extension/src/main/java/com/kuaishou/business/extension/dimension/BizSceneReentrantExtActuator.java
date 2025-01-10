package com.kuaishou.business.extension.dimension;

import java.util.List;
import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.extension.dimension.identity.filter.BizSceneTypeFilter;
import com.kuaishou.business.extension.spring.KbfMethodsUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 扩展点识别
 */
@Slf4j
public class BizSceneReentrantExtActuator implements BizSceneExtActuator {

	@Setter
	private BizSceneTypeFilter bizSceneTypeFilter;

	@Override
	public <Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod,
		Runnable defaultMethod, Reducer<Void, Void> reducer, P request) {
		execute(extClz, ext -> {
			extMethod.accept(ext);
			return null;
		}, () -> {
			defaultMethod.run();
			return null;
		}, reducer, request);
	}

    @Override
    public <Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Long resourceId, Reducer<?, Void> reducer, P request) {
		execute(extClz, ext -> {
			extMethod.accept(ext);
			return null;
		}, () -> {
			defaultMethod.run();
			return null;
		}, resourceId, reducer, request);
    }

    @Override
    public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer, P request) {
		BizSceneExecutor bizSceneExecutor = new BizSceneExecutor();
		String methodName = KbfMethodsUtils.getMethodFromCallback(extClz, extMethod);
		bizSceneExecutor.setSupportBizSceneTypes(getExtMethodSupportTypes(extClz, methodName));
		return bizSceneExecutor.execute(extClz, methodName, extMethod, defaultMethod, reducer, request);
    }

    @Override
    public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Long resourceId, Reducer<T, R> reducer, P request) {
		BizSceneExecutor bizSceneExecutor = new BizSceneExecutor();
		String methodName = KbfMethodsUtils.getMethodFromCallback(extClz, extMethod);
		bizSceneExecutor.setSupportBizSceneTypes(getExtMethodSupportTypes(extClz, methodName));
		bizSceneExecutor.setResourceId(resourceId);
		bizSceneExecutor.setIsResourceRequest(true);
		return bizSceneExecutor.execute(extClz, methodName, extMethod, defaultMethod, reducer, request);
	}

	private List<String> getExtMethodSupportTypes(Class clazz, String methodName) {
		List<String> types = bizSceneTypeFilter.getTypes(clazz, methodName);
		return types;
	}
}
