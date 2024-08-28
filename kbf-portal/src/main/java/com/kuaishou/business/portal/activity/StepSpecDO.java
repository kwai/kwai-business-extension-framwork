package com.kuaishou.business.portal.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kuaishou.business.portal.extension.BaseSpecDO;
import com.kuaishou.business.portal.extension.ExtensionPointSpecDO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author yangtianwen
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StepSpecDO extends BaseSpecDO {

    private Integer index;

    private List<ExtensionPointSpecDO> extensionPointSpecList = new ArrayList<>();
    @JsonIgnore
    private Map<String, ExtensionPointSpecDO> cache = new HashMap<>();

    public void addExtPoint(ExtensionPointSpecDO specDO) {
        extensionPointSpecList.add(specDO);
        cache.put(specDO.getCode(), specDO);
    }
}
