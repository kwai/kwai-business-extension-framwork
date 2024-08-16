package com.kuaishou.business.core.exception;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class BizIdentityException extends RuntimeException {

    private final String message;
}
