package com.kuaishou.business.portal.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kuaishou.business.portal.extension.ExtensionPointSpecDO;

import lombok.Data;

/**
 * @author yangtianwen
 */
@Data
public class RealizationConfigDO {

    private String domainCode;

    private Set<String> bizCodeList = new HashSet<>();

    private Map<String, List<ActivitySpecDO>> activityMap = new HashMap<>();

    private Map<String, List<ExtensionPointSpecDO>> realizationMap = new HashMap<>();

    private Long version;

    public void addActivities(String bizCode, Collection<ActivitySpecDO> list) {
        bizCodeList.add(bizCode);

        List<ActivitySpecDO> last = activityMap.computeIfAbsent(bizCode, (key) -> new ArrayList<>());
        last.addAll(list);
    }

    public void addRealizations(String bizCode, Collection<ExtensionPointSpecDO> list) {
        bizCodeList.add(bizCode);

        List<ExtensionPointSpecDO> last = realizationMap.computeIfAbsent(bizCode, (key) -> new ArrayList<>());
        last.addAll(list);
    }
}
