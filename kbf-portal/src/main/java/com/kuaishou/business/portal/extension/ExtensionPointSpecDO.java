package com.kuaishou.business.portal.extension;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yangtianwen
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExtensionPointSpecDO extends BaseSpecDO {

    private String groupClassName;
    private String implClassName;
}
