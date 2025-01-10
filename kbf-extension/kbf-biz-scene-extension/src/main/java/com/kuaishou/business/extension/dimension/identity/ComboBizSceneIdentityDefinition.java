package com.kuaishou.business.extension.dimension.identity;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kuaishou.business.core.identity.Match;
import com.kuaishou.business.extension.dimension.MatchStatus;

import lombok.AllArgsConstructor;

/**
 * @author liuzhuo
 * Created on 2023-03-19 下午12:47
 */
@AllArgsConstructor
class ComboBizSceneIdentityDefinition implements BizSceneIdentityDefinition<Collection<BizSceneItem>> {

    private final List<String> dimensionComposition;

    @Override
    public MatchStatus match(Collection<BizSceneItem> request) {
        Set<String> collect = request.stream().map(BizSceneItem::code).collect(Collectors.toSet());
        if (collect.containsAll(dimensionComposition)) {
            return MatchStatus.MATCH;
        }
        return MatchStatus.UNKNOWN;
    }

	@Override
	public Match match(Object request, Long resourceId) {
		return match((Collection<BizSceneItem>) request);
	}
}
