package com.kuaishou.business.samples.app.bizscene.activity.realize;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.Priority;
import com.kuaishou.business.extension.dimension.BizSceneRealize;
import com.kuaishou.business.extension.dimension.MatchType;
import com.kuaishou.business.samples.app.bizscene.activity.Consolidate;
import com.kuaishou.business.samples.app.bizscene.activity.MultiPlayer;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;

/**
 * @author liuzhuo07
 * Created on 2024-10-07
 */
@BizSceneRealize(scenes = {Consolidate.class, MultiPlayer.class}, matchType = MatchType.ALL)
@Component
public class MultiPlayerConsolidateRealize implements OrderPriceExtPoints {
	@Override
	@Priority(2100)
	public Long calculateExpressFee(CreateOrderDTO createOrderDTO) {
		return 750L;
	}
}
