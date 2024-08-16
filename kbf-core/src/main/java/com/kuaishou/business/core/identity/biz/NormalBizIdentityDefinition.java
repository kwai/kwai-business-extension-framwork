package com.kuaishou.business.core.identity.biz;

import com.kuaishou.business.core.identity.MatchResult;

/**
 * 业务身份定义
 */
public interface NormalBizIdentityDefinition<T> extends BizIdentityDefinition<T, MatchResult> {

    @Override
    MatchResult match(T request);

}
