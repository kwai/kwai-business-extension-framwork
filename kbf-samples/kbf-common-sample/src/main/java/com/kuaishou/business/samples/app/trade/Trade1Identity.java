package com.kuaishou.business.samples.app.trade;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.kuaishou.business.core.annotations.KBusiness;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.biz.NormalBizIdentityDefinition;
import com.kuaishou.business.samples.app.trade.constants.Trade1Constants;
import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;


@KBusiness(name = Trade1Constants.BIZ_CODE_NAME, code = Trade1Constants.BIZ_CODE)
@Component
public class Trade1Identity implements NormalBizIdentityDefinition {

	@Override
	public String supportedBizCode() {
		return Trade1Constants.BIZ_CODE;
	}

	@Override
	public String scanPath() {
		return "*";
	}

	@Override
	public MatchResult match(Object request) {
		CreateOrderRequest createOrder = (CreateOrderRequest) request;
		if (Objects.nonNull(request) && createOrder.getBizCode().equals("trade1")) {
			return MatchResult.MATCH;
		} else {
			return MatchResult.NOT_MATCH;
		}
	}
}
