package com.kuaishou.business.portal.identity;

import java.util.List;

import lombok.Data;

/**
 * @author yangtianwen
 * @date 2022-04-03
 */
@Data
public class BizIdentityConfigDO {

    private String domainCode;
    private String domainName;

    private Object domainObject;
    private List<BizIdentityDO> bizIdentities;
}
