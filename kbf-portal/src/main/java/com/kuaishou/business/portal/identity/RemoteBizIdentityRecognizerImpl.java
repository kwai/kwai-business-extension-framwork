package com.kuaishou.business.portal.identity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kuaishou.business.core.KBusinessRequest;
import com.kuaishou.business.portal.common.ConfigConstant;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangtianwen
 * @date 2022-04-01
 */
@Slf4j
public class RemoteBizIdentityRecognizerImpl<T extends KBusinessRequest> {
//        implements RemoteBizIdentityRecognizer


    @Setter
    private RemoteBizIdentityConfigLoader remoteBizIdentityConfigLoader;

    private final List<RemoteBizIdentityMatcher> matcherList = new ArrayList<>();

    public void init() {
        // todo: config
        String domainCode = ConfigConstant.DOMAIN_CODE_TRADE;
        List<RemoteBizIdentityMatcher> matchers = remoteBizIdentityConfigLoader.load(domainCode);
        log.warn("remoteBizIdentityConfigLoader.load, param:{}, result:{}", domainCode, matchers);

        if (matchers != null && !matchers.isEmpty()) {
            matcherList.addAll(matchers);
        }
    }

    public String recognize(KBusinessRequest request) {

        for (RemoteBizIdentityMatcher identity : matcherList) {
            String bizCode = identity.match(request);
            if (StringUtils.isNotBlank(bizCode)) {
                /*DefaultBizIdentity defaultBizIdentity = new DefaultBizIdentity();
                defaultBizIdentity.setBizCode(bizCode);
                return defaultBizIdentity;*/
                return bizCode;
            }
        }

        return null;
    }
}
