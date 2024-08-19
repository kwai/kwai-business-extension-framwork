package com.kuaishou.business.samples.app.trade.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.KBizRealize;
import com.kuaishou.business.samples.app.trade.constants.Trade1Constants;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;


@KBizRealize(bizCode = Trade1Constants.BIZ_CODE)
@Component
public class Trade1Realize implements OrderPriceExtPoints {

	@Override
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 1000L;
	}
}
