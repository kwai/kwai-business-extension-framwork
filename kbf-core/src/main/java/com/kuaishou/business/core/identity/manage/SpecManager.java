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
	 *
	 * @param businessItem 垂直业务
	 */
    void registerBusinessSpec(BusinessItem businessItem);

	/**
	 *
	 * @param productSpec 产品
	 */
    void registerProductSpec(P productSpec);

	/**
	 *
	 * @param code 业务身份
	 * @return BusinessItem
	 */
    BusinessItem getBusinessSpec(String code);

	/**
	 *
	 * @param code 业务身份
	 * @return 产品
	 */
    P getProductSpec(String code);

	/**
	 *
	 * @return 所有产品
	 */
    Collection<P> getAllProductSpecs();

	/**
	 *
	 * @return 所有业务
	 */
    Collection<BusinessItem> getAllBusinessSpecs();

	/**
	 *
	 * @return 产品数量
	 */
    int countAllProductSpecs();

	/**
	 *
	 * @return 业务数量
	 */
    int countAllBusinessSpecs();
}
