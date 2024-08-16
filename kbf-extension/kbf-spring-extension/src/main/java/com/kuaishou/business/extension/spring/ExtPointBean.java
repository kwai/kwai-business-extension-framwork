package com.kuaishou.business.extension.spring;

import java.util.Optional;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Enhancer;

import com.kuaishou.business.core.Invoker;
import com.kuaishou.business.core.extpoint.ExtPoint;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class ExtPointBean<T extends ExtPoint> implements FactoryBean<T> {

    private final Class<T> extPoint;

    private final Invoker invoke;

    @Setter
    private T defaultImpl;


    @Override
	public T getObject() {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(extPoint);
        final ExtPointBeanProxyMethod<T> extPointBeanProxyMethod = new ExtPointBeanProxyMethod<>(extPoint, invoke);
        extPointBeanProxyMethod.setDefaultImpl(Optional.ofNullable(defaultImpl));
        enhancer.setCallback(extPointBeanProxyMethod);
        return (T) enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return extPoint;
    }

}
