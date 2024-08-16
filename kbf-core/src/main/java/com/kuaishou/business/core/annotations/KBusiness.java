package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.identity.manage.BusinessItem;

/**
 * @author liuzhuo
 * Created on 2023-03-26 下午10:58
 * 垂直业务
 *
 * {@link BusinessItem 垂直业务标准能力}
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KBusiness {

    /**
     * 名称
     */
    String name() default "";

    /**
     * 编码
     */
    String code() default "";

}
