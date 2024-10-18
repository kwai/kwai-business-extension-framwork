package com.kuaishou.business.core.identity.product;

import com.kuaishou.business.core.identity.IdentityMatcher;
import com.kuaishou.business.core.identity.Match;

/**
 * 水平产品匹配
 */
public interface ProductIdentityMatcher<T> extends IdentityMatcher<T> {

    @Override
    Match match(T request);
}
