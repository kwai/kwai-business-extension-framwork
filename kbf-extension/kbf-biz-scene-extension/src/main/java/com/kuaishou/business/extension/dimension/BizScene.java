package com.kuaishou.business.extension.dimension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.annotations.KProduct;
import com.kuaishou.business.extension.dimension.identity.EffectScope;

/**
 * @author liuzhuo
 * Created on 2023-03-21 下午1:57
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@KProduct
public @interface BizScene {

    /**
     * 维度类型
     */
    String type();

    /**
     * 维度名称
     */
    String name();

    /**
     * 作用域
     */
    EffectScope scope() default EffectScope.RESOURCE;
}
