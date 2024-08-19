package com.kuaishou.business.samples.service;

import org.springframework.stereotype.Service;

import com.kuaishou.business.extension.spring.annotations.KExtPointInvoke;
import com.kuaishou.business.extension.spring.annotations.KSessionAround;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.extpoints.OrderPriceExtPoints;
import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;
import com.kuaishou.business.samples.sdk.response.CreateOrderResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-04-01 下午11:16
 */
@Service
@Slf4j
public class CreateOrderServiceImpl implements CreateOrderService {

	@KExtPointInvoke
	private OrderPriceExtPoints orderPriceExtPoints;

	@Override
	@KSessionAround
	public CreateOrderResponse createOrder(CreateOrderRequest request) {
		CreateOrderDTO createOrder = request.getCreateOrder();

		Long expressFee = orderPriceExtPoints.calculateExpressFee(createOrder);
		log.info("exec calculateExpressFee pre oid : {}, result : {}}", createOrder.getOid(), expressFee);

		CreateOrderResponse response = new CreateOrderResponse();
		response.setExpressFee(expressFee);
		return response;
	}
}
