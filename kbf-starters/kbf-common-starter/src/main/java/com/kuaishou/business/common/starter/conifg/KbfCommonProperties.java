package com.kuaishou.business.common.starter.conifg;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Created by wuhao on 2021/2/21.
 */
@ConfigurationProperties(prefix = "kwai.business.framework.extension")
@Data
public class KbfCommonProperties {

	private String extensionType = "proto";


	private String recognizeCommand = "simple";


	private Boolean enableExtGrayscale = Boolean.FALSE;

}
