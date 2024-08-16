package com.kuaishou.business.core.reduce;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午6:36
 * 全部
 */
class All<T> extends Reducer<T, List<T>>{

    public All() {

    }

    @Override
    public List<T> reduce(Collection<T> results) {
        if (CollectionUtils.isEmpty(results)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(results);
    }

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
