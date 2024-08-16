package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.reduce.ReduceType;

/**
 * 扩展点方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KExtPointMethod {

    /**
     * 扩展点方法归属
     */
    String belong() default "";

    /**
     * 展示的名字
     */
    String displayName() default "";

    /**
     * 默认取class name
     */
    String uniqueName() default "";

    /**
     * 扩展实例的合并策略
     */
    ReduceType reduceType() default ReduceType.FIRST;

}