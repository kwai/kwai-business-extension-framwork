package com.kuaishou.business.portal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuaishou.business.portal.extension.BaseSpecDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yangtianwen
 * @date 2022-04-04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProcessSpecDO extends BaseSpecDO {

    private List<ActivitySpecDO> activitySpecList = new ArrayList<>();
    @JsonIgnore
    private Map<String, ActivitySpecDO> cache = new HashMap<>();

    public void addActivity(ActivitySpecDO specDO) {
        activitySpecList.add(specDO);
        cache.put(specDO.getCode(), specDO);
    }
}
