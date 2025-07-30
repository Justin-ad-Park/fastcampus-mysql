package com.example.oop.ocp.insuranceclaim.tobe;

public class VehicleInsuranceSurveyor implements InsuranceSurveyor{
    public boolean isValidClaim(){
        System.out.println("VehicleInsuranceSurveyor: Validating vehicle insurance claim...");
        /*Logic to validate vehicle insurance claims*/
        return true;
    }
}