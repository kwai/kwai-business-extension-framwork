package com.kuaishou.business.extension.dimension.identity;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Maps;
import com.kuaishou.business.core.identity.KbfRealizeItemSessionWrap;
import com.kuaishou.business.extension.dimension.MatchStatus;

import lombok.Data;

/**
 * @author liuzhuo
 * Created on 2023-05-14 16:56
 */
@Data
public class BizSceneSessionWrap implements KbfRealizeItemSessionWrap<BizSceneItem> {

    private BizSceneItem bizSceneItem;

    /**
     * 全局维度产品匹配状态
     */
    private MatchStatus globalMatchStatus = MatchStatus.UNKNOWN;

    /**
     * 资源维度产品匹配状态
     * {"1" : "MATCH"}
     */
    private Map<Long, MatchStatus> resourceMatchStatus = Maps.newHashMap();

    private ReentrantLock lock = new ReentrantLock();

	@Override
	public BizSceneItem unwrap() {
		return bizSceneItem;
	}
}
