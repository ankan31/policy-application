package com.ankan.insurance.policy_application.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankan.insurance.policy_application.entity.Policy;

@Repository
public interface PolicyRepo extends JpaRepository<Policy, Integer>{
    Optional<Policy> findById(int policyNumber);
}
