package com.ankan.insurance.policy_application.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ankan.insurance.policy_application.entity.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    
}
