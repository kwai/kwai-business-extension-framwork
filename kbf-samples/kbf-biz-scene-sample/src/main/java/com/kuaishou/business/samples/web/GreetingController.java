package com.kuaishou.business.samples.web;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
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
	public Object kbf(@RequestParam String bizCode, @RequestParam(required = false) String activityType,
		@RequestParam(required = false) Boolean consolidate, @RequestParam(required = false) String payWay) {
		try {
			CreateOrderRequest createOrderRequest = new CreateOrderRequest();

			CreateOrderDTO createOrder = new CreateOrderDTO();
			createOrder.setCategoryId(1L);
			createOrder.setOid(1L);
			if (StringUtils.isNotEmpty(activityType)) {
				createOrder.setActivityType(activityType);
			}
			if (Objects.nonNull(consolidate)) {
				createOrder.setConsolidate(consolidate);
			}
			if (StringUtils.isNotEmpty(payWay)) {
				createOrder.setPayWay(payWay);
			}

			createOrderRequest.setCreateOrder(createOrder);
			createOrderRequest.setBizCode(bizCode);

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
}
