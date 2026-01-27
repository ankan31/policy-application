package com.ankan.insurance.policy_application.dto;

import java.util.Date;

import com.ankan.insurance.policy_application.entity.Policy;

import lombok.Data;

@Data
public class PolicyforAgent {
    private int policyNumber;
    private String policyHolder;
    private String nominee;
    private String product;
    private String email;
    private Date pensionDate;
    private Date maturityDate;
    private Date nextPremiumDate;
    private String policyType;
    private Double premiumAmount;

    public PolicyforAgent(Policy policy) {
        this.policyNumber = policy.getPolicyNumber();
        String salutation;
        if (policy.getPolicyHolder().getGender() == 'M') {
            salutation = "Mr. ";
        } else if (policy.getPolicyHolder().getGender() == 'F') {
            salutation = "Ms. ";
        } else {
            salutation = "";
        }
        this.policyHolder = salutation + policy.getPolicyHolder().getName();
        if (policy.getNominee().getGender() == 'M') {
            salutation = "Mr. ";
        } else if (policy.getNominee().getGender() == 'F') {
            salutation = "Ms. ";
        } else {
            salutation = "";
        }
        this.nominee = salutation + policy.getNominee().getName();
        this.product = policy.getProduct();
        this.email = policy.getEmail();
        this.pensionDate = policy.getPensionDate();
        this.maturityDate = policy.getMaturityDate();
        this.nextPremiumDate = policy.getNextPremiumDate();
        switch (policy.getPolicyType()) {
            case 'Y':
                this.policyType = "Yearly";
                break;
            case 'H':
                this.policyType = "Half-Yearly";
                break;
            case 'Q':
                this.policyType = "Quarterly";
                break;
            case 'M':
                this.policyType = "Monthly";
                break;
            default:
                this.policyType = "Unknown";
                break;
        }
        this.premiumAmount = policy.getPremiumAmount();
    }
}
