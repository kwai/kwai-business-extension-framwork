package com.kuaishou.business.portal.activity;

import java.util.HashMap;
import java.util.Map;

import com.kuaishou.business.portal.common.ConfigConstant;

/**
 * @author yangtianwen
 * @date 2022-04-04
 */
public class ProcessSpecManager {

    private static final String DEF_FLOW_ID = "default-flow";

    private Map<String, ActivitySpecDO> codeToActivitySpecMap = new HashMap<>();

    public void addActivitySpec(ActivitySpecDO newActivity) {

        ActivitySpecDO lastActivity = codeToActivitySpecMap.get(newActivity.getCode());
        if (null == lastActivity) {
            codeToActivitySpecMap.put(newActivity.getCode(), newActivity);
        } else {
            newActivity.getStepSpecList().forEach(newStep -> {
                StepSpecDO lastStep = lastActivity.getCache().get(newStep.getCode());
                if (null == lastStep) {
                    lastActivity.addStep(newStep);
                } else {
                    newStep.getExtensionPointSpecList().forEach(lastStep::addExtPoint);
                }
            });
        }
    }

    public ProcessConfigDO toConfigDO() {
        ProcessConfigDO configDO = new ProcessConfigDO();
        configDO.setDomainCode(ConfigConstant.DOMAIN_CODE_TRADE);

        configDO.add(DEF_FLOW_ID, codeToActivitySpecMap.values());

        return configDO;
    }
}
