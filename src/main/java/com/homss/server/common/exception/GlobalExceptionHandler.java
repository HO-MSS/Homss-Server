package com.homss.server.common.exception;

import com.homss.server.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.homss.server.common.exception.ExceptionCode.*;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNoValidArgumentException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        List<String> errorMessages = bindingResult.getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + ", Error: " + error.getDefaultMessage()).toList();

        String arguments = String.join(", ", errorMessages);
        log.error(LOG_TEMPLATE,
                "MethodArgumentNotValidException",
                exception.getClass().getSimpleName(),
                REQUEST_ARGUMENT_NOT_VALID_ERROR.getCode() + arguments,
                exception.getMessage(),
                exception.getStackTrace()
        );
        return ResponseEntity.status(REQUEST_ARGUMENT_NOT_VALID_ERROR.getHttpStatus())
                .body(ErrorResponse.of(REQUEST_ARGUMENT_NOT_VALID_ERROR.getCode(),
                        REQUEST_ARGUMENT_NOT_VALID_ERROR.getMessage() + arguments));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNoBodyException(HttpMessageNotReadableException exception) {
        log.error(LOG_TEMPLATE,
                "HttpMessageNotReadableException",
                exception.getClass().getSimpleName(),
                REQUEST_BODY_NOT_FOUND_ERROR.getCode(),
                exception.getMessage(),
                exception.getStackTrace()
        );
        return ResponseEntity.status(REQUEST_BODY_NOT_FOUND_ERROR.getHttpStatus())
                .body(ErrorResponse.of(REQUEST_BODY_NOT_FOUND_ERROR.getCode(), REQUEST_BODY_NOT_FOUND_ERROR.getMessage()));
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
