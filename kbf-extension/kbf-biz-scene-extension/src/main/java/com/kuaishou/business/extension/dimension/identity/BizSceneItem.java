package com.kuaishou.business.extension.dimension.identity;

import java.util.Objects;

import com.kuaishou.business.core.identity.manage.BaseProductItem;

import lombok.Getter;

/**
 * @author liuzhuo
 * Created on 2023-03-21 下午3:10
 */
@Getter
public class BizSceneItem extends BaseProductItem<BizSceneIdentityDefinition> {

    /**
     * 作用域
     */
    @Getter
    private final EffectScope effectScope;

    /**
     * 场景类型
     */
    private final String bizSceneType;

    private final int hashCode;

    public BizSceneItem(String name, String code, BizSceneIdentityDefinition definition, String bizSceneType,
            EffectScope effectScope) {
        super(name, code, definition);
        this.effectScope = effectScope;
        this.bizSceneType = bizSceneType;
        this.hashCode = Objects.hash(code);
    }


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
        if (!super.equals(o)) {
            return false;
        }
        BizSceneItem that = (BizSceneItem) o;
        return Objects.equals(name(), that.name())
                && Objects.equals(code(), that.code())
                && getEffectScope() == that.getEffectScope()
                && Objects.equals(bizSceneType, that.bizSceneType);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return "BizSceneItem{" +
                "name='" + name() + '\'' +
                ", code='" + code() + '\'' +
                ", bizSceneType='" + bizSceneType + '\'' +
                ", effectScope=" + getEffectScope() +
                '}';
    }
}
