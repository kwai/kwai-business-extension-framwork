package com.kuaishou.business.portal.extension;

import java.util.List;

/**
 * @author yangtianwen
 * @date 2022-04-05
 */
public interface ExtensionConfigService {

    void saveExtensionSpec(List<ExtensionGroupSpecDO> specList);
}
