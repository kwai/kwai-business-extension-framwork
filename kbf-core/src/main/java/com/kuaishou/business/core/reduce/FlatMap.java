package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Maps;

/**
 * 1@author liuzhuo
 * Created on 2023-03-19 下午8:57
 */
class FlatMap<K, V> extends Reducer<Map<K, V>, Map<K, V>> {

    private final Predicate<Map<K, V>> predicate;

    public FlatMap(Predicate<Map<K, V>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public Map<K, V> reduce(Collection<Map<K, V>> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Maps.newHashMap();
        }

        Map<K, V> r = Maps.newHashMap();
        for (Map<K, V> result : results) {
            if (predicate.test(result)) {
                r.putAll(result);
            }
        }
        return r;
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
