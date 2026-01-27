package com.ankan.insurance.policy_application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ankan.insurance.policy_application.dto.PolicyforAgent;
import com.ankan.insurance.policy_application.entity.LoginUser;
import com.ankan.insurance.policy_application.entity.Policy;
import com.ankan.insurance.policy_application.repo.PolicyRepo;
import com.ankan.insurance.policy_application.service.PolicyService;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    public PolicyRepo policyRepo;
    @Autowired
    public PolicyService policyService;

    @GetMapping("/")
    public Object getPolicyList() throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return policyService.getPolicyList(authentication);
        } else {
            throw new AuthenticationException("Invalid authentication");
        }
    }

    @GetMapping({"/{policyNumber}", "/{policyNumber}/{param}"})
    public Object getPolicyDetails(@PathVariable int policyNumber, @PathVariable Optional<String> param) throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!= null && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) authentication.getPrincipal();
            Policy policy = policyRepo.findById(policyNumber).orElse(null);
            if (user.getRole().equals("ROLE_ADMIN")) {
                return policy;
            } else if (user.getAgent() != null && user.getAgent().getAgentNumber() == policy.getAgent().getAgentNumber()) {
                return new PolicyforAgent(policy);
            } else if (user.getPolicy() != null && user.getPolicy().getPolicyNumber() == policy.getPolicyNumber()) {
                if (param.isEmpty() || !param.get().equals("data")) {
                    return new PolicyforAgent(policy);
                } else {
                    return policy;
                }
            } else {
                throw new BadCredentialsException("Unauthorized access");
                
            }
        } else {
            throw new AuthenticationException("Invalid authentication");
        }
    }

}
