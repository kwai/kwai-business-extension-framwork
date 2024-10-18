package com.kuaishou.business.samples.app.bizscene.activity;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.kuaishou.business.extension.dimension.BizScene;
import com.kuaishou.business.extension.dimension.MatchStatus;
import com.kuaishou.business.extension.dimension.identity.BizSceneIdentityDefinition;
import com.kuaishou.business.extension.dimension.identity.EffectScope;
import com.kuaishou.business.samples.app.bizscene.constants.CommonConstants;
import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;

/**
 * @author liuzhuo07
 * Created on 2024-09-30
 */
@BizScene(type = CommonConstants.ACTIVITY, name = CommonConstants.MULTI_PLAYER, scope = EffectScope.RESOURCE)
@Component
public class MultiPlayer implements BizSceneIdentityDefinition {

	@Override
	public MatchStatus match(Object request, Long resourceId) {
		CreateOrderRequest createOrder = (CreateOrderRequest) request;
		if (Objects.nonNull(createOrder) && "multiPlayer".equals(createOrder.getCreateOrder().getActivityType())) {
			return MatchStatus.MATCH;
		} else {
			return MatchStatus.NOT_MATCH;
		}
	}
}
