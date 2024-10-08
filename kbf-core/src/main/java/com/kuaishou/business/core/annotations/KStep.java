package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个业务步骤
 *
 * {@link KExtPoint 扩展点组}
 * {@link KExtPointMethod 扩展点方法}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KStep {

    /**
     * @return 步骤名称
     */
    String name();

    /**
     * @return 步骤编码
     */
    String code();

    /**
     * @return 步骤归属
     */
    String belong();

    /**
     * @return 步骤顺序
     */
    int order() default Integer.MIN_VALUE;
}
