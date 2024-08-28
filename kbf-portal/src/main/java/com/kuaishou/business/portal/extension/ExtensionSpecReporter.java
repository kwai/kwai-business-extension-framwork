package com.kuaishou.business.portal.extension;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangtianwen
 */
@Slf4j
public class ExtensionSpecReporter {

    @Autowired
    private ExtensionSpecScanner extensionSpecScanner;
    @Autowired
    private ExtensionConfigService extensionConfigService;

    // todo: async push and pull support
    public void doReport() {
        List<ExtensionGroupSpecDO> groupSpecDOList = extensionSpecScanner.scan();
        log.warn("extensionSpecScanner.scan, result:{}", groupSpecDOList);
        if (CollectionUtils.isEmpty(groupSpecDOList)) {
            return;
        }

        extensionConfigService.saveExtensionSpec(groupSpecDOList);
    }
}
