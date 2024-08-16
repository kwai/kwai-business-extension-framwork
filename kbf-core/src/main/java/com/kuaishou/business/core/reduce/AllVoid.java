package com.kuaishou.business.core.reduce;

/**
 * 1@author liuzhuo
 * Created on 2023-04-03 下午7:49
 * 全部(无返回)
 */
class AllVoid<T> extends VoidReducer<T> {

    @Override
    public ReduceType reduceType() {
        return ReduceType.ALL;
    }
}
