package com.kuaishou.business.core.identity.match;

import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.KbfRealizeItemSessionWrap;

/**
 * @author liuzhuo07
 * Created on 2024-10-14
 * A -> MatchProcessor.process() > 很多shixian
 * Context -> extend Context
 */
public interface MatchProcessor<Item extends KbfRealizeItemSessionWrap, Context extends ExecuteContext> {

	boolean process(Item item, Context context);

}
