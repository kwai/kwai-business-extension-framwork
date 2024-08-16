package com.kuaishou.business.extension.engine;


import org.joor.Reflect;

import com.kuaishou.business.core.extpoint.ExtPointInvoker;

import lombok.Data;

@Data
public class DefaultExtPointInvoker implements ExtPointInvoker {

    private Object object;

    private String methodName;

    @Override
    public <R> R invoke(Object[] args) {
        return Reflect.on(object).call(methodName, args).get();
    }
}
