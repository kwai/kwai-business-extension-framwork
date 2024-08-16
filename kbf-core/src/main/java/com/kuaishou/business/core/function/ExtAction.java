package com.kuaishou.business.core.function;

import java.util.function.Consumer;

/**
 * @author liuzhuo
 * Created on 2023-04-03 下午10:16
 * 一次行为
 */
@FunctionalInterface
public interface ExtAction<T> extends Consumer<T> {
}
