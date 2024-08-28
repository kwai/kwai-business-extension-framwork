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
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActivitySpecDO extends BaseSpecDO {

    private List<StepSpecDO> stepSpecList = new ArrayList<>();
    @JsonIgnore
    private Map<String, StepSpecDO> cache = new HashMap<>();

    public void addStep(StepSpecDO specDO) {
        stepSpecList.add(specDO);
        cache.put(specDO.getCode(), specDO);
    }
}
