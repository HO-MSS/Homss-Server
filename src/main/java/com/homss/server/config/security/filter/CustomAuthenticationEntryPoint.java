package com.homss.server.config.security.filter;

import com.homss.server.common.exception.ApplicationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.homss.server.common.exception.ExceptionCode.NO_AUTHORIZATION_TOKEN_ERROR;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        if (request.getHeader("Authorization") == null) {
            throw ApplicationException.create(NO_AUTHORIZATION_TOKEN_ERROR);
        }
    }
}
