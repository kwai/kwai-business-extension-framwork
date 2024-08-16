package com.kuaishou.business.core.exception;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ExtPointInitException extends RuntimeException {
    private final Throwable throwable;
    private final String spi;
}
