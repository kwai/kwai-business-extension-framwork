package com.kuaishou.business.core.extpoint;

public interface ExtPointInvoker {

    <R> R invoke(Object[] args) throws Throwable;

}
