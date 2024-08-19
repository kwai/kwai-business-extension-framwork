package com.kuaishou.business.samples.app.trade2.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.KBizRealize;
import com.kuaishou.business.samples.app.trade2.constants.Trade2Constants;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;


@KBizRealize(bizCode = Trade2Constants.BIZ_CODE)
@Component
public class Trade2Realize implements OrderPriceExtPoints {

	@Override
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 500L;
	}
}
