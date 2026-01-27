package com.ankan.insurance.policy_application.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ankan.insurance.policy_application.entity.Agent;
import com.ankan.insurance.policy_application.entity.LoginUser;

@Service
public class AgentService {
    @Autowired
    private PolicyService policyService;

    public Map<String, Object> getAgent(Authentication auth) {
        // Implement logic to fetch agent details for the agent
        if (auth != null && auth.getPrincipal() instanceof LoginUser) {
            LoginUser user = (LoginUser) auth.getPrincipal();
            if (user.getAgent() != null) {
                Map<String, Object> agent = new HashMap<String, Object>() {};
                agent.put("agentNumber", user.getAgent().getAgentNumber());
                agent.put("name", user.getAgent().getName());
                agent.put("email", user.getAgent().getEmail());
                agent.put("phone", user.getAgent().getPhone());
                agent.put("licenseNumber",user.getAgent().getLicenseNumber());
                agent.put("policies", user.getAgent().getPolicies().stream().map(policyService::getPolicyforAgent).toList());
                return agent;
            } else if (user.getPolicy() != null && user.getPolicy().getAgent() != null) {
                Map<String, Object> agent = new HashMap<String, Object>() {};
                agent.put("agentNumber", user.getPolicy().getAgent().getAgentNumber());
                agent.put("name", user.getPolicy().getAgent().getName());
                agent.put("email", user.getPolicy().getAgent().getEmail());
                agent.put("phone", user.getPolicy().getAgent().getPhone());
                agent.put("licenseNumber",user.getPolicy().getAgent().getLicenseNumber());
                return agent;
            } else {
                throw new BadCredentialsException("");
            }
        } else {
            return null;
        }
    }
    
    public Map<String, Object> getAgent(Agent agent) {
        // Implement logic to fetch agent details for the agent
        if (agent != null) {
            Map<String, Object> agentDetails = new HashMap<String, Object>() {};
            agentDetails.put("agentNumber", agent.getAgentNumber());
            agentDetails.put("name", agent.getName());
            agentDetails.put("email", agent.getEmail());
            agentDetails.put("phone", agent.getPhone());
            agentDetails.put("licenseNumber",agent.getLicenseNumber());
            agentDetails.put("policies", agent.getPolicies().stream().map(policyService::getPolicyforAgent).toList());
            return agentDetails;
        } else {
            return null;
        }
    }
}
