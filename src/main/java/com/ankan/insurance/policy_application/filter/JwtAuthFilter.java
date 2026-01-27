package com.ankan.insurance.policy_application.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ankan.insurance.policy_application.repo.LoginRepo;
import com.ankan.insurance.policy_application.security.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private LoginRepo loginRepo;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extract JWT from Authorization header
            String header = request.getHeader("Authorization");
            if (header != null && header.startsWith("Bearer ")) {
                String jwt = header.substring(7); // Extract token after "Bearer "
                
                if ((!jwt.isEmpty()) && jwtService.isTokenValid(jwt)) {
                    try {
                        String username = jwtService.getUsernameFromToken(jwt);
                        UserDetails userDetails = loginRepo.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } catch (Exception e) {
                        throw e;
                    }
                } else {
                    throw new BadCredentialsException("Invalid JWT token");
                }
            }
        } catch (Exception e) {
            throw new ServletException("Failed to authenticate user", e);
        }
        filterChain.doFilter(request, response);
    }
}

