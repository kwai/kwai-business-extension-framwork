package com.kuaishou.business.portal.extension;

import java.util.List;

import lombok.Data;

/**
 * @author yangtianwen
 * @date 2022-04-04
 */
@Data
public class ExtensionGroupSpecDO {

    private String spiClassName;
    private List<String> spiApplyActivities;

    private List<ExtensionPointSpecDO> extensionPointSpecList;
}
