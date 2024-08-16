package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.constants.KbfConstants;

/**
 * @author liuzhuo
 * Created on 2023-03-19 下午4:46
 * 定义扩展点实例的优先级,以大值优先,默认1000
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    int value() default KbfConstants.DEFAULT_PRIORITY;

}
