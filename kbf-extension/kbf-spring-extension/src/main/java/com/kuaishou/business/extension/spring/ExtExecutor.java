package com.kuaishou.business.extension.spring;

import java.util.function.Supplier;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.reduce.Reducer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 扩展点执行器
 */
@Slf4j
public class ExtExecutor {

    public static <Ext extends ExtPoint> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Reducer<Void, Void> reducer) {
        executeVoid(extClz, extMethod, defaultMethod, reducer, null);
    }

    /**
     * 扩展点执行
     *
     * @param extClz 扩展点类
     * @param extMethod 扩展点方法
     * @param defaultMethod 默认方法
     * @param request 身份识别参数
     *
     */
    public static <Ext extends ExtPoint, P> void executeVoid(Class<Ext> extClz, ExtAction<Ext> extMethod, Runnable defaultMethod, Reducer<Void, Void> reducer, P request) {
        ExtUtils.extActuator.executeVoid(extClz, extMethod, defaultMethod, reducer, request);
    }


    public static <Ext extends ExtPoint, T, R> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer) {
        return execute(extClz, extMethod, defaultMethod, reducer, null);
    }

    /**
     * 扩展点执行
     *
     * @param extClz 扩展点类
     * @param extMethod 扩展点方法
     * @param defaultMethod 默认方法 -> 业务方指定 或 无匹配的扩展点时执行
     * @param request 身份识别参数
     *
     */
    public static <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod, Reducer<T, R> reducer, P request) {
        return ExtUtils.extActuator.execute(extClz, extMethod, defaultMethod, reducer, request);
    }

}
