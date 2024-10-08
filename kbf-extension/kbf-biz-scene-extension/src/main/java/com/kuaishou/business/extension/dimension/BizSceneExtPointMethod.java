package com.kuaishou.business.extension.dimension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.extension.dimension.identity.EffectScope;
import com.kuaishou.business.core.reduce.ReduceType;

/**
 * @author liuzhuo
 * Created on 2023-03-30 下午2:44
 * 扩展点方法定义
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BizSceneExtPointMethod {

    /**
     * 扩展点名称
     */
    String name() default "";

    /**
     * 扩展点支持的扩展维度
     */
    String[] scenesTypes();

    /**
     * 扩展实例的合并策略
     */
    ReduceType reduceType() default ReduceType.FIRST;

    /**
     * 所属的域服务
     */
    String belong();

    /**
     * 当前作用范围
     */
    EffectScope scope() default EffectScope.RESOURCE;
}
