package com.kuaishou.business.portal.extension;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

/**
 * @author yangtianwen
 * @date 2022-04-09
 */
@Data
public class BaseSpecDO {

    private String code;
    private String name;

    public <T extends BaseSpecDO> T of(String code, String name) {
        this.code = code;
        this.name = name;

        return (T) this;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }

        BaseSpecDO target = (BaseSpecDO) obj;
        return StringUtils.equals(this.getCode(), target.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}
