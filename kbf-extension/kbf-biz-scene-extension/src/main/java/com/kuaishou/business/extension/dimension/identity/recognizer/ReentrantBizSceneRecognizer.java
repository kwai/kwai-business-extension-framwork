package com.kuaishou.business.extension.dimension.identity.recognizer;

import java.util.ArrayList;
import java.util.List;

import com.kuaishou.business.extension.dimension.BizSceneExecuteContext;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.EffectScope;
import com.kuaishou.business.extension.dimension.identity.match.BizSceneMatchProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-04-05 下午7:34
 * reentrant recognizer scan products with cache
 */
@Slf4j
public class ReentrantBizSceneRecognizer implements BizSceneRecognizer<BizSceneExecuteContext> {

    private final List<BizSceneSessionWrap> bizSceneSessionWraps;
	private final List<String> supportTypes;
	private final BizSceneMatchProcessor bizSceneMatchProcessor;

    public ReentrantBizSceneRecognizer(List<BizSceneSessionWrap> bizSceneSessionWraps, List<String> supportTypes,
            BizSceneMatchProcessor bizSceneMatchProcessor) {
        this.bizSceneSessionWraps = bizSceneSessionWraps;
		this.supportTypes = supportTypes;
        this.bizSceneMatchProcessor = bizSceneMatchProcessor;
    }

    /**
     * 提供全局维度识别
     */
    @Override
    public List<BizSceneItem> recognize(BizSceneExecuteContext context) {
        // 生效的业务场景 包括MATCH_NOT_CACHE、MATCH
        int initSize = bizSceneSessionWraps.size();
        List<BizSceneItem> effectBizSceneSpecs = new ArrayList<>(initSize);

        //产品识别
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneItem();
            if (bizSceneSpec.isCombo()
				|| !EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope())
				|| !supportTypes.contains(bizSceneSpec.getBizSceneType())) {
                continue;
            }

			boolean match = bizSceneMatchProcessor.process(bizSceneSessionWrap, context);
			if (match) {
				effectBizSceneSpecs.add(bizSceneSpec);
			}
        }

        //组合产品识别(通过已生效的产品识别)
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneItem();
            if (!bizSceneSpec.isCombo()
                    || !EffectScope.REQUEST.equals(bizSceneSpec.getEffectScope())) {
                continue;
            }

			boolean match = bizSceneMatchProcessor.process(bizSceneSessionWrap, context);
			if (match) {
				effectBizSceneSpecs.add(bizSceneSpec);
			}
        }
        return effectBizSceneSpecs;
    }

    /**
     * 提供全局 + 资源维度识别
     */
    @Override
    public List<BizSceneItem> recognize(BizSceneExecuteContext context, Long resourceId) {
        int initSize = bizSceneSessionWraps.size();
        List<BizSceneItem> effectBizSceneSpecs = new ArrayList<>(initSize);
        //产品识别
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneItem();
			if (!supportTypes.contains(bizSceneSpec.getBizSceneType())) {
				continue;
			}
            //过滤
            if (bizSceneSpec.isCombo()) {
                continue;
            }

			boolean match = bizSceneMatchProcessor.process(bizSceneSessionWrap, context);
			if (match) {
				effectBizSceneSpecs.add(bizSceneSpec);
			}
        }
		context.setEffectBizScenes(effectBizSceneSpecs);
        //组合产品识别(通过已生效的产品识别)
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            BizSceneItem bizSceneSpec = bizSceneSessionWrap.getBizSceneItem();
            if (!bizSceneSpec.isCombo()) {
                continue;
            }

			boolean match = bizSceneMatchProcessor.process(bizSceneSessionWrap, context);
			if (match) {
				effectBizSceneSpecs.add(bizSceneSpec);
			}
        }

        return effectBizSceneSpecs;
    }
}
