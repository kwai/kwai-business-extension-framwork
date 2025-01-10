package com.kuaishou.business.extension.engine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.manage.SpecManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-26 下午10:30
 */
@Slf4j
public class DefaultSpecManager implements SpecManager<NormalProductItem> {

    private final Map<String, BusinessItem> businessSpecMap = new HashMap<>();

    private final Map<String, NormalProductItem> productSpecMap = new HashMap<>();

    @Override
    public void registerBusinessItem(BusinessItem businessItem) {
        String code = businessItem.code();
        if (businessSpecMap.containsKey(code)) {
            String errMsg = "[kbf] DefaultItemManager registerBusinessItem code has exists, code : " + code;
            log.error(errMsg);
            throw new BizIdentityException(errMsg);
        }
        businessSpecMap.put(code, businessItem);
    }

    @Override
    public void registerProductItem(NormalProductItem productSpec) {
        String code = productSpec.code();
        if (productSpecMap.containsKey(code)) {
            String errMsg = "[kbf] DefaultItemManager registerProductItem code has exists, code : " + code;
            log.error(errMsg);
            throw new BizIdentityException(errMsg);
        }
        productSpecMap.put(code, productSpec);
    }

    @Override
    public BusinessItem getBusinessItem(String code) {
        return businessSpecMap.get(code);
    }

    @Override
    public NormalProductItem getProductItem(String code) {
        return productSpecMap.get(code);
    }

    @Override
    public Collection<NormalProductItem> getAllProductItems() {
        return productSpecMap.values();
    }

    @Override
    public Collection<BusinessItem> getAllBusinessItems() {
        return businessSpecMap.values();
    }

    @Override
    public int countAllProductItems() {
        return productSpecMap.size();
    }

    @Override
    public int countAllBusinessItems() {
        return businessSpecMap.size();
    }
}
