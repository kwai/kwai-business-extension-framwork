package com.kuaishou.business.portal.identity;

import java.util.List;

/**
 * @author yangtianwen
 * @date 2022-04-01
 */
public interface RemoteBizIdentityConfigLoader {

    List<RemoteBizIdentityMatcher> load(String domainCode);
}
