package com.ankan.insurance.policy_application.controller;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankan.insurance.policy_application.entity.LoginUser;
import com.ankan.insurance.policy_application.repo.AgentRepo;
import com.ankan.insurance.policy_application.service.AgentService;

@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentRepo agentRepo;

    @GetMapping("/")
    public Object getAgent(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) auth.getPrincipal();
            if (user.getRole().equals("ROLE_ADMIN")) {
                return agentRepo.findAll().stream().map(agentService::getAgent).toList();
            } else {
                return Arrays.asList(agentService.getAgent(auth));
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid Authentication");
        }
    }
}
