package com.kuaishou.business.core.identity.manage;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.identity.biz.NormalBizIdentityDefinition;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author liuzhuo
 * Created on 2023-03-21 下午4:44
 * 垂直业务能力
 *
 */
@RequiredArgsConstructor
public class BusinessItem<T> implements KbfRealizeItem {

    /**
     * 名称
     */
    private final String name;

    /**
     * 编码
     */
    private final String code;

    /**
     * 业务定义
     */
    @Getter
    private final NormalBizIdentityDefinition<T> definition;

    /**
     * 实例
     */
    private final List<ExtPointRealizeWrap> extPointWraps = Lists.newCopyOnWriteArrayList();

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public List<ExtPointRealizeWrap> getExtPointRealizes() {
        return extPointWraps;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusinessItem that = (BusinessItem) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "BusinessItem{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
