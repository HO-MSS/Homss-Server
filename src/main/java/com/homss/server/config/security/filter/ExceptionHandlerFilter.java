package com.homss.server.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homss.server.common.exception.ApplicationException;
import com.homss.server.common.response.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ApplicationException e) {
            int code = e.getHttpStatus().value();
            setErrorResponse(response, e.getMessage(), e.getErrorCode(), HttpStatus.valueOf(code));
        }

    }

    private void setErrorResponse(HttpServletResponse response, String message, String errorCode, HttpStatus status)
            throws IOException {
        log.error("Code : {}, Message : {}", errorCode, message);
        String errorResponse = objectMapper.writeValueAsString(ErrorResponse.of(errorCode, message));
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(errorResponse);
    }
}
