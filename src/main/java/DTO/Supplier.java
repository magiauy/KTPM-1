/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.Objects;

/**
 *
 * @author NGOC
 */
public class Supplier {
    private String supplierId;
    private String buildingId_Supplier;
    private String firstName_Supplier;
    private String lastName_Supplier;
    private String phoneNumber_Supplier;
    private String citizenIdentityCart_Supplier;

    public Supplier(String supplierId, String buildingId_Supplier, String firstName_Supplier, String lastName_Supplier,
                    String phoneNumber_Supplier, String citizenIdentityCart_Supplier) {
        this.supplierId = supplierId;
        this.buildingId_Supplier = buildingId_Supplier;
        this.firstName_Supplier = firstName_Supplier;
        this.lastName_Supplier = lastName_Supplier;
        this.phoneNumber_Supplier = phoneNumber_Supplier;
        this.citizenIdentityCart_Supplier = citizenIdentityCart_Supplier;
    }

    public Supplier() {
        this.supplierId = "";
        this.buildingId_Supplier = "";
        this.firstName_Supplier = "";
        this.lastName_Supplier = "";
        this.phoneNumber_Supplier = "";
        this.citizenIdentityCart_Supplier = "";
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBuildingId_Supplier() {
        return buildingId_Supplier;
    }

    public void setBuildingId_Supplier(String buildingId_Supplier) {
        this.buildingId_Supplier = buildingId_Supplier;
    }

    public String getFirstName_Supplier() {
        return firstName_Supplier;
    }

    public void setFirstName_Supplier(String firstName_Supplier) {
        this.firstName_Supplier = firstName_Supplier;
    }

    public String getLastName_Supplier() {
        return lastName_Supplier;
    }

    public void setLastName_Supplier(String lastName_Supplier) {
        this.lastName_Supplier = lastName_Supplier;
    }

    public String getPhoneNumber_Supplier() {
        return phoneNumber_Supplier;
    }

    public void setPhoneNumber_Supplier(String phoneNumber_Supplier) {
        this.phoneNumber_Supplier = phoneNumber_Supplier;
    }

    public String getCitizenIdentityCart_Supplier() {
        return citizenIdentityCart_Supplier;
    }

    public void setCitizenIdentityCart_Supplier(String citizenIdentityCart_Supplier) {
        this.citizenIdentityCart_Supplier = citizenIdentityCart_Supplier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingId_Supplier, citizenIdentityCart_Supplier, firstName_Supplier, lastName_Supplier,
                phoneNumber_Supplier, supplierId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Supplier other = (Supplier) obj;
        return Objects.equals(buildingId_Supplier, other.buildingId_Supplier)
                && Objects.equals(citizenIdentityCart_Supplier, other.citizenIdentityCart_Supplier)
                && Objects.equals(firstName_Supplier, other.firstName_Supplier)
                && Objects.equals(lastName_Supplier, other.lastName_Supplier)
                && Objects.equals(phoneNumber_Supplier, other.phoneNumber_Supplier)
                && Objects.equals(supplierId, other.supplierId);
    }

    @Override
    public String toString() {
        return "Supplier [supplierId=" + supplierId + ", buildingId_Supplier=" + buildingId_Supplier
                + ", firstName_Supplier=" + firstName_Supplier + ", lastName_Supplier=" + lastName_Supplier
                + ", phoneNumber_Supplier=" + phoneNumber_Supplier + ", citizenIdentityCart_Supplier="
                + citizenIdentityCart_Supplier + "]";
    }
}
