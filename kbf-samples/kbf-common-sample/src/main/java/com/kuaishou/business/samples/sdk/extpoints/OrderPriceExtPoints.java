package com.kuaishou.business.samples.sdk.extpoints;


import com.kuaishou.business.core.annotations.KExtPoint;
import com.kuaishou.business.core.annotations.KExtPointMethod;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;

@KExtPoint(belong = "TradeBuyStep", displayName = "计算金额扩展点")
public interface OrderPriceExtPoints extends ExtPoint {

	@KExtPointMethod(belong = "TradeBuyStep", displayName = "计算邮费金额")
	default Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 1500L;
	}
}
