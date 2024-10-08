package com.kuaishou.business.samples.app.bizscene.activity.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.Priority;
import com.kuaishou.business.extension.dimension.BizSceneRealize;
import com.kuaishou.business.samples.app.bizscene.activity.MultiPlayer;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;

/**
 * @author liuzhuo07
 * Created on 2024-10-07
 */
@BizSceneRealize(scenes = MultiPlayer.class)
@Component
public class MultiPlayerRealize implements OrderPriceExtPoints {
	@Override
	@Priority(1050)
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 500L;
	}
}
