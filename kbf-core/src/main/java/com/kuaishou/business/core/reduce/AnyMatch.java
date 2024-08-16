package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.function.Predicate;

import lombok.Getter;

/**
 * 1@author liuzhuo
 * Created on 2023-03-23 下午9:59
 * 任意匹配
 */
class AnyMatch<T> extends Reducer<T, Boolean> {

    @Getter
    private final Predicate<T> predicate;

    public AnyMatch(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }

    @Override
    public Boolean reduce(Collection<T> results) {
        for (T result : results) {
            if (predicate.test(result)) {
                return true;
            }
        }
        return false;
    }
}
