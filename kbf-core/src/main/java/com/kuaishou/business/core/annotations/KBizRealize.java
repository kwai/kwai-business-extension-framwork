package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扩展点实例
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KBizRealize {

    /**
     * 业务编码
     */
    String[] bizCode();

    /**
     * 业务名称
     */
    String bizName() default "";

}
