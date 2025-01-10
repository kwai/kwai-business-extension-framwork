package com.kuaishou.business.core.identity.product;

import com.kuaishou.business.core.identity.KbfRealizeItemSessionWrap;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.manage.NormalProductItem;

import lombok.Data;

/**
 * @author liuzhuo
 * Created on 2023-05-14 16:55
 */
@Data
public class DefaultProductSessionWrap implements KbfRealizeItemSessionWrap<NormalProductItem> {

    private NormalProductItem item;

    /**
     * 匹配结果
     */
    private MatchResult matchResult = MatchResult.UNKNOWN;

	@Override
	public NormalProductItem unwrap() {
		return item;
	}
}
