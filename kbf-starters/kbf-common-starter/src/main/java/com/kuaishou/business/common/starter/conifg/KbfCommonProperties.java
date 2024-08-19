package com.kuaishou.business.common.starter.conifg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "kwai.business.framework.extension")
@Data
public class KbfCommonProperties {

	private String extensionType = "proto";


	private String recognizeCommand = "simple";


	private Boolean enableExtGrayscale = Boolean.FALSE;

}
