package com.kuaishou.business.extension.engine;


import java.util.List;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.biz.BizIdentityRecognizer;
import com.kuaishou.business.core.identity.manage.BusinessItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultBizIdentityRecognizer<T> implements BizIdentityRecognizer<T> {

    private final List<DefaultBizIdentitySessionWrap> businessWrapItemList;
	private final BizIdentityMatchProcessor bizIdentityMatchProcessor;

    @Override
    public BusinessItem<T> recognize(T request) {
		ExecuteContext executeContext = buildExecuteContext(request);
		for (DefaultBizIdentitySessionWrap bizIdentitySessionWrap : businessWrapItemList) {
			boolean match = bizIdentityMatchProcessor.process(bizIdentitySessionWrap, executeContext);
			if (match) {
				return bizIdentitySessionWrap.unwrap();
			}
        }
        return null;
    }

	private static <T> ExecuteContext buildExecuteContext(T request) {
		ExecuteContext executeContext = new ExecuteContext();
		executeContext.setRequest(request);
		return executeContext;
	}

}
