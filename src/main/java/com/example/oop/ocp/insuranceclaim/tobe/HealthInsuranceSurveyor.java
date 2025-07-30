package com.example.oop.ocp.insuranceclaim.tobe;


public class HealthInsuranceSurveyor implements InsuranceSurveyor{
    public boolean isValidClaim(){
        System.out.println("HealthInsuranceSurveyor: Validating health insurance claim...");
        /*Logic to validate health insurance claims*/
        return true;
    }
}