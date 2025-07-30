package com.example.oop.ocp.insuranceclaim.asis.step1;

import org.junit.jupiter.api.Test;

class ClaimApprovalManagerTest {
    @Test
    void processHealthClaim() {
        ClaimApprovalManager manager = new ClaimApprovalManager();

        manager.processHealthClaim(new HealthInsuranceSurveyor());
    }
}