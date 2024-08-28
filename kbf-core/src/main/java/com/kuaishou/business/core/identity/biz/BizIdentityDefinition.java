package com.kuaishou.business.core.identity.biz;

import com.kuaishou.business.core.identity.IdentityDefinition;
import com.kuaishou.business.core.identity.IdentityMatcher;
import com.kuaishou.business.core.identity.Match;

/**
 * 业务身份定义
 */
public interface BizIdentityDefinition<T, R extends Match> extends IdentityDefinition, IdentityMatcher<T, R> {

    /**
     * @return 当前支持的业务身份
     */
    String supportedBizCode();

    /**
     * @return 业务身份所对应的扫描路径
     */
    String scanPath();

}
