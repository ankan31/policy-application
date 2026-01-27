package com.ankan.insurance.policy_application.entity;

import jakarta.validation.constraints.Pattern;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Agent {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int agentNumber;
    private String name;
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 numeric characters")
    private Long phone;
    private String licenseNumber;
    @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER)
    private List<Policy> policies;
    @OneToMany(mappedBy = "agent", fetch = FetchType.EAGER)
    private List<Address> address;
}
