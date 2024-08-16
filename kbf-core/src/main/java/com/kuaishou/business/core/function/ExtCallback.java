package com.kuaishou.business.core.function;

import java.util.function.Function;

/**
 * @author liuzhuo
 * Created on 2023-03-21 上午1:11
 * 一次返回结果的行为
 */
@FunctionalInterface
public interface ExtCallback<T, R> extends Function<T, R> {

}
