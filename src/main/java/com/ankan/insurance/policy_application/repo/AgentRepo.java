package com.ankan.insurance.policy_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankan.insurance.policy_application.entity.Agent;

@Repository
public interface AgentRepo extends JpaRepository<Agent, Integer> {
    Agent findByEmail(String email);
}