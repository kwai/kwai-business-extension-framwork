package com.kuaishou.business.core.exception;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BizIdentityException extends RuntimeException {

	private final String errorMsg;

	public BizIdentityException(String message) {
		super(message);
		this.errorMsg = message;
	}

}
