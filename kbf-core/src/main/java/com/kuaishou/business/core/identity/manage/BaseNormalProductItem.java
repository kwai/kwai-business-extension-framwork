package com.kuaishou.business.core.identity.manage;

import com.kuaishou.business.core.identity.product.NormalProductIdentityDefinition;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午5:50
 * 基本水平产品能力
 *
 * {@link NormalProductItem 水平产品能力}}
 */
public abstract class BaseNormalProductItem<P extends NormalProductIdentityDefinition> extends BaseProductItem<P> {


    public BaseNormalProductItem(String name, String code, P definition) {
        super(name, code, definition);
    }

    @Override
    public P getDefinition() {
        return definition;
    }

}
