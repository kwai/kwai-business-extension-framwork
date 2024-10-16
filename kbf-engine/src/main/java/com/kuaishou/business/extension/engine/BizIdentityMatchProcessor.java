package com.kuaishou.business.extension.engine;

import org.apache.commons.lang3.tuple.Pair;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.Match;
import com.kuaishou.business.core.identity.manage.BusinessItem;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
public class BizIdentityMatchProcessor extends AbstractMatchProcessor<DefaultBizIdentitySessionWrap, ExecuteContext> {

	@Override
	public Pair<Boolean, Boolean> check(DefaultBizIdentitySessionWrap itemSessionWrap, ExecuteContext context) {
		return Pair.of(false, false);
	}

	@Override
	public Match match(DefaultBizIdentitySessionWrap itemSessionWrap, ExecuteContext context) {
		BusinessItem item = itemSessionWrap.getItem();
		return item.getDefinition().match(context.getRequest());
	}

	@Override
	public boolean isMatch(Match match) {
		return match.hasMatch();
	}
}
