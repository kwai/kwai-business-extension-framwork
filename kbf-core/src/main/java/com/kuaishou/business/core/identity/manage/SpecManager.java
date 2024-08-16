package com.kuaishou.business.core.identity.manage;

import java.util.Collection;

/**
 * 垂直业务/水平产品统一管理
 * 支持水平产品扩展
 *
 * {@link KbfRealizeItem 标准能力}
 * {@link BusinessItem 垂直业务标准能力}
 * {@link NormalProductItem 水平产品标准能力}
 */
public interface SpecManager<P extends BaseProductItem> {

    /**
     * 注册垂直业务
     */
    void registerBusinessSpec(BusinessItem businessItem);

    /**
     * 注册水平产品
     */
    void registerProductSpec(P productSpec);

    /**
     * 获取垂直业务注册信息
     */
    BusinessItem getBusinessSpec(String code);

    /**
     * 获取水平产品注册信息
     */
    P getProductSpec(String code);

    /**
     * 获取所有注册的水平产品信息
     */
    Collection<P> getAllProductSpecs();

    /**
     * 获取所有注册的垂直业务信息
     */
    Collection<BusinessItem> getAllBusinessSpecs();

    /**
     * 获取所有注册的水平产品信息的数量
     */
    int countAllProductSpecs();

    /**
     * 获取所有注册的垂直业务信息的数量
     */
    int countAllBusinessSpecs();
}
