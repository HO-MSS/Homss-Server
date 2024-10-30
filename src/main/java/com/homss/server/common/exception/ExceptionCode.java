package com.homss.server.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    INTERNAL_SERVER_ERROR("0001", HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버에러"),
    EXPIRED_TOKEN_ERROR("0002", HttpStatus.BAD_REQUEST, "만료된 토큰"),
    INVALIDATE_TOKEN_ERROR("0003", HttpStatus.BAD_REQUEST, "잘못된 토큰");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

}
