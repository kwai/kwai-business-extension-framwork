package com.kuaishou.business.portal.identity;

/**
 * @author yangtianwen
 */
public interface BizIdentityConfigService {

    BizIdentityConfigDO getByDomainCode(String domainCode);
}
