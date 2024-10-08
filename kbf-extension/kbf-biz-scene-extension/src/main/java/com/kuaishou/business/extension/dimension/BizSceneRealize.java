package com.kuaishou.business.extension.dimension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kuaishou.business.core.constants.KbfConstants;
import com.kuaishou.business.core.identity.biz.BizIdentityDefinition;
import com.kuaishou.business.extension.dimension.identity.BizSceneIdentityDefinition;

/**
 * @author liuzhuo
 * Created on 2023-03-27 下午6:55
 * 扩展点方法实现
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BizSceneRealize {

    /**
     * 垂直业务(与扩展维度互斥)
     */
    Class<? extends BizIdentityDefinition> business() default BizIdentityDefinition.class;

    /**
     * 扩展维度
     */
    Class<? extends BizSceneIdentityDefinition>[] scenes() default {};

    /**
     * 扩展生效维度的匹配方式
     */
    MatchType matchType() default MatchType.ANY;

    /**
     * 扩展实例的优先级
     */
    int priority() default KbfConstants.DEFAULT_PRIORITY;
}
