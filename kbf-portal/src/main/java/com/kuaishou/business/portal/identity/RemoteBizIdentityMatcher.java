package com.kuaishou.business.portal.identity;

import com.kuaishou.business.core.KBusinessRequest;

/**
 * @author yangtianwen
 */
public interface RemoteBizIdentityMatcher<T extends KBusinessRequest> {

    int priority();

    String match(T request);
}
