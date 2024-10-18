package com.kuaishou.business.samples.common.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SampleCommonErrorEnum {
	SERVER_ERROR(1, "SYSTEM_ERROR"),

	BIZ_IDENTITY_NOT_EXIST(2, "can not found biz identity"),
	;

	private final int code;

	
	private final String desc;
}
