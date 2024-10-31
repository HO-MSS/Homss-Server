package com.homss.server.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{

    private final String errorCode;
    private final HttpStatus httpStatus;

    private ApplicationException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.errorCode = exceptionCode.getCode();
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public static ApplicationException create(ExceptionCode exceptionCode) {
        return new ApplicationException(exceptionCode);
    }

}
