package com.kuaishou.business.extension.engine;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.product.ProductSessionWrap;
import com.kuaishou.business.core.identity.product.ProductIdentityRecognizer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhuo
 * Created on 2023-03-17 上午12:54
 * simple recognizer scan all products every time
 */
@Slf4j
public class SimpleProductIdentityRecognizer<T> implements ProductIdentityRecognizer<T, Set<NormalProductItem>> {

    private final List<ProductSessionWrap> productSessionWraps;

    public SimpleProductIdentityRecognizer(List<ProductSessionWrap> productSessionWraps) {
        this.productSessionWraps = productSessionWraps;
    }

    @Override
    public Set<NormalProductItem> recognize(T request) {
        Set<NormalProductItem> newEffectProducts = Sets.newHashSet();

        for (ProductSessionWrap productSessionWrap : productSessionWraps) {
            if (productSessionWrap.getProductSpec().isCombo()) {
                continue;
            }
            MatchResult match = productSessionWrap.getProductSpec().getDefinition().match(request);
            productSessionWrap.setMatchResult(match);
            if (MatchResult.match(match)) {
                newEffectProducts.add(productSessionWrap.getProductSpec());
            }
        }
        //识别组合产品
        for (ProductSessionWrap productSessionWrap : productSessionWraps) {
            if (!productSessionWrap.getProductSpec().isCombo()) {
                continue;
            }
            MatchResult match = productSessionWrap.getProductSpec().getDefinition().match(newEffectProducts);
            productSessionWrap.setMatchResult(match);
            if (MatchResult.match(match)) {
                newEffectProducts.add(productSessionWrap.getProductSpec());
            }
        }

        return newEffectProducts;
    }

}
