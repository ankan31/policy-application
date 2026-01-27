package com.ankan.insurance.policy_application.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(Exception ex, HttpServletResponse response, HttpServletRequest request) throws IOException { 
        response.setContentType("application/json"); 
        // Do NOT set response.setStatus(...) → Spring keeps its default 
        String json = String.format( 
            "{\"timestamp\":\"%s\",\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}", 
            LocalDateTime.now(), 
            ex.getClass().getSimpleName(), 
            ex.getMessage(), 
            request.getRequestURI() 
        ); 
        response.getWriter().write(json); 
    }
}

