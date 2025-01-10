package com.kuaishou.business.extension.dimension.identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kuaishou.business.core.exception.BizIdentityException;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.SpecManager;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-27 下午1:08
 */
@Slf4j
public class BizSceneSpecManager implements SpecManager<BizSceneItem> {

    private final Map<String, BusinessItem> businessSpecMap = new HashMap<>();
    private final List<BusinessItem> businessItemList = new ArrayList<>();

    private final Map<String, BizSceneItem> bizSceneSpecMap = new HashMap<>();
    private final List<BizSceneItem> bizSceneSpecList = new ArrayList<>();

    @Override
    public void registerBusinessItem(BusinessItem businessItem) {
        String code = businessItem.code();
        if (businessSpecMap.containsKey(code)) {
            String errMsg = "[kbf] BizSceneItemManager registerBusinessItem code has exists, code : " + code;
            log.error(errMsg);
            throw new BizIdentityException(errMsg);
        }
        businessSpecMap.put(code, businessItem);
        businessItemList.add(businessItem);
    }

    @Override
    public void registerProductItem(BizSceneItem bizSceneSpec) {
        String code = bizSceneSpec.code();
        if (bizSceneSpecMap.containsKey(code)) {
            String errMsg = "[kbf] BizSceneItemManager registerProductItem code has exists, code : " + code;
            log.error(errMsg);
            throw new BizIdentityException(errMsg);
        }
        bizSceneSpecMap.put(code, bizSceneSpec);
        bizSceneSpecList.add(bizSceneSpec);
    }

    @Override
    public BusinessItem getBusinessItem(String code) {
        return businessSpecMap.get(code);
    }

    @Override
    public BizSceneItem getProductItem(String code) {
        return bizSceneSpecMap.get(code);
    }

    @Override
    public Collection<BizSceneItem> getAllProductItems() {
        return bizSceneSpecList;
    }

    @Override
    public Collection<BusinessItem> getAllBusinessItems() {
        return businessItemList;
    }

    @Override
    public int countAllProductItems() {
        return bizSceneSpecMap.size();
    }

    @Override
    public int countAllBusinessItems() {
        return businessSpecMap.size();
    }
}
