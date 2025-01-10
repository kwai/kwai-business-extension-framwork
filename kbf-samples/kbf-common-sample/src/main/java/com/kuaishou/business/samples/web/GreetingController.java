package com.kuaishou.business.samples.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.samples.common.constants.SampleCommonErrorEnum;
import com.kuaishou.business.samples.sdk.CreateOrderDTO;
import com.kuaishou.business.samples.sdk.request.CreateOrderRequest;
import com.kuaishou.business.samples.sdk.response.CreateOrderResponse;
import com.kuaishou.business.samples.service.CreateOrderService;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class GreetingController {

	@Autowired
	private CreateOrderService createOrderService;

	@RequestMapping("/kbf")
	@ResponseBody
	public Object kbf(@RequestParam String bizCode) {
		try {
			CreateOrderRequest createOrderRequest = buildCreateOrderRequest(bizCode);
			CreateOrderResponse response = createOrderService.createOrder(createOrderRequest);

			return ControllerResult.of(response);
		} catch (BizIdentityException e) {
			log.error("GreetingController call kbf BizIdentityException bizCode is {} ", bizCode, e);
			return ControllerResult.ofFailed(SampleCommonErrorEnum.BIZ_IDENTITY_NOT_EXIST.getCode() + "", e.getErrorMsg());
		} catch (Throwable t) {
			log.error("GreetingController call kbf Syetem error bizCode is {} ", bizCode, t);
			return ControllerResult.ofFailed(SampleCommonErrorEnum.SERVER_ERROR.getCode() + "", SampleCommonErrorEnum.SERVER_ERROR.getDesc());
		}
	}

	@RequestMapping("/kbf2")
	@ResponseBody
	public Object kbf2(@RequestParam String bizCode) {
		try {
			CreateOrderRequest createOrderRequest = buildCreateOrderRequest(bizCode);
			CreateOrderResponse response = createOrderService.createOrder2(createOrderRequest);

			return ControllerResult.of(response);
		} catch (BizIdentityException e) {
			log.error("GreetingController call kbf2 BizIdentityException bizCode is {} ", bizCode, e);
			return ControllerResult.ofFailed(SampleCommonErrorEnum.BIZ_IDENTITY_NOT_EXIST.getCode() + "", e.getErrorMsg());
		} catch (Throwable t) {
			log.error("GreetingController call kbf2 Syetem error bizCode is {} ", bizCode, t);
			return ControllerResult.ofFailed(SampleCommonErrorEnum.SERVER_ERROR.getCode() + "", SampleCommonErrorEnum.SERVER_ERROR.getDesc());
		}
	}

	private static CreateOrderRequest buildCreateOrderRequest(String bizCode) {
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();

		CreateOrderDTO createOrder = new CreateOrderDTO();
		createOrder.setCategoryId(1L);
		createOrder.setOid(1L);

		createOrderRequest.setCreateOrder(createOrder);
		createOrderRequest.setBizCode(bizCode);
		return createOrderRequest;
	}
}
