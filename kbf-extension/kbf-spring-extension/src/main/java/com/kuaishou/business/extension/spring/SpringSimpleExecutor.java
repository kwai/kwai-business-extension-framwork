package com.kuaishou.business.extension.spring;

import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.engine.SimpleExecutor;

public class SpringSimpleExecutor extends SimpleExecutor {

	@Override
	public boolean check(Object request) {
		if (!KSessionScope.init()) {
			String errMsg = "[kbf] ExtExecutor execute scope is empty";
			throw new KSessionException(errMsg);
		}

		if (ExtUtils.enableExtGray) {
			return KSessionScope.isExecExtPoint();
		}
		return true;
	}

}
