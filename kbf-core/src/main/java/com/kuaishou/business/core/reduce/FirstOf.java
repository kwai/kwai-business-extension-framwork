package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

/**
 * 1@author liuzhuo
 * Created on 2023-03-17 下午6:29
 * 第一个
 */
class FirstOf<T> extends Reducer<T, T> {

    private Predicate<T> predicate;

    public FirstOf(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    public FirstOf() {

    }

    @Override
    public T reduce(Collection<T> results) {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }

        for (T result : results) {
            if (null == predicate) {
                return result;
            } else if (predicate.test(result)) {
                return result;
            }
        }
        return null;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.FIRST;
    }
}
