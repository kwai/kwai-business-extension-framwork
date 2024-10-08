package com.kuaishou.business.samples.sdk.request;


import com.kuaishou.business.samples.common.TradeRequest;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class CreateOrderRequest extends TradeRequest {

	private CreateOrderDTO createOrder;
}
