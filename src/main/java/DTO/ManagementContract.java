/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author NGOC
 */
public class ManagementContract {
    private String managementContract;
    private String buildingManagerId_ManagementContract;
    private LocalDate signingDate;
    private LocalDate leaseStartDate;
    private LocalDate leaseEndDate;

    public ManagementContract(String managementContract, String buildingManagerId_ManagementContract, LocalDate signingDate,
                              LocalDate leaseStartDate, LocalDate leaseEndDate) {
        this.managementContract = managementContract;
        this.buildingManagerId_ManagementContract = buildingManagerId_ManagementContract;
        this.signingDate = signingDate;
        this.leaseStartDate = leaseStartDate;
        this.leaseEndDate = leaseEndDate;
    }

    public ManagementContract() {
        this.managementContract = "";
        this.buildingManagerId_ManagementContract = "";
        this.signingDate = LocalDate.now();
        this.leaseStartDate = LocalDate.now();
        this.leaseEndDate = LocalDate.now();
    }


    public String getManagementContract() {
        return managementContract;
    }

    public void setManagementContract(String managementContract) {
        this.managementContract = managementContract;
    }

    public String getBuildingManagerId() {
        return buildingManagerId_ManagementContract;
    }

    public void setBuildingManagerId(String buildingManagerId) {
        this.buildingManagerId_ManagementContract = buildingManagerId;
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

    public String getBuildingManagerId_ManagementContract() {
        return buildingManagerId_ManagementContract;
    }

    public void setBuildingManagerId_ManagementContract(String buildingManagerId_ManagementContract) {
        this.buildingManagerId_ManagementContract = buildingManagerId_ManagementContract;
    }

    public void setLeaseEndDate(LocalDate leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingManagerId_ManagementContract, leaseEndDate, leaseStartDate, managementContract, signingDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ManagementContract other = (ManagementContract) obj;
        return Objects.equals(buildingManagerId_ManagementContract, other.buildingManagerId_ManagementContract)
                && Objects.equals(leaseEndDate, other.leaseEndDate)
                && Objects.equals(leaseStartDate, other.leaseStartDate)
                && Objects.equals(managementContract, other.managementContract)
                && Objects.equals(signingDate, other.signingDate);
    }

    @Override
    public String toString() {
        return "ManagementContract [managementContract=" + managementContract + ", buildingManagerId="
                + buildingManagerId_ManagementContract + ", signingDate=" + signingDate + ", leaseStartDate=" + leaseStartDate
                + ", leaseEndDate=" + leaseEndDate + "]";
    }

}
