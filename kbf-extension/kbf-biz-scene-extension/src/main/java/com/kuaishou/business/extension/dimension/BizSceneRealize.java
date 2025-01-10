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
     * @return 垂直业务(与维度互斥)
     */
    Class<? extends BizIdentityDefinition> business() default BizIdentityDefinition.class;

    /**
     * @return 维度
     */
    Class<? extends BizSceneIdentityDefinition>[] scenes() default {};

    /**
     * @return 维度的匹配方式
     */
    MatchType matchType() default MatchType.ANY;

    /**
     * @return 扩展实例的优先级
     */
    int priority() default KbfConstants.DEFAULT_PRIORITY;
}
