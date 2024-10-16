package com.kuaishou.business.extension.dimension.identity.match;

import org.apache.commons.lang3.tuple.Pair;

import com.kuaishou.business.core.identity.Match;
import com.kuaishou.business.extension.dimension.BizSceneExecuteContext;
import com.kuaishou.business.extension.dimension.MatchStatus;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.EffectScope;
import com.kuaishou.business.extension.engine.AbstractMatchProcessor;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
public class BizSceneMatchProcessor extends AbstractMatchProcessor<BizSceneSessionWrap, BizSceneExecuteContext> {


	@Override
	protected Pair<Boolean, Boolean> check(BizSceneSessionWrap bizSceneSessionWrap, BizSceneExecuteContext context) {
		MatchStatus matchStatus = getCurrentMatchStatus(bizSceneSessionWrap, context);
		if (MatchStatus.match(matchStatus)) {
			return Pair.of(true, true);
		}
		if (MatchStatus.notMatch(matchStatus)) {
			return Pair.of(true, false);
		}
		return Pair.of(false, false);
	}

	@Override
	protected void beforeMatch(BizSceneSessionWrap bizSceneSessionWrap) {
		bizSceneSessionWrap.getLock().lock();
	}

	@Override
	protected Match match(BizSceneSessionWrap bizSceneSessionWrap, BizSceneExecuteContext context) {
		BizSceneItem item = bizSceneSessionWrap.getBizSceneItem();
		MatchStatus matchStatus = getCurrentMatchStatus(bizSceneSessionWrap, context);
		if (MatchStatus.hasMatchResult(matchStatus)) {
			return matchStatus;
		}
		if (item.isCombo()) {
			return item.getDefinition().match(context.getEffectBizScenes());
		}

		if (EffectScope.RESOURCE.equals(item.getEffectScope())) {
			return item.getDefinition().match(context.getRequest(), context.getResourceId());
		} else {
			return item.getDefinition().match(context.getRequest());
		}
	}

	@Override
	protected void afterMatch(BizSceneSessionWrap bizSceneSessionWrap, Match match, BizSceneExecuteContext context) {
		BizSceneItem item = bizSceneSessionWrap.getBizSceneItem();
		if (EffectScope.RESOURCE.equals(item.getEffectScope())) {
			Long resourceId = context.getResourceId();
			bizSceneSessionWrap.getResourceMatchStatus().put(resourceId, (MatchStatus) match);
		} else {
			bizSceneSessionWrap.setGlobalMatchStatus((MatchStatus) match);
		}
	}

	@Override
	protected void afterCompletion(BizSceneSessionWrap bizSceneSessionWrap) {
		bizSceneSessionWrap.getLock().unlock();
	}

	@Override
	protected boolean isMatch(Match match) {
		return match.hasMatch();
	}

	private static MatchStatus getCurrentMatchStatus(BizSceneSessionWrap bizSceneSessionWrap,
		BizSceneExecuteContext context) {
		EffectScope effectScope = bizSceneSessionWrap.getBizSceneItem().getEffectScope();
		if (EffectScope.RESOURCE.equals(effectScope)) {
			return bizSceneSessionWrap.getResourceMatchStatus().get(context.getResourceId());
		} else {
			return bizSceneSessionWrap.getGlobalMatchStatus();
		}
	}
}
