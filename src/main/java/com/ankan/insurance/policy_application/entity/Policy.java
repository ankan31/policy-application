package com.ankan.insurance.policy_application.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Policy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int policyNumber;
    @OneToOne
    @JoinColumn(name = "holder_id")
    private PolicyHolder policyHolder;
    @OneToOne
    @JoinColumn(name = "nominee_id")
    private PolicyHolder nominee;
    @ManyToOne
    @JoinColumn(name = "agent_id")
    @JsonIgnore
    private Agent agent;
    private String product;
    private String email;
    private Date startDate;
    private Date pensionDate;
    private Date maturityDate;
    private Date lastPremiumDate;
    private Date nextPremiumDate;
    @Pattern(regexp = "[MQYS]", message = "Policy type must be 'M', 'Q', 'Y' or 'S'")
    private char policyType;
    private Double premiumAmount;
}
