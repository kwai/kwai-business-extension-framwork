package com.kuaishou.business.extension.dimension.session;

import java.io.Serializable;
import java.util.List;

import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.session.KSession;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;

import lombok.Getter;
import lombok.ToString;

/**
 * bizScene的KSession扩展
 */
@Getter
@ToString
public class BizSceneKSession extends KSession implements AutoCloseable, Serializable {

    /**
     * 单次请求的产品识别记录
     */
    private List<BizSceneSessionWrap> bizSceneSessionWraps;

    public BizSceneKSession(String bizCode, BusinessItem businessItem, KBizContext context, List<BizSceneSessionWrap> bizSceneSessionWraps) {
        super(bizCode, businessItem, context);
        this.bizSceneSessionWraps = bizSceneSessionWraps;
    }

    @Override
    public void close() {
        KSessionScope.destroy();
    }

}