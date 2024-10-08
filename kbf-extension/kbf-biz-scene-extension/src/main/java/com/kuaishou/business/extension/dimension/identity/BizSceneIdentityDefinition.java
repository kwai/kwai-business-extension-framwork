package com.kuaishou.business.extension.dimension.identity;

import com.kuaishou.business.core.identity.product.ProductIdentityDefinition;
import com.kuaishou.business.extension.dimension.MatchStatus;

/**
 * @author liuzhuo
 * Created on 2023-03-16 下午3:35
 * 维度身份定义
 */
public interface BizSceneIdentityDefinition<T> extends ProductIdentityDefinition<T, MatchStatus> {

    @Override
    default MatchStatus match(T request) {
        return MatchStatus.UNKNOWN;
    }

    /**
     * 资源作用域识别
     */
    default MatchStatus match(Object request, Long resourceId) {
        return MatchStatus.UNKNOWN;
    }
}
