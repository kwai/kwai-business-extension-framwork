package com.kuaishou.business.extension.spring;

import com.kuaishou.business.core.identity.manage.SpecManager;
import com.kuaishou.business.extension.engine.ExtActuator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-21 上午12:42
 */
@Slf4j
public class ExtUtils {


    public static final String PROTO = "proto";

    /**
     * 扩展能力管理
     */
    public static SpecManager specManager;

    /**
     * 执行器
     */
    public static ExtActuator extActuator;

    /**
     * 识别命令
     */
    public static String recognizeCommand;

    /**
     * 扩展类型
     */
    public static String extensionType;

	/**
	 * 是否开启扩展点执行灰度
	 */
	public static Boolean enableExtGray;

	public static String kExtPointScanPathConfig = "kwai.business.framework.ext.scanPath";

    public void setSpecManager(SpecManager specManager) {
        ExtUtils.specManager = specManager;
    }

    public void setExtActuator(ExtActuator extActuator) {
        ExtUtils.extActuator = extActuator;
    }

	public void setRecognizeCommand(String recognizeCommand) {
		ExtUtils.recognizeCommand = recognizeCommand;
	}

	public void setExtensionType(String extensionType) {
		ExtUtils.extensionType = extensionType;
	}

	public void setEnableExtGray(Boolean enableExtGray) {
		ExtUtils.enableExtGray = enableExtGray;
	}
}
