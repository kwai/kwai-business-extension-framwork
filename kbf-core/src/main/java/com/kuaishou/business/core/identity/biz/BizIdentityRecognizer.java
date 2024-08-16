package com.kuaishou.business.core.identity.biz;

import com.kuaishou.business.core.identity.IdentityRecognizer;
import com.kuaishou.business.core.identity.manage.BusinessItem;

@FunctionalInterface
public interface BizIdentityRecognizer<T> extends IdentityRecognizer<T, BusinessItem<T>> {

    @Override
    BusinessItem<T> recognize(T request);

}
