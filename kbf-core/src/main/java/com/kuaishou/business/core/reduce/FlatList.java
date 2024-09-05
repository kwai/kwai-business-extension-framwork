package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * 1@author liuzhuo
 * Created on 2023-03-20 下午7:45
 */
class FlatList<T> implements Reducer<List<T>, List<T>> {

    private final Predicate<List<T>> predicate;

    public FlatList(Predicate<List<T>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public List<T> reduce(Collection<List<T>> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Lists.newArrayList();
        }
        List<T> r = Lists.newArrayList();

        for (List<T> result : results) {
            if (predicate.test(result)) {
                if (CollectionUtils.isNotEmpty(result)) {
                    r.addAll(result);
                }
            }
        }
        return r;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
