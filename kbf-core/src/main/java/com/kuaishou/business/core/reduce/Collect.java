package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * 1@author liuzhuo
 * Created on 2023-03-23 下午10:04
 * 全部满足条件的结果
 */
class Collect<T> implements Reducer<T, List<T>> {

    private final Predicate<T> predicate;

    public Collect(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public List<T> reduce(Collection<T> results) {
        List<T> r = Lists.newArrayList();
        if (CollectionUtils.isEmpty(results)) {
            return r;
        }

        for (T result : results) {
            if (predicate.test(result)) {
                r.add(result);
            }
        }

        return r;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
