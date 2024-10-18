package com.kuaishou.business.samples.app.bizscene.pay;

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
 * Created on 2024-10-08
 */
@BizScene(type = CommonConstants.PAY, name = CommonConstants.ALI_PAY, scope = EffectScope.REQUEST)
@Component
public class AliPay implements BizSceneIdentityDefinition {

	@Override
	public MatchStatus match(Object request) {
		CreateOrderRequest createOrder = (CreateOrderRequest) request;
		if (Objects.nonNull(createOrder) && "aliPay".equals(createOrder.getCreateOrder().getPayWay())) {
			return MatchStatus.MATCH;
		} else {
			return MatchStatus.NOT_MATCH;
		}
	}
}
