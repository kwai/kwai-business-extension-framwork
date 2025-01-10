package com.kuaishou.business.samples.app.bizscene.pay.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.Priority;
import com.kuaishou.business.extension.dimension.BizSceneRealize;
import com.kuaishou.business.samples.app.bizscene.pay.AliPay;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;

/**
 * @author liuzhuo07
 * Created on 2024-10-08
 */
@BizSceneRealize(scenes = AliPay.class)
@Component
public class AliPayRealize implements OrderPriceExtPoints {
	@Override
	@Priority(2050)
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 100L;
	}
}
