package com.kuaishou.business.extension.dimension.session;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kuaishou.business.core.context.KBizContext;
import com.kuaishou.business.core.exception.KSessionException;
import com.kuaishou.business.core.identity.manage.BusinessItem;
import com.kuaishou.business.core.session.KSessionScope;
import com.kuaishou.business.extension.dimension.MatchStatus;
import com.kuaishou.business.extension.dimension.identity.BizSceneSessionWrap;
import com.kuaishou.business.extension.dimension.identity.BizSceneItem;
import com.kuaishou.business.extension.dimension.identity.EffectScope;

/**
 * 一个 KSessionScope 对应一个KProcess的执行
 */
public final class BizSceneKSessionScope {

    public static BizSceneKSession getCurrentSession() {
        return (BizSceneKSession) KSessionScope.getCurrentSession().get();
    }

    /**
     * @return 当前垂直业务身份code
     */
    public static String getCurrentBizCode() {
        return getCurrentSession().getBizCode();
    }

    /**
     * @return 当前业务上下文
     */
    public static KBizContext getCurrentContext() {
        return getCurrentSession().getContext();
    }

	/**
	 * @param kBizContext 业务上下文
	 */
	public static void setBizContext(KBizContext kBizContext) {
		if (Objects.isNull(kBizContext)) {
			throw new KSessionException("[kbf] BizSceneKSessionScope setBizContext context is null");
		}
		getCurrentSession().setContext(kBizContext);
	}

    /**
     * @return 判断session是否已创建
     */
    public static boolean init() {
        return KSessionScope.init();
    }

    /**
     * @return 获取当前垂直业务项
     */
    public static BusinessItem getCurrentBusiness() {
        return getCurrentSession().getBusinessItem();
    }

    /**
     * @return 获取全局作用域生效维度
     */
    public static Set<BizSceneItem> getEffectProducts() {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Set<BizSceneItem> result = Sets.newHashSet();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.REQUEST.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            if (MatchStatus.match(bizSceneSessionWrap.getGlobalMatchStatus())) {
                result.add(bizSceneSessionWrap.getBizSceneItem());
            }
        }
        return result;
    }

    /**
     * @return 获取全局作用域未命中维度
     */
    public static Set<BizSceneItem> getIneffectProducts() {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Set<BizSceneItem> result = Sets.newHashSet();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.REQUEST.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            if (MatchStatus.notMatch(bizSceneSessionWrap.getGlobalMatchStatus())) {
                result.add(bizSceneSessionWrap.getBizSceneItem());
            }
        }
        return result;
    }

    public static List<BizSceneSessionWrap> getBizScenes() {
        return getCurrentSession().getBizSceneSessionWraps();
    }

    /**
     * @return 获取所有资源作用域生效维度
     */
    public static Map<Long, Set<BizSceneItem>> getEffectResourceProducts() {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Map<Long, Set<BizSceneItem>> result = Maps.newHashMap();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.RESOURCE.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            Map<Long, MatchStatus> resourceMatchBizScenes = bizSceneSessionWrap.getResourceMatchStatus();
            for (Entry<Long, MatchStatus> e : resourceMatchBizScenes.entrySet()) {
                if (MatchStatus.match(e.getValue())) {
                    result.computeIfAbsent(e.getKey(), l -> Sets.newHashSet()).add(bizSceneSessionWrap.getBizSceneItem());
                }
            }
        }
        return result;
    }

    /**
     * @param resourceId 资源id
     * @return 所有资源作用域生效维度
     */
    public static Set<BizSceneItem> getEffectResourceProduct(Long resourceId) {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Set<BizSceneItem> result = Sets.newHashSet();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.RESOURCE.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            if (MatchStatus.match(bizSceneSessionWrap.getResourceMatchStatus().get(resourceId))) {
                result.add(bizSceneSessionWrap.getBizSceneItem());
            }
        }
        return result;
    }

    /**
     * @return 全部资源作用域未命中维度
     */
    public static Map<Long, Set<BizSceneItem>> getIneffectResourceProducts() {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Map<Long, Set<BizSceneItem>> result = Maps.newHashMap();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.RESOURCE.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            Map<Long, MatchStatus> resourceMatchBizScenes = bizSceneSessionWrap.getResourceMatchStatus();
            for (Entry<Long, MatchStatus> e : resourceMatchBizScenes.entrySet()) {
                if (MatchStatus.notMatch(e.getValue())) {
                    result.computeIfAbsent(e.getKey(), l -> Sets.newHashSet()).add(bizSceneSessionWrap.getBizSceneItem());
                }
            }
        }
        return result;
    }

    /**
     * @param resourceId 资源id
     * @return 资源作用域未命中维度
     */
    public static Set<BizSceneItem> getIneffectResourceProduct(Long resourceId) {
        List<BizSceneSessionWrap> bizSceneSessionWraps = getCurrentSession().getBizSceneSessionWraps();
        Set<BizSceneItem> result = Sets.newHashSet();
        for (BizSceneSessionWrap bizSceneSessionWrap : bizSceneSessionWraps) {
            if (!EffectScope.RESOURCE.equals(bizSceneSessionWrap.getBizSceneItem().getEffectScope())) {
                continue;
            }
            if (MatchStatus.notMatch(bizSceneSessionWrap.getResourceMatchStatus().get(resourceId))) {
                result.add(bizSceneSessionWrap.getBizSceneItem());
            }
        }
        return result;
    }

    /**
     * @param enable 是否允许执行扩展点
     */
    public static void setExecExtPoint(Boolean enable) {
        getCurrentSession().setExecExtPoint(enable);
    }

    /**
     * @return 是否允许执行扩展点
     */
    public static boolean isExecExtPoint() {
        return getCurrentSession().getExecExtPoint();
    }
}
