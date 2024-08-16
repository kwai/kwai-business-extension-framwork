package com.kuaishou.business.core.identity.product;

import com.kuaishou.business.core.identity.IdentityMatcher;
import com.kuaishou.business.core.identity.Match;

/**
 * 水平产品匹配
 */
public interface ProductIdentityMatcher<T, M extends Match> extends IdentityMatcher<T, M> {

    @Override
    M match(T request);
}
