package com.kuaishou.business.core.session;

import java.io.Serializable;
import java.util.List;

import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.product.ProductSessionWrap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 一次会话
 *
 * {@link KSessionFactory 会话工厂}
 */
@Getter
@ToString
public class KSession implements AutoCloseable, Serializable {

    private final String bizCode;

    /**
     * 垂直业务
     */
    private final BusinessItem businessItem;

    /**
     * 业务上下文
     */
	@Setter
    private KBizContext context;

    /**
     * 单次请求的产品识别记录
     */
    private List<ProductSessionWrap> productSessionWraps;

    /**
     * 是否执行扩展点
     */
    @Setter
    private Boolean execExtPoint = false;

    @Override
    public void close() {
        KSessionScope.destroy();
    }

    public KSession(String bizCode, BusinessItem businessItem, KBizContext context, List<ProductSessionWrap> productSessionWraps) {
        this.bizCode = bizCode;
        this.businessItem = businessItem;
        this.context = context;
        this.productSessionWraps = productSessionWraps;
    }

    public KSession(String bizCode, BusinessItem businessItem, KBizContext context) {
        this.bizCode = bizCode;
        this.businessItem = businessItem;
        this.context = context;
    }
}
