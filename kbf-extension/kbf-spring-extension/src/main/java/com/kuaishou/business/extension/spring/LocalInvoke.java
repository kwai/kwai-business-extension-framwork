package com.kuaishou.business.extension.spring;

import java.lang.reflect.Method;

import org.joor.Reflect;

import com.kuaishou.business.core.Invoker;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.reduce.Reducers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalInvoke implements Invoker {

    @Setter
	private SimpleExtActuator simpleExtActuator;

    @Override
	public <R, T extends ExtPoint> R invoke(String bizCode, Class<T> c, Method method, Object[] params, Object defaultInvoker) throws Throwable {
		return simpleExtActuator.execute(c, t -> Reflect.on(t).call(method.getName(), params).get(),
			() -> Reflect.on(defaultInvoker).call(method.getName(), params).get(), Reducers.first());
    }

}
