package com.homss.server.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 시스템 에러 (0000~)*/
    INTERNAL_SERVER_ERROR("0000", HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버에러"),
    REQUEST_BODY_NOT_FOUND_ERROR("0001", HttpStatus.BAD_REQUEST, "요청의 Body가 없음"),
    REQUEST_ARGUMENT_NOT_VALID_ERROR("0002", HttpStatus.BAD_REQUEST, "요청에 필요한 필드가 없음 -> "),
    DATABASE_ERROR("0003", HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러"),

    /* 인증&인가 에러 (1000~) */
    EXPIRED_TOKEN_ERROR("1000", HttpStatus.BAD_REQUEST, "만료된 토큰"),
    INVALIDATE_TOKEN_ERROR("1001", HttpStatus.BAD_REQUEST, "잘못된 토큰"),
    ID_FILED_NOT_FOUND_ERROR("1002", HttpStatus.INTERNAL_SERVER_ERROR, "ID 필드를 찾지 못함"),
    NO_AUTHORIZATION_TOKEN_ERROR("1003", HttpStatus.BAD_REQUEST, "인증 토큰이 존재하지 않음");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

}
