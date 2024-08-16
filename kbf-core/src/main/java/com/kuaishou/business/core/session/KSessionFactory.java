package com.kuaishou.business.core.session;

import com.kuaishou.business.core.context.KBizContext;

/**
 * session工厂
 *
 * {@link KSession 会话}
 */
public interface KSessionFactory<Session extends KSession, T> {

    <Context extends Class<? extends KBizContext>> Session openSession(T request, Context context);

}
