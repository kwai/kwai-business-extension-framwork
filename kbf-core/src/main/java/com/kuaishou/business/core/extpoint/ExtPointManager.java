package com.kuaishou.business.core.extpoint;

import java.util.List;

import com.kuaishou.business.core.identity.product.ProductIdentityDefinition;

public interface ExtPointManager {

    void registerExtPointRealize(String bizCode, ExtPointRealizeWrap extPointRealizeWrap);

    void registerExtPointRealizeOfProducts(Class<? extends ProductIdentityDefinition> product, ExtPointRealizeWrap extPointRealizeWrap);

    <T> T getInstance(String bizCode, Class<T> clazz);

    <T> List<T> getInstanceList(String bizCode, List<Class<? extends ProductIdentityDefinition>> products, Class<T> clazz);
}
