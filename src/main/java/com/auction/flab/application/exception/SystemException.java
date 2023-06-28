package com.auction.flab.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

}
