package com.ankan.insurance.policy_application.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class PolicyHolder {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    private char gender;
    private Date birthDate;
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 numeric characters")
    private Long phone;
    @OneToMany(mappedBy = "policyHolder")
    private List<Address> address;
}
