package com.ankan.insurance.policy_application.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.getWriter().write(
            String.format("{\"timestamp\":\"%s\",\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
                          LocalDateTime.now(), exception.getClass().getSimpleName(), exception.getMessage(), request.getRequestURI())
        );
    }
}
