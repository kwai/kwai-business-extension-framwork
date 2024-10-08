package com.kuaishou.business.extension.dimension;

import java.util.Objects;

import com.kuaishou.business.core.identity.Match;

/**
 * @author zhangqinxian <zhangqinxian@kuaishou.com>
 * Created on 2023-03-18
 */
public enum MatchStatus implements Match {
    /**
     * 未知状态 -> 代表信息缺失,还可以再识别
     */
    UNKNOWN,
    /**
     * 匹配
     */
    MATCH,
    /**
     * 不匹配 -> 确定不匹配,后续不再识别
     */
    NOT_MATCH,

    /**
     * 匹配不缓存
     */
    MATCH_NOT_CACHE,

    /**
     * 不匹配不缓存 -> 代表信息缺失,还可以再识别
     */
    NOT_MATCH_NOT_CACHE;

    public static MatchStatus ofBoolean(boolean match) {
        return match ? MATCH : NOT_MATCH;
    }

    public static boolean match(MatchStatus matchStatus) {
        return Objects.equals(MATCH, matchStatus);
    }

    public static boolean notMatch(MatchStatus matchStatus) {
        return Objects.equals(NOT_MATCH, matchStatus);
    }

    public static boolean hasMatchResult(MatchStatus matchStatus) {
        return match(matchStatus) || notMatch(matchStatus);
    }

    public static boolean hasMatch(MatchStatus matchStatus) {
        return match(matchStatus) || matchNotCache(matchStatus);
    }

    public static boolean matchNotCache(MatchStatus matchStatus) {
        return Objects.equals(MATCH_NOT_CACHE, matchStatus);
    }

    public static boolean notMatchNotCache(MatchStatus matchStatus) {
        return Objects.equals(NOT_MATCH_NOT_CACHE, matchStatus);
    }

    public static MatchStatus ofBooleanNotCache(boolean match) {
        return match ? MATCH_NOT_CACHE : NOT_MATCH_NOT_CACHE;
    }
}
