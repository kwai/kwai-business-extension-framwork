package com.kuaishou.business.extension.engine;


import java.util.List;

import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.biz.BizIdentityRecognizer;
import com.kuaishou.business.core.identity.manage.BusinessItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DefaultBizIdentityRecognizer<T> implements BizIdentityRecognizer<T> {

    private final List<BusinessItem<T>> businessItems;

    @Override
    public BusinessItem<T> recognize(T request) {
        for (BusinessItem<T> businessItem : businessItems) {
            MatchResult matchResult = businessItem.getBizIdentityDefinition().match(request);
            if (MatchResult.match(matchResult)) {
                return businessItem;
            }
        }
        return null;
    }

}
