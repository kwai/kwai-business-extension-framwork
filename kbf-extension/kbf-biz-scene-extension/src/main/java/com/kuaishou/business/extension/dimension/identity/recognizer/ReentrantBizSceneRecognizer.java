package com.kuaishou.business.extension.dimension.identity.recognizer;

import java.util.ArrayList;
import java.util.List;

import com.kuaishou.business.core.identity.product.ProductIdentityRecognizer;
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
public class ReentrantBizSceneRecognizer<T> implements ProductIdentityRecognizer<T, List<BizSceneItem>> {

    private final List<BizSceneSessionWrap> bizSceneSessionWraps;
	private final List<String> supportTypes;
	private final Long resourceId;
	private final Boolean isResourceRequest;

    public ReentrantBizSceneRecognizer(List<BizSceneSessionWrap> bizSceneSessionWraps, List<String> supportTypes,
            Long resourceId, Boolean isResourceRequest) {
        this.bizSceneSessionWraps = bizSceneSessionWraps;
		this.supportTypes = supportTypes;
        this.resourceId = resourceId;
		this.isResourceRequest = isResourceRequest;
    }

	@Override
	public List<BizSceneItem> recognize(T request) {
		if (isResourceRequest) {
			return recognizeAllProducts(request);
		} else {
			return recognizeGlobalProducts(request);
		}
	}

	public List<BizSceneItem> recognizeGlobalProducts(T request) {
		int initSize = bizSceneSessionWraps.size();
		List<BizSceneItem> effectBizSceneItemList = new ArrayList<>(initSize);
		//产品识别
		for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
			BizSceneItem item = bizSceneSessionWrap.unwrap();
			if (item.isCombo() || !EffectScope.REQUEST.equals(item.getEffectScope())
				|| !supportTypes.contains(item.getBizSceneType())) {
				continue;
			}
			matchGlobalProduct(bizSceneSessionWrap, effectBizSceneItemList, request);
		}

		//组合产品识别(通过已生效的产品识别)
		for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
			BizSceneItem item = bizSceneSessionWrap.unwrap();
			if (!item.isCombo() || !EffectScope.REQUEST.equals(item.getEffectScope())) {
				continue;
			}
			matchGlobalProduct(bizSceneSessionWrap, effectBizSceneItemList);
		}
		return effectBizSceneItemList;
	}

	public List<BizSceneItem> recognizeAllProducts(T request) {
		int initSize = bizSceneSessionWraps.size();
		List<BizSceneItem> effectBizSceneItemList = new ArrayList<>(initSize);
		//产品识别
		for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
			BizSceneItem bizSceneItem = bizSceneSessionWrap.unwrap();
			if (bizSceneItem.isCombo() || !supportTypes.contains(bizSceneItem.getBizSceneType())) {
				continue;
			}

			if (EffectScope.RESOURCE.equals(bizSceneItem.getEffectScope())) {
				matchResourceProduct(bizSceneSessionWrap, effectBizSceneItemList, resourceId, request);
			} else {
				matchGlobalProduct(bizSceneSessionWrap, effectBizSceneItemList, request);
			}
		}

		//组合产品识别(通过已生效的产品识别)
		for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
			BizSceneItem bizSceneSpec = bizSceneSessionWrap.unwrap();
			if (!bizSceneSpec.isCombo()) {
				continue;
			}

			if (EffectScope.RESOURCE.equals(bizSceneSpec.getEffectScope())) {
				matchResourceProduct(bizSceneSessionWrap, effectBizSceneItemList, resourceId);
			} else {
				matchGlobalProduct(bizSceneSessionWrap, effectBizSceneItemList);
			}
		}

		return effectBizSceneItemList;
	}

	private void matchResourceProduct(BizSceneSessionWrap bizSceneSessionWrap,
		List<BizSceneItem> effectBizSceneItemList, Long resourceId, Object... request) {
		BizSceneItem item = bizSceneSessionWrap.unwrap();

		MatchStatus curMatchStatus = bizSceneSessionWrap.getResourceMatchStatus().get(resourceId);
		if (MatchStatus.match(curMatchStatus)) {
			effectBizSceneItemList.add(item);
			return;
		}
		if (MatchStatus.notMatch(curMatchStatus)) {
			return;
		}

		MatchStatus matchStatus;
		if (request.length > 0) {
			matchStatus = doMatchResourceProduct(bizSceneSessionWrap, resourceId, request[0]);
		} else {
			matchStatus = doMatchResourceProduct(bizSceneSessionWrap, resourceId, effectBizSceneItemList);
		}
		if (MatchStatus.hasMatch(matchStatus)) {
			effectBizSceneItemList.add(item);
		}
	}

	private MatchStatus doMatchResourceProduct(BizSceneSessionWrap bizSceneSessionWrap, Long resourceId, Object req) {
		MatchStatus matchStatus = MatchStatus.UNKNOWN;
		bizSceneSessionWrap.getLock().lock();
		try {
			//双重校验检查
			if (!MatchStatus.hasMatchResult(bizSceneSessionWrap.getResourceMatchStatus().get(resourceId))) {
				matchStatus = (MatchStatus) bizSceneSessionWrap.unwrap().getDefinition().match(req, resourceId);
				bizSceneSessionWrap.getResourceMatchStatus().put(resourceId, matchStatus);
			}
		} finally {
			bizSceneSessionWrap.getLock().unlock();
		}
		return matchStatus;
	}


	private void matchGlobalProduct(BizSceneSessionWrap bizSceneSessionWrap, List<BizSceneItem> effectBizSceneItemList, Object... request) {
		BizSceneItem item = bizSceneSessionWrap.unwrap();
		MatchStatus curMatchStatus = bizSceneSessionWrap.getGlobalMatchStatus();
		if (MatchStatus.match(curMatchStatus)) {
			effectBizSceneItemList.add(item);
			return;
		}
		if (MatchStatus.notMatch(curMatchStatus)) {
			return;
		}

		MatchStatus matchStatus;
		if (request.length > 0) {
			matchStatus = doMatchGlobalProduct(bizSceneSessionWrap, request[0]);
		} else {
			matchStatus = doMatchGlobalProduct(bizSceneSessionWrap, effectBizSceneItemList);
		}
		if (MatchStatus.hasMatch(matchStatus)) {
			effectBizSceneItemList.add(item);
		}
	}

	private MatchStatus doMatchGlobalProduct(BizSceneSessionWrap bizSceneSessionWrap, Object req) {
		MatchStatus matchStatus = MatchStatus.UNKNOWN;
		bizSceneSessionWrap.getLock().lock();
		try {
			//双重校验检查
			if (!MatchStatus.hasMatchResult(bizSceneSessionWrap.getGlobalMatchStatus())) {
				matchStatus = (MatchStatus) bizSceneSessionWrap.unwrap().getDefinition().match(req);
				bizSceneSessionWrap.setGlobalMatchStatus(matchStatus);
			}
		} finally {
			bizSceneSessionWrap.getLock().unlock();
		}
		return matchStatus;
	}

}
