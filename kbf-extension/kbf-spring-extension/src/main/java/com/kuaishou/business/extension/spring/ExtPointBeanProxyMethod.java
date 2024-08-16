package com.kuaishou.business.extension.spring;

import java.lang.reflect.Method;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.kuaishou.business.core.Invoker;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.session.KSessionScope;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ExtPointBeanProxyMethod<T extends ExtPoint> implements MethodInterceptor {

    private final Class<T> extPoint;

    private final Invoker invoke;

    @Setter
    private Optional<T> defaultImpl = Optional.empty();

    public static final String[] EXCLUDE_METHODS = new String[]{
            "toString",
            "getClass",
            "hashCode",
            "equals",
            "clone",
            "wait",
            "notify",
            "notifyAll",
            "finalize",
            "wait",
    };

    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        if (ArrayUtils.contains(EXCLUDE_METHODS, method.getName())) {
            log.debug("method:{} exclude proxy", method.getName());
            return method.invoke(o, params);
        }
        final String currentBizCode = KSessionScope.getCurrentBizCode();
        return invoke.invoke(currentBizCode, extPoint, method, params, defaultImpl);
    }
}
