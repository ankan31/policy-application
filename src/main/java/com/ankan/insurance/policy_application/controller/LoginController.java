package com.ankan.insurance.policy_application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ankan.insurance.policy_application.entity.LoginUser;
import com.ankan.insurance.policy_application.security.JwtService;

@RestController
public class LoginController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private JwtService  jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) {
        // Implement login logic here
        if (loginUser != null) {
            // Check if user exists in the database
            if (userDetailsManager.userExists(loginUser.getEmail())) {
                // Check if the password matches
                if (passwordEncoder.matches(loginUser.getPassword(), userDetailsManager.loadUserByUsername(loginUser.getEmail()).getPassword())) {
                    String token = jwtService.generateToken(loginUser.getEmail());
                    Map<String, Object> tokenMap = new HashMap<>();
                    tokenMap.put("token", token);
                    tokenMap.put("expiry", jwtService.getExpiration(token));
                    return ResponseEntity.ok(tokenMap);
                } else {
                    return ResponseEntity.status(401).body("Invalid password");
                }
            } else {
                return ResponseEntity.status(401).body("Invalid email");
            }
        } else {
            return ResponseEntity.badRequest().body("LoginUser cannot be null");
        }
    }   
}
