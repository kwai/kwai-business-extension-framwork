package com.kuaishou.business.extension.dimension;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.function.ExtCallback;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.reduce.Reducer;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.recognizer.ReentrantBizSceneRecognizer;
import com.kuaishou.business.extension.dimension.session.BizSceneKSessionScope;
import com.kuaishou.business.extension.engine.Executor;
import com.kuaishou.business.extension.engine.SimpleKExtPointManger;
import com.kuaishou.business.extension.spring.ExtUtils;

import lombok.Setter;

/**
 * @author liuzhuo07
 * Created on 2024-09-28
 */
public class BizSceneExecutor extends Executor<BizSceneExecutorContext> {

	@Setter
	private List<String> supportBizSceneTypes;

	public <Ext extends ExtPoint, T, R, P> R execute(Class<Ext> extClz, String methodName, ExtCallback<Ext, T> extMethod, Supplier<T> defaultMethod,
		Long resourceId, Reducer<T, R> reducer, P request) {
		BizSceneExecutorContext context = buildExecutorContext(extClz, methodName, request);
		if (!check(context)) {
			return reducer.reduce(Lists.newArrayList(defaultMethod.get()));
		}
		context.setResourceId(resourceId);
		context.setIsResourceRequest(true);

		Collection<KbfRealizeItem> currentProducts = recognize(context);

		List<KbfRealizeItem> kbfRealizeItemList = Lists.newArrayList();
		kbfRealizeItemList.add(KSessionScope.getCurrentBusiness());
		kbfRealizeItemList.addAll(currentProducts);

		List<Ext> instanceList = SimpleKExtPointManger.getInstance(kbfRealizeItemList, extClz, methodName);

		return execAndHandlerResults(extMethod, defaultMethod, reducer, instanceList);
	}

	@Override
	public <Ext extends ExtPoint, P> BizSceneExecutorContext buildExecutorContext(Class<Ext> extClz, String methodName, P request) {
		BizSceneExecutorContext context = new BizSceneExecutorContext();
		context.setRequest(request);
		context.setExtClz(extClz);
		context.setMethodName(methodName);
		return context;
	}

	@Override
	public List<KbfRealizeItem> recognize(BizSceneExecutorContext context) {
		List<BizSceneSessionWrap> bizScenes = BizSceneKSessionScope.getBizScenes();
		ReentrantBizSceneRecognizer recognizer = new ReentrantBizSceneRecognizer<>(bizScenes, supportBizSceneTypes);
		if (context.getIsResourceRequest()) {
			return recognizer.recognize(context.getRequest(), context.getResourceId());
		} else {
			return recognizer.recognize(context.getRequest());
		}
	}

	@Override
	public boolean check(BizSceneExecutorContext context) {
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
