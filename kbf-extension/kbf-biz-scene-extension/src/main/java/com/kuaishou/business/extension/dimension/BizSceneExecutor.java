package com.kuaishou.business.extension.dimension;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.recognizer.ReentrantBizSceneRecognizer;
import com.kuaishou.business.extension.dimension.session.BizSceneKSessionScope;
import com.kuaishou.business.extension.engine.Executor;
import com.kuaishou.business.extension.spring.ExtUtils;

import lombok.Setter;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
public class BizSceneExecutor extends Executor {

	@Setter
	private List<String> supportBizSceneTypes;
	@Setter
	private Long resourceId;
	@Setter
	private Boolean isResourceRequest = false;

	@Override
	public List<BizSceneItem> recognize(Object request) {
		List<BizSceneSessionWrap> bizScenes = BizSceneKSessionScope.getBizScenes();
		ReentrantBizSceneRecognizer recognizer = new ReentrantBizSceneRecognizer(bizScenes,
			supportBizSceneTypes, resourceId, isResourceRequest);
		return recognizer.recognize(request);
	}

	@Override
	public boolean check(Object request) {
		if (!BizSceneKSessionScope.init()) {
			String errMsg = "[kbf] ExtExecutor execute scope is empty";
			throw new KSessionException(errMsg);
		}

		if (ExtUtils.enableExtGray) {
			return KSessionScope.isExecExtPoint();
		}

		if (CollectionUtils.isEmpty(supportBizSceneTypes)) {
			return false;
		}

		return true;
	}

}
