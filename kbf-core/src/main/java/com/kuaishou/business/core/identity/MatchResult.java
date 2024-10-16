package com.kuaishou.business.core.identity;

import java.util.Objects;

/**
 * @author liuzhuo
 * Created on 2023-04-04 下午6:07
 */
public enum MatchResult implements Match {

    /**
     * 未知状态,代表信息缺失,还可以再识别
     */
    UNKNOWN,
    /**
     * 匹配
     */
    MATCH,
    /**
     * 不匹配
     */
    NOT_MATCH
    ;

    public static MatchResult ofBoolean(boolean match) {
        return match ? MATCH : NOT_MATCH;
    }

    public static boolean match(MatchResult matchStatus) {
        return Objects.equals(MATCH, matchStatus);
    }

    public static boolean notMatch(MatchResult matchStatus) {
        return Objects.equals(NOT_MATCH, matchStatus);
    }

    public static boolean hasMatchResult(MatchResult matchStatus) {
        return match(matchStatus) || notMatch(matchStatus);
    }

	@Override
	public boolean hasMatch() {
		return match(this);
	}
}
