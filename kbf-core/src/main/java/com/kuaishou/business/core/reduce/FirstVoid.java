package com.kuaishou.business.core.reduce;

/**
 * @author liuzhuo
 * Created on 2023-04-03 下午7:43
 * 第一个(无返回)
 */
class FirstVoid<T> extends VoidReducer<T> {

    @Override
    public ReduceType reduceType() {
        return ReduceType.FIRST;
    }
}
