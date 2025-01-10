package com.kuaishou.business.extension.engine;

import com.kuaishou.business.core.identity.KbfRealizeItemSessionWrap;
import com.kuaishou.business.core.identity.manage.BusinessItem;

import lombok.Data;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
@Data
public class DefaultBizIdentitySessionWrap implements KbfRealizeItemSessionWrap<BusinessItem> {

	private BusinessItem item;

	@Override
	public BusinessItem unwrap() {
		return item;
	}
}
