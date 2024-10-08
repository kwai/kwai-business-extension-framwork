package com.kuaishou.business.common.starter.conifg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "kwai.business.framework.extension")
@Data
public class KbfCommonProperties {

	private String extensionType = "bizScene";


	private String recognizeCommand = "reentrant";


	private Boolean enableExtGrayscale = Boolean.FALSE;

}
