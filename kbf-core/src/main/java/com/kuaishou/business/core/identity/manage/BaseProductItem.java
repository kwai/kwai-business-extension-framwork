package com.kuaishou.business.core.identity.manage;

import java.util.List;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;
import com.kuaishou.business.core.identity.product.ProductIdentityDefinition;

import lombok.Getter;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午5:50
 * 基本水平产品能力
 *
 * {@link NormalProductItem 水平产品能力}}
 */
public abstract class BaseProductItem<P extends ProductIdentityDefinition> implements KbfRealizeItem {

    /**
     * 名称
     */
    protected final String name;

    /**
     * 编码
     */
    protected final String code;

    /**
     * 产品定义
     */
    @Getter
    protected final P definition;

    /**
     * 实例
     */
    protected List<ExtPointRealizeWrap> extPointWraps = Lists.newArrayList();

    public BaseProductItem(String name, String code, P definition) {
        this.name = name;
        this.code = code;
        this.definition = definition;
    }

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
}
