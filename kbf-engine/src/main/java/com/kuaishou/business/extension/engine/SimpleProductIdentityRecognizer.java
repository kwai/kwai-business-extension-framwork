package com.kuaishou.business.extension.engine;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.kuaishou.business.core.context.ExecuteContext;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.product.DefaultProductSessionWrap;
import com.kuaishou.business.core.identity.product.ProductIdentityRecognizer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 上午12:54
 * simple recognizer scan all products every time
 */
@Slf4j
public class SimpleProductIdentityRecognizer implements ProductIdentityRecognizer<ExecuteContext, Set<NormalProductItem>> {

    private final List<DefaultProductSessionWrap> productSessionWraps;
	private final ProductMatchProcessor productMatchProcessor;

    public SimpleProductIdentityRecognizer(List<DefaultProductSessionWrap> productSessionWraps,
            ProductMatchProcessor productMatchProcessor) {
        this.productSessionWraps = productSessionWraps;
        this.productMatchProcessor = productMatchProcessor;
    }

    @Override
    public Set<NormalProductItem> recognize(ExecuteContext context) {
        Set<NormalProductItem> newEffectProducts = Sets.newHashSet();

		for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
			if (!productSessionWrap.getItem().isCombo()) {
				processMatch(productSessionWrap, newEffectProducts, context);
			}
		}
		context.setEffectProducts(newEffectProducts);
		//识别组合产品
		for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
			if (productSessionWrap.getItem().isCombo()) {
				processMatch(productSessionWrap, newEffectProducts, context);
			}
		}

        return newEffectProducts;
    }

	private void processMatch(DefaultProductSessionWrap productSessionWrap,
		Set<NormalProductItem> newEffectProducts, ExecuteContext context) {
		boolean match = productMatchProcessor.process(productSessionWrap, context);
		if (match) {
			newEffectProducts.add(productSessionWrap.getItem());
		}
	}

}
