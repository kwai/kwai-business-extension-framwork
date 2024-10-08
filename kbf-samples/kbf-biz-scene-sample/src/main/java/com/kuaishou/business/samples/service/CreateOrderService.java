package com.kuaishou.business.samples.service;


import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;
import com.kuaishou.business.samples.sdk.response.CreateOrderResponse;

public interface CreateOrderService {

	CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest);

}
