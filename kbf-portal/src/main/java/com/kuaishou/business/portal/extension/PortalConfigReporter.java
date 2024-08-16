package com.kuaishou.business.portal.extension;

import org.springframework.beans.factory.annotation.Autowired;

import com.kuaishou.business.portal.activity.ProcessConfigDO;
import com.kuaishou.business.portal.activity.RealizationConfigDO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yangtianwen
 * @date 2022-04-09
 */
@Slf4j
public class PortalConfigReporter {

    @Autowired
    private ProcessScanner processScanner;
    @Autowired
    private ProcessConfigService processConfigService;
    @Autowired
    private RealizationScanner realizationScanner;
    @Autowired
    private RealizationConfigService realizationConfigService;

    public void doReport(String domainCode) {

        try {
            ProcessConfigDO processConfigDO = processScanner.scan();
            processConfigService.save(processConfigDO);
        } catch (Throwable te) {
            log.error("report processConfigDO error, domainCode:{}", domainCode, te);
        }

        try {
            RealizationConfigDO realizationConfigDO = realizationScanner.scan();
            realizationConfigService.save(realizationConfigDO);
        } catch (Throwable te) {
            log.error("report realizationConfigDO error, domainCode:{}", domainCode, te);
        }
    }
}
