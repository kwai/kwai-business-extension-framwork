package com.kuaishou.business.extension.engine;

import java.util.Set;

import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.identity.manage.KbfRealizeItem;
import com.kuaishou.business.core.session.KSessionScope;

public class SimpleExecutor extends Executor<ExecutorContext> {
	@Override
	public <Ext extends ExtPoint, P> ExecutorContext buildExecutorContext(Class<Ext> extClz, String methodName, P request) {
		ExecutorContext context = new ExecutorContext();
		context.setRequest(request);
		context.setExtClz(extClz);
		context.setMethodName(methodName);
		return context;
	}

	@Override
	public Set<KbfRealizeItem> recognize(ExecutorContext context) {
		SimpleProductIdentityRecognizer recognizer = new SimpleProductIdentityRecognizer<>(KSessionScope.getProducts());
		return recognizer.recognize(context.getRequest());
	}

	@Override
	public boolean check(ExecutorContext context) {
		if (!KSessionScope.init()) {
			String errMsg = "[kbf] ExtExecutor execute scope is empty";
			throw new KSessionException(errMsg);
		}
		return true;
	}
}
