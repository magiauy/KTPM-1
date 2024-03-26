/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;

/**
 *
 * @author NGOC
 */
public class LeaseAgreement {
    private String leaseAgreementID;
    private String apartmentID;
    private String tenantID;
    private String buildingManagerID;
    private LocalDate signingDate;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;
    private Double leaseTerm;
    private Double deposit;
    private Double monthlyRent;
    public LeaseAgreement(String leaseAgreementID, String apartmentID, String tenantID,
                          String buildingManagerID, LocalDate signingDate, LocalDate leaseStartDate,
                          LocalDate leaseEndDate, Double leaseTerm, Double deposit, Double monthlyRent) {
        this.leaseAgreementID = leaseAgreementID;
        this.apartmentID = apartmentID;
        this.tenantID = tenantID;
        this.buildingManagerID = buildingManagerID;
        this.signingDate = signingDate;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
        this.leaseTerm = leaseTerm;
        this.deposit = deposit;
        this.monthlyRent = monthlyRent;
    }
    public LeaseAgreement() {
    }

    public LeaseAgreement(LeaseAgreement leaseAgreement) {
        this.leaseAgreementID = leaseAgreement.leaseAgreementID;
        this.apartmentID = leaseAgreement.apartmentID;
        this.tenantID = leaseAgreement.tenantID;
        this.buildingManagerID = leaseAgreement.buildingManagerID;
        this.signingDate = leaseAgreement.signingDate;
        this.leaseStartDate = leaseAgreement.leaseStartDate;
        this.leaseEndDate = leaseAgreement.leaseEndDate;
        this.leaseTerm = leaseAgreement.leaseTerm;
        this.deposit = leaseAgreement.deposit;
        this.monthlyRent = leaseAgreement.monthlyRent;
    }
    public String getLeaseAgreementID() {
        return leaseAgreementID;
    }
    public void setLeaseAgreementID(String leaseAgreementID) {
        this.leaseAgreementID = leaseAgreementID;
    }
    public String getApartmentID() {
        return apartmentID;
    }
    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }
    public String getTenantID() {
        return tenantID;
    }
    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }
    public String getBuildingManagerID() {
        return buildingManagerID;
    }
    public void setBuildingManagerID(String buildingManagerID) {
        this.buildingManagerID = buildingManagerID;
    }
    public LocalDate getSigningDate() {
        return signingDate;
    }
    public void setSigningDate(LocalDate signingDate) {
        this.signingDate = signingDate;
    }
    public LocalDate getLeaseStartDate() {
        return leaseStartDate;
    }
    public void setLeaseStartDate(LocalDate leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }
    public LocalDate getLeaseEndDate() {
        return leaseEndDate;
    }
    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }
    public Double getLeaseTerm() {
        return leaseTerm;
    }
    public void setLeaseTerm(Double leaseTerm) {
        this.leaseTerm = leaseTerm;
    }
    public Double getDeposit() {
        return deposit;
    }
    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
    public Double getMonthlyRent() {
        return monthlyRent;
    }
    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
    @Override
    public String toString() {
        return "LeaseAgreement{" +
                "leaseAgreementID='" + leaseAgreementID + '\'' +
                ", apartmentID='" + apartmentID + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", buildingManagerID='" + buildingManagerID + '\'' +
                ", signingDate=" + signingDate +
                ", leaseStartDate=" + leaseStartDate +
                ", leaseEndDate=" + leaseEndDate +
                ", leaseTerm=" + leaseTerm +
                ", deposit=" + deposit +
                ", monthlyRent=" + monthlyRent +
                '}';
    }
}
