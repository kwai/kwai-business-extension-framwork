package com.kuaishou.business.core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IllegalExtPointException extends RuntimeException {
    private final String extPoint;
}
