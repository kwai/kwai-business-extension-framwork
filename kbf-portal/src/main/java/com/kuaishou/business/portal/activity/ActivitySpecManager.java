package com.kuaishou.business.portal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuaishou.business.portal.common.ConfigConstant;
import com.kuaishou.business.portal.extension.ExtensionPointSpecDO;

/**
 * @author yangtianwen
 * @date 2022-04-04
 */
public class ActivitySpecManager {

    private Map<String, Map<String, ActivitySpecDO>> bizCodeToActivitySpecMap = new HashMap<>();

    private Map<String, List<ExtensionPointSpecDO>> bizCodeToRealizationSpecMap = new HashMap<>();

    public void addActivitySpec(String bizCode, ActivitySpecDO newActivity) {
        Map<String, ActivitySpecDO> codeToActivitySpecMap = bizCodeToActivitySpecMap.computeIfAbsent(bizCode, (key) -> new HashMap<>());

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

    public void addRealizationSpec(String bizCode, ExtensionPointSpecDO newRealization) {
        List<ExtensionPointSpecDO> codeToRealizationSpecMap = bizCodeToRealizationSpecMap.computeIfAbsent(bizCode, (key) -> new ArrayList<>());
        codeToRealizationSpecMap.add(newRealization);
    }

    public RealizationConfigDO toConfigDO() {
        RealizationConfigDO configDO = new RealizationConfigDO();
        configDO.setDomainCode(ConfigConstant.DOMAIN_CODE_TRADE);

        bizCodeToActivitySpecMap.forEach((key, val) -> {
            configDO.addActivities(key, val.values());
        });

        bizCodeToRealizationSpecMap.forEach(configDO::addRealizations);

        return configDO;
    }
}
