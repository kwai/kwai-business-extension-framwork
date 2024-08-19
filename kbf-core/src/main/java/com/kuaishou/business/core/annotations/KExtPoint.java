package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KExtPoint {

    /**
     * 扩展点组归属
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

}