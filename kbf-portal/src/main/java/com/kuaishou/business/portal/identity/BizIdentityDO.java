package com.kuaishou.business.portal.identity;

import lombok.Data;

/**
 * @author yangtianwen
 * @date 2022-04-04
 */
@Data
public class BizIdentityDO {

    private Long id;
    private String code;
    private String name;
    private String desc;
    private String type;

    private BizIdentityRuleDO condition;

    public static BizIdentityDO of(String code, String name) {
        BizIdentityDO identityDO = new BizIdentityDO();
        identityDO.setCode(code);
        identityDO.setName(name);
        return identityDO;
    }
}
