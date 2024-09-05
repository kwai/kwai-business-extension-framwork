package com.kuaishou.business.core.reduce;

import java.util.Collection;

/**
 * @author liuzhuo
 * Created on 2023-03-16 下午7:57
 */
public interface Reducer<T, R> {

	R reduce(Collection<T> results);

	default boolean predicate(T result) {
		return true;
	}

	ReduceType reduceType();
}
