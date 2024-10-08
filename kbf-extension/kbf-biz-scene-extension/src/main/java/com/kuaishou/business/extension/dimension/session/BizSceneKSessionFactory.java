package com.kuaishou.business.extension.dimension.session;

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
import com.kuaishou.business.core.identity.manage.SpecManager;
import com.kuaishou.business.core.session.KSessionFactory;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午10:54
 * bizScene会话工厂
 *
 * {@link BizSceneKSession}
 * {@link BizSceneKSessionScope}
 */
@Slf4j
public class BizSceneKSessionFactory<T> implements KSessionFactory<BizSceneKSession, T> {

    @Setter
    private BizIdentityRecognizer<T> bizIdentityRecognizer;

    @Setter
    private SpecManager specManager;

    @Override
    public <Context extends Class<? extends KBizContext>> BizSceneKSession openSession(T request, Context context) {
        BusinessItem businessItem = bizIdentityRecognizer.recognize(request);
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
        Collection<BizSceneItem> allProductSpecs = specManager.getAllProductSpecs();
        List<BizSceneSessionWrap> bizSceneSessionWrapList = Lists.newArrayListWithExpectedSize(allProductSpecs.size());
        for (BizSceneItem bizSceneSpec : allProductSpecs) {
            BizSceneSessionWrap bizSceneSessionWrap = new BizSceneSessionWrap();
            bizSceneSessionWrap.setBizSceneSpec(bizSceneSpec);
            bizSceneSessionWrapList.add(bizSceneSessionWrap);
        }

        return new BizSceneKSession(businessItem.code(), businessItem, kBizContext, bizSceneSessionWrapList);
    }
}
