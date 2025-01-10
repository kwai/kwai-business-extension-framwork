package com.kuaishou.business.extension.dimension;

import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.extension.engine.ExtActuator;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午8:44
 */
public interface BizSceneExtActuator extends ExtActuator {

    default <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Long resourceId, Reducer<?, Void> reducer) {
        executeVoid(extClz, extMethod, defaultMethod, resourceId, reducer, null);
    }

    <Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Long resourceId, Reducer<?, Void> reducer, P request);

    default <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Long resourceId, Reducer<T, R> reducer) {
        return execute(extClz, extMethod, defaultMethod, resourceId, reducer, null);
    }

    <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Long resourceId, Reducer<T, R> reducer, P request);
}
