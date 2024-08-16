package com.kuaishou.business.core.reduce;

import java.util.Collection;

/**
 * @author liuzhuo
 * Created on 2023-03-16 下午7:57
 */
public abstract class Reducer<T, R> {

    public abstract R reduce(Collection<T> results);

    public abstract ReduceType reduceType();
}
