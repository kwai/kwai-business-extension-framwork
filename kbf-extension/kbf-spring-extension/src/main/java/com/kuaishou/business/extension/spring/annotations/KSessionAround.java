package com.kuaishou.business.extension.spring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.context.DefaultKBizContext;
import com.kuaishou.business.core.context.KBizContext;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KSessionAround {

    /**
     * 流程业务上下文类型
     */
    Class<? extends KBizContext> bizContextType() default DefaultKBizContext.class;

}
