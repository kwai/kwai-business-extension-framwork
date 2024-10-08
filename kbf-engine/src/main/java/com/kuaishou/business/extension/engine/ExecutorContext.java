package com.kuaishou.business.extension.engine;

import lombok.Data;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
@Data
public class ExecutorContext {

	private Object request;

	private Class extClz;

	private String methodName;

}
