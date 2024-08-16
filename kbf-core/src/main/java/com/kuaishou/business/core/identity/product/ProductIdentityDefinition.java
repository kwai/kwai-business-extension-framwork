package com.kuaishou.business.core.identity.product;

import com.kuaishou.business.core.identity.IdentityDefinition;
import com.kuaishou.business.core.identity.IdentityMatcher;
import com.kuaishou.business.core.identity.Match;

/**
 * 水平产品定义
 *
 * 需要结合{@link KProduct}或其扩展使用
 */
public interface ProductIdentityDefinition<T, R extends Match> extends IdentityMatcher<T, R>, IdentityDefinition {

}

