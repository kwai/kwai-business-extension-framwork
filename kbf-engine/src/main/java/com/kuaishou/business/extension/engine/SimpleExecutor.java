package com.kuaishou.business.extension.engine;

import java.util.Set;

import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.session.KSessionScope;

public class SimpleExecutor extends Executor {

	@Override
	public <P> Set<NormalProductItem> recognize(P request) {
		SimpleProductIdentityRecognizer<P> recognizer = new SimpleProductIdentityRecognizer<>(KSessionScope.getProducts());
		return recognizer.recognize(request);
	}

	@Override
	public boolean check(Object request) {
		if (!KSessionScope.init()) {
			String errMsg = "[kbf] ExtExecutor execute scope is empty";
			throw new KSessionException(errMsg);
		}
		return true;
	}
}
