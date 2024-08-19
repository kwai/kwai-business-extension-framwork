package com.kuaishou.business.samples.app.trade2;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.KBusiness;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.samples.app.trade2.constants.Trade2Constants;
import com.kuaishou.business.samples.common.TradeNormalBizIdentityDefinition;
import com.kuaishou.business.samples.common.TradeRequest;
import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;

@KBusiness(name = Trade2Constants.BIZ_CODE_NAME, code = Trade2Constants.BIZ_CODE)
@Component
public class Trade2Identity implements TradeNormalBizIdentityDefinition {

	@Override
	public String supportedBizCode() {
		return Trade2Constants.BIZ_CODE;
	}

	@Override
	public String scanPath() {
		return "*";
	}

	@Override
	public MatchResult match(TradeRequest request) {
		CreateOrderRequest createOrder = (CreateOrderRequest) request;
		if (createOrder.getBizCode().equals("trade2")) {
			return MatchResult.MATCH;
		} else {
			return MatchResult.NOT_MATCH;
		}
	}
}
