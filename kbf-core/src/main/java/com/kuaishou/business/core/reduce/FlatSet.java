package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Sets;

/**
 * @author liuzhuo
 * Created on 2023-03-30 下午10:20
 */
class FlatSet<T> extends Reducer<List<T>, Set<T>> {

    private final Predicate<List<T>> predicate;

    public FlatSet(Predicate<List<T>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Set<T> reduce(Collection<List<T>> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Sets.newHashSet();
        }
        Set<T> r = Sets.newHashSet();

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
