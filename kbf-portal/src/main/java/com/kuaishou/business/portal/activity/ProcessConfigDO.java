package com.kuaishou.business.portal.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * @author yangtianwen
 */
@Data
public class ProcessConfigDO {

    private String domainCode;

    private Set<String> flowIdList = new HashSet<>();

    private Map<String, List<ActivitySpecDO>> activityMap = new HashMap<>();

    private Long version;

    public void add(String flowId, Collection<ActivitySpecDO> list) {
        flowIdList.add(flowId);

        List<ActivitySpecDO> last = activityMap.computeIfAbsent(flowId, (key) -> new ArrayList<>());
        last.addAll(list);
    }
}
