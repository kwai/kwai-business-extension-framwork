package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个业务活动
 *
 * {@link KStep 业务步骤}
 * {@link KExtPoint 扩展点组}
 * {@link KExtPointMethod 扩展点方法}
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KActivity {

    /**
     * 活动名称
     */
    String name();

    /**
     * 活动编码
     */
    String code();
}
