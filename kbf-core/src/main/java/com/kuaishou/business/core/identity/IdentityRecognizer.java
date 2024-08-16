package com.kuaishou.business.core.identity;

@FunctionalInterface
public interface IdentityRecognizer<T, R> {

    R recognize(T request);

}
