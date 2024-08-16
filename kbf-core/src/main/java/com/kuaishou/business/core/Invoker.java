package com.kuaishou.business.core;

import java.lang.reflect.Method;

import com.kuaishou.business.core.extpoint.ExtPoint;


public interface Invoker {
	<R, T extends ExtPoint> R invoke(String bizCode, Class<T> c, Method method, Object[] params, Object defaultInvoker) throws Throwable;

}
