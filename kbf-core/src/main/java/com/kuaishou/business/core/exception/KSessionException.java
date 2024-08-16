package com.kuaishou.business.core.exception;

import lombok.RequiredArgsConstructor;

/**
 * @author liuzhuo
 * Created on 2023-03-23 下午11:34
 */
@RequiredArgsConstructor
public class KSessionException extends RuntimeException{

    private final String errorMessage;
}
