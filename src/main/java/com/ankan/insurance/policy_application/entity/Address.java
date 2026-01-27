package com.ankan.insurance.policy_application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agent_id")
    private Agent agent;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "policy_holder_id")
    private PolicyHolder policyHolder;
    private String street;
    private String city;
    private String state;
    @Pattern(regexp = "\\d{6}", message = "PIN code must be exactly 6 numeric characters")
    private int postcode;

}
