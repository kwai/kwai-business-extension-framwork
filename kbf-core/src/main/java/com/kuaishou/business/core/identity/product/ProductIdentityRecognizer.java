package com.kuaishou.business.core.identity.product;

import com.kuaishou.business.core.identity.IdentityRecognizer;

/**
 * 水平产品识别器
 *
 * 提供2种识别方式
 * 1. 全量识别
 * 2. 缓存识别
 */
public interface ProductIdentityRecognizer<T, R> extends IdentityRecognizer<T, R> {

    @Override
    R recognize(T request);

}
