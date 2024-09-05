package com.kuaishou.business.core.reduce;

import java.util.Collection;

/**
 * @author liuzhuo
 * Created on 2023-04-03 下午7:44
 */
abstract class VoidReducer<T> implements Reducer<T, Void> {

    @Override
    public Void reduce(Collection<T> results) {
        return null;
    }
}
