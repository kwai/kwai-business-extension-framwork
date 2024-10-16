package com.kuaishou.business.extension.engine;

import org.apache.commons.lang3.tuple.Pair;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.Match;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.product.DefaultProductSessionWrap;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
public class ProductMatchProcessor extends AbstractMatchProcessor<DefaultProductSessionWrap, ExecuteContext> {

	@Override
	public void afterMatch(DefaultProductSessionWrap item, Match match, ExecuteContext context) {
		item.setMatchResult((MatchResult) match);
	}

	@Override
	public boolean isMatch(Match match) {
		return match.hasMatch();
	}

	@Override
	public Pair<Boolean, Boolean> check(DefaultProductSessionWrap defaultProductSessionWrap, ExecuteContext context) {
		return Pair.of(false, false);
	}

	@Override
	public Match match(DefaultProductSessionWrap defaultProductSessionWrap, ExecuteContext context) {
		NormalProductItem item = defaultProductSessionWrap.getItem();
		if (item.isCombo()) {
			return item.getDefinition().match(context.getEffectProducts());
		} else {
			return item.getDefinition().match(context.getRequest());
		}
	}
}
