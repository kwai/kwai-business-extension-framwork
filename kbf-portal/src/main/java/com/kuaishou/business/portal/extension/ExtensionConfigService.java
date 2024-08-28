package com.kuaishou.business.portal.extension;

import java.util.List;

/**
 * @author yangtianwen
 */
public interface ExtensionConfigService {

    void saveExtensionSpec(List<ExtensionGroupSpecDO> specList);
}
