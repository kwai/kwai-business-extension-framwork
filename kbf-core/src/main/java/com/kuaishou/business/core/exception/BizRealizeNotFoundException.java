package com.kuaishou.business.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * spi实现未找到的异常
 */
@RequiredArgsConstructor
@Getter
@ToString
public class BizRealizeNotFoundException extends RuntimeException {
    private final String bizCode;
    private final String ext;
}
