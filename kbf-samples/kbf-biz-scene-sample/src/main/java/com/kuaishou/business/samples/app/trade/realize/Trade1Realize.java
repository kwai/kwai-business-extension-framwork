package com.kuaishou.business.samples.app.trade.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.extension.dimension.BizSceneRealize;
import com.kuaishou.business.samples.app.trade.Trade1Identity;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;


@BizSceneRealize(business = Trade1Identity.class)
@Component
public class Trade1Realize implements OrderPriceExtPoints {

	@Override
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 1000L;
	}
}
