package com.kuaishou.business.core.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;
import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.identity.MatchResult;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.identity.manage.NormalProductItem;
import com.kuaishou.business.core.identity.product.DefaultProductSessionWrap;

import lombok.Getter;
import lombok.Setter;

/**
 * 一个 KSessionScope 对应一个KProcess的执行
 */
public final class KSessionScope {

    @Setter
    private KSession kSession;

    @Getter
    @Setter
    private List<String> spiTraces = new ArrayList<>(20);

    private static final ThreadLocal<KSessionScope> SCOPE = InheritableThreadLocal.withInitial(KSessionScope::new);

    public static Optional<KSession> getCurrentSession() {
        return Optional.ofNullable(SCOPE.get().kSession);
    }

    public static String getCurrentBizCode() {
        return getCurrentSession().map(KSession::getBizCode).orElse(null);
    }

    public static KBizContext getCurrentContext() {
        return getCurrentSession().map(KSession::getContext).orElse(null);
    }

    public static void initWithSession(KSession session) {
        SCOPE.get().setKSession(session);
    }

    public static boolean init() {
        return Objects.nonNull(getCurrentContext());
    }

    public static void destroy() {
        SCOPE.remove();
    }

    public static void traceSpi(Class<?> spi) {
        SCOPE.get().getSpiTraces().add(spi.getCanonicalName());
    }

    public static BusinessItem getCurrentBusiness() {
        return SCOPE.get().kSession.getBusinessItem();
    }

    public static List<DefaultProductSessionWrap> getProducts() {
        return SCOPE.get().kSession.getProductSessionWraps();
    }

    public static Set<NormalProductItem> getEffectProducts() {
        List<DefaultProductSessionWrap> productSessionWraps = SCOPE.get().kSession.getProductSessionWraps();
        Set<NormalProductItem> productSpecs = Sets.newHashSet();
        for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
            if (MatchResult.match(productSessionWrap.getMatchResult())) {
                productSpecs.add(productSessionWrap.getItem());
            }
        }
        return productSpecs;
    }


    public static Set<NormalProductItem> getIneffectProducts() {
        List<DefaultProductSessionWrap> productSessionWraps = SCOPE.get().kSession.getProductSessionWraps();
        Set<NormalProductItem> productSpecs = Sets.newHashSet();
        for (DefaultProductSessionWrap productSessionWrap : productSessionWraps) {
            if (MatchResult.notMatch(productSessionWrap.getMatchResult())) {
                productSpecs.add(productSessionWrap.getItem());
            }
        }
        return productSpecs;
    }

    public static void setExecExtPoint(Boolean enable) {
        SCOPE.get().kSession.setExecExtPoint(enable);
    }

    public static boolean isExecExtPoint() {
        return SCOPE.get().kSession.getExecExtPoint();
    }
}
