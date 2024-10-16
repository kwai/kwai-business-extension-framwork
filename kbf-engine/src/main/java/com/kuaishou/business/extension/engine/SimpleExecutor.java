package com.kuaishou.business.extension.engine;

import java.util.Set;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.extpoint.ExtPoint;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.session.KSessionScope;

public class SimpleExecutor extends Executor<ExecuteContext> {
	@Override
	public <Ext extends ExtPoint, P> ExecuteContext buildExecutorContext(Class<Ext> extClz, String methodName, P request) {
		ExecuteContext context = new ExecuteContext();
		context.setRequest(request);
		context.setExtClz(extClz);
		context.setMethodName(methodName);
		return context;
	}

	@Override
	public Set<NormalProductItem> recognize(ExecuteContext context) {
		SimpleProductIdentityRecognizer recognizer = new SimpleProductIdentityRecognizer(
			KSessionScope.getProducts(), new ProductMatchProcessor());
		return recognizer.recognize(context);
	}

	@Override
	public boolean check(ExecuteContext context) {
		if (!KSessionScope.init()) {
			String errMsg = "[kbf] ExtExecutor execute scope is empty";
			throw new KSessionException(errMsg);
		}
		return true;
	}
}
