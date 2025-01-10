package com.kuaishou.business.core.identity.biz;

import com.kuaishou.business.core.identity.IdentityDefinition;
import com.kuaishou.business.core.identity.IdentityMatcher;

/**
 * 业务身份定义
 */
public interface BizIdentityDefinition<T> extends IdentityDefinition, IdentityMatcher<T> {

    /**
     * @return 当前支持的业务身份
     */
    String supportedBizCode();

    /**
     * @return 业务身份所对应的扫描路径
     */
    String scanPath();

}
