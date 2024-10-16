package com.kuaishou.business.extension.spring;

import java.util.Set;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.engine.ProductMatchProcessor;
import com.kuaishou.business.extension.engine.SimpleProductIdentityRecognizer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 下午5:42
 * 扩展点识别（全局扫描）
 */
@Slf4j
public class SimpleExtActuator extends AbstractExtActuator {

    @Override
    public <P> Set<NormalProductItem> recognize(P request) {
		ExecuteContext executeContext = buildExecuteContext(request);
		SimpleProductIdentityRecognizer recognizer = new SimpleProductIdentityRecognizer(
			KSessionScope.getProducts(), new ProductMatchProcessor());
        return recognizer.recognize(executeContext);
    }

	private static <T> ExecuteContext buildExecuteContext(T request) {
		ExecuteContext executeContext = new ExecuteContext();
		executeContext.setRequest(request);
		return executeContext;
	}
}
