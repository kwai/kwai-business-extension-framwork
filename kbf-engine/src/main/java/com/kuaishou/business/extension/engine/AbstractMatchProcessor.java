package com.kuaishou.business.extension.engine;

import org.apache.commons.lang3.tuple.Pair;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.KbfRealizeItemSessionWrap;
import com.kuaishou.business.core.identity.Match;
import com.kuaishou.business.core.identity.match.MatchProcessor;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 */
public abstract class AbstractMatchProcessor<Item extends KbfRealizeItemSessionWrap, Context extends ExecuteContext>
	implements MatchProcessor<Item, Context> {
	@Override
	public boolean process(Item item, Context context) {
		Pair<Boolean, Boolean> checkResult = check(item, context);
		if (checkResult.getLeft()) {
			return checkResult.getRight();
		}
		beforeMatch(item);
		try {
			Match matchResult = match(item, context);
			afterMatch(item, matchResult, context);
			return isMatch(matchResult);
		} finally {
			afterCompletion(item);
		}
	}

	/**
	 * Users can customize strategy to determine whether to return early and the return result.
	 */
	protected abstract Pair<Boolean, Boolean> check(Item item, Context context);

	protected void beforeMatch(Item item) {

	}

	protected abstract Match match(Item item, Context context);

	protected void afterMatch(Item item, Match match, Context context) {

	}

	protected void afterCompletion(Item item) {

	}

	protected abstract boolean isMatch(Match match);
}
