package com.kuaishou.business.extension.dimension;

import com.kuaishou.business.extension.engine.ExecutorContext;

import lombok.Data;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
@Data
public class BizSceneExecutorContext extends ExecutorContext {

	private Long resourceId;
	private Boolean isResourceRequest = false;
}
