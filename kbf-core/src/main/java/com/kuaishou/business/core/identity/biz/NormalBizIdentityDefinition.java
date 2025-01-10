package com.kuaishou.business.core.identity.biz;

import com.kuaishou.business.core.identity.Match;

/**
 * 业务身份定义
 */
public interface NormalBizIdentityDefinition<T> extends BizIdentityDefinition<T> {

    @Override
	Match match(T request);

}
