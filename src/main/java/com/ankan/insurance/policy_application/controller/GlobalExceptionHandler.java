package com.ankan.insurance.policy_application.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object handleAllExceptions(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Map<String, Object> errorDetails = Map.of(
            "timestamp", LocalDateTime.now().toString(),
            "error", ex.getClass().getSimpleName(),
            "message", ex.getMessage(),
            "path", request.getRequestURI()
        );
        return errorDetails;
    }
}

