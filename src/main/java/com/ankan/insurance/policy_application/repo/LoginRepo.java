package com.ankan.insurance.policy_application.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankan.insurance.policy_application.entity.LoginUser;

@Repository
public interface LoginRepo extends JpaRepository<LoginUser, Long> {
    Optional<LoginUser> findByEmail(String email);
}
