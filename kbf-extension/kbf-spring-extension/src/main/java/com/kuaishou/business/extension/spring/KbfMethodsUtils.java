package com.kuaishou.business.extension.spring;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtAction;
import com.kuaishou.business.core.function.ExtCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-29 下午10:01
 * 获取即将执行的方法名
 */
@Slf4j
public class KbfMethodsUtils {

    public static <Ext extends ExtPoint> String getMethodFromCallback(
            Class<Ext> clazz, ExtCallback<Ext, ?> extMethod) {
        String[] method0 = {null};
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, params, methodProxy) -> {
            method0[0] = method.getName();
            return null;
        });
        Ext ext = (Ext) enhancer.create();
        extMethod.apply(ext);
        return method0[0];
    }

    public static <Ext extends ExtPoint> String getMethodFromAction(
            Class<Ext> clazz, ExtAction<Ext> extMethod) {
        String[] method0 = {null};
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (o, method, params, methodProxy) -> {
            method0[0] = method.getName();
            return null;
        });
        Ext ext = (Ext) enhancer.create();
        extMethod.accept(ext);
        return method0[0];
    }

}
