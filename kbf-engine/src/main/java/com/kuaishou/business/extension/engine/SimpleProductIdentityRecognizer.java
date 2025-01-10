package com.kuaishou.business.extension.engine;

import java.util.List;

import com.google.common.collect.Lists;
import com.kuaishou.business.core.identity.MatchResult;
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
public class SimpleProductIdentityRecognizer<T> implements ProductIdentityRecognizer<T, List<NormalProductItem>> {

    private final List<DefaultProductSessionWrap> productSessionWraps;

    public SimpleProductIdentityRecognizer(List<DefaultProductSessionWrap> productSessionWraps) {
        this.productSessionWraps = productSessionWraps;
    }

    @Override
    public List<NormalProductItem> recognize(T request) {
        List<NormalProductItem> newEffectProducts = Lists.newArrayList();

		for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
			if (productSessionWrap.getItem().isCombo()) {
				continue;
			}
			matchProduct(productSessionWrap, newEffectProducts, request);
		}
		//识别组合产品
		for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
			if (!productSessionWrap.getItem().isCombo()) {
				continue;
			}
			matchProduct(productSessionWrap, newEffectProducts);
		}

        return newEffectProducts;
    }

	private static void matchProduct(DefaultProductSessionWrap productSessionWrap,
		List<NormalProductItem> newEffectProducts, Object... request) {
		MatchResult match;
		if (request.length > 0) {
			match = productSessionWrap.unwrap().getDefinition().match(request[0]);
		} else {
			match = productSessionWrap.unwrap().getDefinition().match(newEffectProducts);
		}
		productSessionWrap.setMatchResult(match);
		if (MatchResult.match(match)) {
			newEffectProducts.add(productSessionWrap.unwrap());
		}
	}

}
