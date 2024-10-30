package com.homss.server.common.exception;

import com.homss.server.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.homss.server.common.exception.ExceptionCode.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_TEMPLATE = "Error: {}, Class : {}, Code : {}, Message : {}, Stack : {}";

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException exception) {
        log.error(LOG_TEMPLATE,
                "ApplicationException",
                exception.getClass().getSimpleName(),
                exception.getErrorCode(),
                exception.getMessage(),
                exception.getStackTrace()
        );
        return ResponseEntity.status(exception.getHttpStatus())
                .body(ErrorResponse.of(exception.getErrorCode(), exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        log.error(LOG_TEMPLATE,
                "RuntimeException",
                exception.getClass().getSimpleName(),
                INTERNAL_SERVER_ERROR.getCode(),
                exception.getMessage(),
                exception.getStackTrace()
        );
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(LOG_TEMPLATE,
                "Exception",
                exception.getClass().getSimpleName(),
                INTERNAL_SERVER_ERROR.getCode(),
                exception.getMessage(),
                exception.getStackTrace()
        );
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.of(INTERNAL_SERVER_ERROR.getCode(), INTERNAL_SERVER_ERROR.getMessage()));
    }
}
