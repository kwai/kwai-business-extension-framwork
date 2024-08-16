package com.kuaishou.business.core.identity.manage;

import java.util.Objects;

import com.kuaishou.business.core.identity.product.NormalProductIdentityDefinition;

/**
 * @author liuzhuo
 * Created on 2023-03-21 下午4:44
 * 水平产品能力
 *
 * {@link BusinessItem 垂直业务能力}
 */
public class NormalProductItem extends BaseNormalProductItem<NormalProductIdentityDefinition> {

    public NormalProductItem(String name, String code, NormalProductIdentityDefinition definition) {
        super(name, code, definition);
    }

    /**
     * 是否组合产品
     */
    public boolean isCombo() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NormalProductItem that = (NormalProductItem) o;
        return Objects.equals(name, that.name)
                && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "NormalProductItem{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
