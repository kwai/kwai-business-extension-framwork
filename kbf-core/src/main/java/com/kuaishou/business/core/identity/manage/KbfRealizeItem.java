package com.kuaishou.business.core.identity.manage;

import java.util.List;

import com.kuaishou.business.core.extpoint.ExtPointRealizeWrap;

/**
 * KBF实现项
 */
public interface KbfRealizeItem extends KbfItem {

    /**
     * 实例
     */
    List<ExtPointRealizeWrap> getExtPointRealizes();

}
