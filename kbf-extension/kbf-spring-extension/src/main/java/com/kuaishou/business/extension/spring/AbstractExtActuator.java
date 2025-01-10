package com.kuaishou.business.extension.spring;

import java.util.List;
import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.extension.engine.ExtActuator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 扩展点识别（全局扫描）
 */
@Slf4j
public abstract class AbstractExtActuator implements ExtActuator {

    @Override
	public <Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Reducer<Void, Void> reducer, P request) {
		execute(extClz, ext -> {
			extMethod.accept(ext);
			return null;
		}, () -> {
            defaultMethod.run();
			return null;
		}, reducer, request);
    }

    protected abstract List<? extends KbfRealizeItem> recognize(Object request);

    @Override
    public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer, P request) {
		SpringSimpleExecutor simpleExecutor = new SpringSimpleExecutor();
		String methodName = KbfMethodsUtils.getMethodFromCallback(extClz, extMethod);
		return simpleExecutor.execute(extClz, methodName, extMethod, defaultMethod, reducer, request);
    }

}
