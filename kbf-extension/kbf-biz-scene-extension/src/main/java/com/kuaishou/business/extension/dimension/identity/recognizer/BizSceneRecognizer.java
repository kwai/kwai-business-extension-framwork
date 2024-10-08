package com.kuaishou.business.extension.dimension.identity.recognizer;

import java.util.List;

import com.kuaishou.business.core.identity.product.ProductIdentityRecognizer;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;

/**
 * @author liuzhuo
 * Created on 2023-04-04 下午5:24
 * <p>
 * bizScene识别器
 */
public interface BizSceneRecognizer<T> extends ProductIdentityRecognizer<T, List<BizSceneItem>> {

    @Override
    List<BizSceneItem> recognize(T request);


    /**
     * 资源作用域识别
     */
    List<BizSceneItem> recognize(T request, Long resourceId);
}
