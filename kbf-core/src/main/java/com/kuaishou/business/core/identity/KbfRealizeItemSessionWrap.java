package com.kuaishou.business.core.identity;

import com.kuaishou.business.core.identity.manage.KbfRealizeItem;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
public interface KbfRealizeItemSessionWrap<Item extends KbfRealizeItem> {

	Item unwrap();
}
