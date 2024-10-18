package com.kuaishou.business.extension.engine;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.identity.biz.BizIdentityRecognizer;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.manage.SpecManager;
import com.kuaishou.business.core.identity.product.DefaultProductSessionWrap;
import com.kuaishou.business.core.session.KSession;
import com.kuaishou.business.core.session.KSessionFactory;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认session工厂
 *
 * {@link KSession}
 * {@link com.kuaishou.business.core.session.KSessionScope}
 */
@Slf4j
public class DefaultKSessionFactory<T> implements KSessionFactory<KSession, T> {

    @Setter
    private BizIdentityRecognizer<T> bizIdentityRecognizer;

    @Setter
    private SpecManager specManager;

    @Override
    public <Context extends Class<? extends KBizContext>> KSession openSession(T request, Context context) {
        BusinessItem<T> businessItem = bizIdentityRecognizer.recognize(request);
		if (Objects.isNull(businessItem)) {
			String errMsg =
				"[kbf] openSession cannot find business, request : " + request + ", context : " + context;
			throw new BizIdentityException(errMsg);
		}

        KBizContext kBizContext = null;
        try {
            Constructor<? extends KBizContext> constructor = context.getConstructor();
            kBizContext = constructor.newInstance();
        } catch (Throwable t) {
            String errMsg =
                    "[kbf] openSession biz context cannot constr, request : " + request + ", context : " + context;
            log.error(errMsg);
            throw new KSessionException(errMsg);
        }
        Collection<NormalProductItem> allProductSpecs = specManager.getAllProductItems();
        List<DefaultProductSessionWrap> productSessionWrapList = Lists.newArrayListWithExpectedSize(allProductSpecs.size());
        for (NormalProductItem productItem : allProductSpecs) {
            DefaultProductSessionWrap productSessionWrap = new DefaultProductSessionWrap();
            productSessionWrap.setItem(productItem);
            productSessionWrapList.add(productSessionWrap);
        }

        return new KSession(businessItem.code(), businessItem, kBizContext, productSessionWrapList);
    }

}
