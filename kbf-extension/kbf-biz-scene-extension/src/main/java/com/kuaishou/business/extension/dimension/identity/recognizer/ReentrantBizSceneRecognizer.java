package com.kuaishou.business.extension.dimension.identity.recognizer;

import java.util.ArrayList;
import java.util.List;

import com.kuaishou.business.extension.dimension.MatchStatus;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.EffectScope;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午7:34
 * reentrant recognizer scan products with cache
 */
@Slf4j
public class ReentrantBizSceneRecognizer<T> implements BizSceneRecognizer<T> {

    private final List<BizSceneSessionWrap> bizSceneSessionWraps;
	private final List<String> supportTypes;

    public ReentrantBizSceneRecognizer(List<BizSceneSessionWrap> bizSceneSessionWraps, List<String> supportTypes) {
        this.bizSceneSessionWraps = bizSceneSessionWraps;
		this.supportTypes = supportTypes;
    }

    /**
     * 提供全局维度识别
     */
    @Override
    public List<BizSceneItem> recognize(T request) {
        // 生效的业务场景 包括MATCH_NOT_CACHE、MATCH
        int initSize = bizSceneSessionWraps.size();
        List<BizSceneItem> effectBizSceneSpecs = new ArrayList<>(initSize);
        //产品识别
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneSpec();
            if (bizSceneSpec.isCombo()
				|| !EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope())
				|| !supportTypes.contains(bizSceneSpec.getBizSceneType())) {
                continue;
            }
            if (MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
                if (MatchStatus.match(bizSceneSessionWrap.getGlobalMatchStatus())) {
                    effectBizSceneSpecs.add(bizSceneSpec);
                }
                continue;
            }

            MatchStatus matchStatus = matchGlobalProduct(bizSceneSessionWrap, request);
            if (MatchStatus.hasMatch(matchStatus)) {
                effectBizSceneSpecs.add(bizSceneSpec);
            }
        }

        //组合产品识别(通过已生效的产品识别)
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneSpec();
            if (!bizSceneSpec.isCombo()
                    || !EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope())
                    || MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
                continue;
            }
            MatchStatus matchStatus = matchGlobalProduct(bizSceneSessionWrap, effectBizSceneSpecs);
            if (MatchStatus.hasMatch(matchStatus)) {
                effectBizSceneSpecs.add(bizSceneSpec);
            }
        }
        return effectBizSceneSpecs;
    }

    /**
     * 提供全局 + 资源维度识别
     */
    @Override
    public List<BizSceneItem> recognize(T request, Long resourceId) {
        int initSize = bizSceneSessionWraps.size();
        List<BizSceneItem> effectBizSceneSpecs = new ArrayList<>(initSize);
        //产品识别
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneSpec();
			if (!supportTypes.contains(bizSceneSpec.getBizSceneType())) {
				continue;
			}
            boolean requestScope = EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope());
            boolean resourceScope = EffectScope.RESOURCE.equals(bizSceneSpec.getEffectScope());
            //过滤
            if (bizSceneSpec.isCombo()) {
                continue;
            }

            MatchStatus matchStatus = MatchStatus.UNKNOWN;
            if (requestScope) {
                MatchStatus globalMatchStatus = bizSceneSessionWrap.getGlobalMatchStatus();
                if (MatchStatus.hasMatchResult(globalMatchStatus)) {
                    if (MatchStatus.match(bizSceneSessionWrap.getGlobalMatchStatus())) {
                        effectBizSceneSpecs.add(bizSceneSpec);
                    }
                    continue;
                }
                //do match
                matchStatus = matchGlobalProduct(bizSceneSessionWrap, request);
            }
            if (resourceScope) {
                MatchStatus resourceMatchStatus = bizSceneSessionWrap.getResourceMatchStatus().get(resourceId);
                if (MatchStatus.hasMatchResult(resourceMatchStatus)) {
                    if (MatchStatus.match(resourceMatchStatus)) {
                        effectBizSceneSpecs.add(bizSceneSpec);
                    }
                    continue;
                }
                //do match
                matchStatus = matchResourceProduct(bizSceneSessionWrap, resourceId, request);
            }

            if (MatchStatus.hasMatch(matchStatus)) {
                effectBizSceneSpecs.add(bizSceneSpec);
            }
        }

        //组合产品识别(通过已生效的产品识别)
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneSpec();
            if (!bizSceneSpec.isCombo()) {
                continue;
            }
            boolean requestScope = EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope());
            boolean resourceScope = EffectScope.RESOURCE.equals(bizSceneSpec.getEffectScope());
            if (requestScope && MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
                continue;
            }
            if (resourceScope && MatchStatus.hasMatchResult(bizSceneSessionWrap.getResourceMatchStatus().get(resourceId))) {
                continue;
            }
            MatchStatus matchStatus = MatchStatus.UNKNOWN;
            if (resourceScope) {
                bizSceneSessionWrap.getLock().lock();
                try {
                    //双重校验检查
                    if (!MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
                        matchStatus = bizSceneSessionWrap.getBizSceneSpec().getDefinition().match(effectBizSceneSpecs);
                        bizSceneSessionWrap.getResourceMatchStatus().put(resourceId, matchStatus);
                    }
                } finally {
                    bizSceneSessionWrap.getLock().unlock();
                }
            }
            if (requestScope) {
                matchStatus = matchGlobalProduct(bizSceneSessionWrap, effectBizSceneSpecs);
            }
            boolean match = MatchStatus.hasMatch(matchStatus);
            if (match) {
                effectBizSceneSpecs.add(bizSceneSpec);
            }
        }

        return effectBizSceneSpecs;
    }

    private MatchStatus matchResourceProduct(BizSceneSessionWrap bizSceneSessionWrap, Long resourceId, Object req) {
        MatchStatus matchStatus = MatchStatus.UNKNOWN;
        bizSceneSessionWrap.getLock().lock();
        try {
            //双重校验检查
            if (!MatchStatus.hasMatchResult(bizSceneSessionWrap.getResourceMatchStatus().get(resourceId))) {
                matchStatus = bizSceneSessionWrap.getBizSceneSpec().getDefinition().match(req, resourceId);
                bizSceneSessionWrap.getResourceMatchStatus().put(resourceId, matchStatus);
            }
        } finally {
            bizSceneSessionWrap.getLock().unlock();
        }
        return matchStatus;
    }


    private MatchStatus matchGlobalProduct(BizSceneSessionWrap bizSceneSessionWrap, Object req) {
        MatchStatus matchStatus = MatchStatus.UNKNOWN;
        bizSceneSessionWrap.getLock().lock();
        try {
            //双重校验检查
            if (!MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
                matchStatus = bizSceneSessionWrap.getBizSceneSpec().getDefinition().match(req);
                bizSceneSessionWrap.setGlobalMatchStatus(matchStatus);
            }
        } finally {
            bizSceneSessionWrap.getLock().unlock();
        }
        return matchStatus;
    }

}
