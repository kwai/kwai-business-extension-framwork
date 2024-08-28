package com.kuaishou.business.portal.identity;

import java.util.List;

/**
 * @author yangtianwen
 */
public interface RemoteBizIdentityConfigLoader {

    List<RemoteBizIdentityMatcher> load(String domainCode);
}
