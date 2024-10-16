package com.kuaishou.business.extension.dimension;

import java.util.List;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;

import lombok.Data;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
@Data
public class BizSceneExecuteContext extends ExecuteContext {

	private Long resourceId;

	private Boolean isResourceRequest = false;

	private List<BizSceneItem> effectBizScenes;
}
