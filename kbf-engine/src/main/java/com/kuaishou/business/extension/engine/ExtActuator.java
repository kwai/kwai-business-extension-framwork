package com.kuaishou.business.extension.engine;

import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.reduce.Reducer;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午8:06
 */
public interface ExtActuator {

	default <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Reducer<Void, Void> reducer) {
        executeVoid(extClz, extMethod, defaultMethod, reducer, null);
    }

	<Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Reducer<Void, Void> reducer,
            P request);

    default <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer) {
        return execute(extClz, extMethod, defaultMethod, reducer, null);
    }

    <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer, P request);

}
