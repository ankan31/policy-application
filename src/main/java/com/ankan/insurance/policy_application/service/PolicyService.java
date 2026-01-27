package com.ankan.insurance.policy_application.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ankan.insurance.policy_application.dto.PolicyforAgent;
import com.ankan.insurance.policy_application.entity.LoginUser;
import com.ankan.insurance.policy_application.entity.Policy;

@Service
public class PolicyService {
    public Map<String, Object> getPolicyforAgent(Policy policy) {
        // Implement logic to fetch policy details for the agent
        Map<String, Object> policyData = new HashMap<>();
        policyData.put("policyNumber", policy.getPolicyNumber());
        PolicyforAgent agentView = new PolicyforAgent(policy);
        policyData.put("policyHolder", agentView.getPolicyHolder());
        return policyData;
    }

    public List<?> getPolicyList(Authentication authentication) throws AuthenticationException {
        // Implement logic to fetch list of policies
        if (authentication!= null && authentication.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) authentication.getPrincipal();
            if (user.getAgent().getPolicies() != null) {
                return user.getAgent().getPolicies().stream().map(this::getPolicyforAgent).toList();
            } else if (user.getPolicy() != null) {
                return List.of(user.getPolicy());
            } else {
                throw new BadCredentialsException("Unauthorized access");
            }
        } else {
            throw new AuthenticationException("Invalid authentication");
        }
    }

}
