package com.kuaishou.business.core.identity;


import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.session.KSessionScope;

/**
 * 身份识别
 */
public interface IdentityMatcher<T> {

	Match match(T request);

    default KBizContext getKBizContext() {
        return KSessionScope.getCurrentContext();
    }
}
