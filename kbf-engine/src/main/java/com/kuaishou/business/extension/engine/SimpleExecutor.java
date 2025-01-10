package com.kuaishou.business.extension.engine;

import java.util.List;

import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.session.KSessionScope;

public class SimpleExecutor extends Executor {

	@Override
	public List<NormalProductItem> recognize(Object request) {
		SimpleProductIdentityRecognizer recognizer = new SimpleProductIdentityRecognizer<>(KSessionScope.getProducts());
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
