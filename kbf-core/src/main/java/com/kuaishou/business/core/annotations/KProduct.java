package com.kuaishou.business.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.identity.manage.NormalProductItem;

/**
 * @author liuzhuo
 * Created on 2023-03-25 下午5:31
 * 水平业务（产品）
 *
 * {@link NormalProductItem 水平产品标准能力}
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KProduct {

    /**
     * 名称
     */
    String name() default "";

    /**
     * 编码
     */
    String code() default "";

}
