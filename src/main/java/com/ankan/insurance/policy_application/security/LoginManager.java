package com.ankan.insurance.policy_application.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.ankan.insurance.policy_application.entity.LoginUser;
import com.ankan.insurance.policy_application.repo.LoginRepo;

@Service
public class LoginManager implements UserDetailsManager {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(user.getUsername());
        loginUser.setPassword(passwordEncoder.encode(user.getPassword()));
        loginRepo.save(loginUser);
    }

    @Override
    public void updateUser(UserDetails user) {
        LoginUser loginUser = loginRepo.findByEmail(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        loginUser.setRole(user.getAuthorities().toString());
        loginRepo.save(loginUser);
    }

    @Override
    public void deleteUser(String username) {
        LoginUser loginUser = loginRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (loginUser != null) {
            loginRepo.delete(loginUser);
        }
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // This method would typically require the current authenticated user context
        String currentUsername = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        LoginUser loginUser = loginRepo.findByEmail(currentUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(oldPassword, loginUser.getPassword())) {
            loginUser.setPassword(passwordEncoder.encode(newPassword));
            loginRepo.save(loginUser);
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        
    }

    @Override
    public boolean userExists(String username) {
        Optional<LoginUser> loginUser = loginRepo.findByEmail(username);
        return loginUser.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
}
