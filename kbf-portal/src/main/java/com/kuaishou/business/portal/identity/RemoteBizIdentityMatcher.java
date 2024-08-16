package com.kuaishou.business.portal.identity;

import com.kuaishou.business.core.KBusinessRequest;

/**
 * @author yangtianwen
 * @date 2022-04-01
 */
public interface RemoteBizIdentityMatcher<T extends KBusinessRequest> {

    int priority();

    String match(T request);
}