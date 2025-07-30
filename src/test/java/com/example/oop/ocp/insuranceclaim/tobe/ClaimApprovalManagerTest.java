package com.example.oop.ocp.insuranceclaim.tobe;

import org.junit.jupiter.api.Test;

public class ClaimApprovalManagerTest {
    @Test
    public void testProcessClaim() {

        ClaimApprovalManager healthClaimApprovalManager =new ClaimApprovalManager();
        healthClaimApprovalManager.processClaim(new HealthInsuranceSurveyor());

        ClaimApprovalManager vehicleClaimApprovalManager = new ClaimApprovalManager();
        vehicleClaimApprovalManager.processClaim(new VehicleInsuranceSurveyor());
    }
}