package com.kuaishou.business.portal.identity;

/**
 * @author yangtianwen
 * @date 2022-04-03
 */
public interface BizIdentityConfigService {

    BizIdentityConfigDO getByDomainCode(String domainCode);
}
