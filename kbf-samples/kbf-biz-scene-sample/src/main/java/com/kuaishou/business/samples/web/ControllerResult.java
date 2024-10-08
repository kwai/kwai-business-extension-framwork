package com.kuaishou.business.samples.web;

import lombok.Data;

@Data
public class ControllerResult<T> {

	private T result;

	private String errorCode;

	private String errorMsg;

	private boolean success;

	public static <T> ControllerResult<T> of() {
		return new ControllerResult<>();
	}


	public static <T> ControllerResult<T> of(T result) {
		ControllerResult<T> controllerResult = of();
		controllerResult.setSuccess(true);
		controllerResult.setResult(result);
		return controllerResult;
	}


	public static <T> ControllerResult<T> ofFailed(String errorCode, String errorMsg) {
		ControllerResult<T> controllerResult = of();
		controllerResult.setErrorCode(errorCode);
		controllerResult.setErrorMsg(errorMsg);
		controllerResult.setSuccess(false);
		return controllerResult;
	}

}
