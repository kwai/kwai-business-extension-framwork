package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午6:58
 * 全部匹配
 */
class AllMatch<T> implements Reducer<T, Boolean> {

    private final Predicate<T> predicate;

    public AllMatch(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Boolean reduce(Collection<T> results) {
        for (T result : results) {
            if (!predicate.test(result)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
