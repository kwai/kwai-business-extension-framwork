package com.kuaishou.business.portal.extension;

import java.util.List;

import lombok.Data;

/**
 * @author yangtianwen
 */
@Data
public class ExtensionGroupSpecDO {

    private String spiClassName;
    private List<String> spiApplyActivities;

    private List<ExtensionPointSpecDO> extensionPointSpecList;
}
