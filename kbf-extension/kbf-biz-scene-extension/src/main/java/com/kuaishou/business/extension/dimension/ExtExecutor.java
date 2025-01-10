package com.kuaishou.business.extension.dimension;

import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.extension.spring.ExtUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 支持资源作用域/全局作用域的扩展点执行器
 */
@Slf4j
public class ExtExecutor {

	public static <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod,
		Runnable defaultMethod, Reducer<Void, Void> reducer) {
		executeVoid(extClz, extMethod, defaultMethod, reducer, null);
	}

	public static <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod,
		Runnable defaultMethod, Reducer<Void, Void> reducer, Object request) {
		ExtUtils.extActuator.executeVoid(extClz, extMethod, defaultMethod, reducer, request);
	}

    public static <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz,
            ExtAction<Ext> extMethod, Runnable defaultMethod,
             Long resourceId,  Reducer<?, Void> reducer) {
        executeVoid(extClz, extMethod, defaultMethod, resourceId, reducer, null);
    }

    public static <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz,
            ExtAction<Ext> extMethod, Runnable defaultMethod,
             Long resourceId,  Reducer<?, Void> reducer, Object request) {
        BizSceneExtActuator extActuator = (BizSceneExtActuator) ExtUtils.extActuator;
        extActuator.executeVoid(extClz, extMethod, defaultMethod, resourceId, reducer, request);
    }

    public static <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz,
            ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
             Reducer<T, R> reducer) {
        return execute(extClz, extMethod, defaultMethod, reducer, null);
    }

    public static <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz,
            ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
             Reducer<T, R> reducer, Object request) {
        return ExtUtils.extActuator.execute(extClz, extMethod, defaultMethod, reducer, request);
    }

    public static <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz,
            ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
             Long resourceId,  Reducer<T, R> reducer) {
        return execute(extClz, extMethod, defaultMethod, resourceId, reducer, null);
    }

    public static <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz,
            ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
             Long resourceId,  Reducer<T, R> reducer, Object request) {
        BizSceneExtActuator extActuator = (BizSceneExtActuator) ExtUtils.extActuator;
        return extActuator.execute(extClz, extMethod, defaultMethod, resourceId, reducer, request);
    }

}
