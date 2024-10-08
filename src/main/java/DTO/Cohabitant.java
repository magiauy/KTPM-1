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
public class Cohabitant {
    private String cohabitantID;
    private String tenantID;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private LocalDate dob;
    private String gender;
    private String citizenIdentityCard;

    public String getFullName(){
        return lastName +" "+ firstName;
    }

    public Cohabitant(String cohabitantID, String tenantID, String lastName,
                      String firstName, String phoneNumber, LocalDate dob,
                      String gender, String citizenIdentityCard) {
        this.cohabitantID = cohabitantID;
        this.tenantID = tenantID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.gender = gender;
        this.citizenIdentityCard = citizenIdentityCard;
    }

    public Cohabitant() {
    }

    public Cohabitant(Cohabitant cohabitant) {
        this.cohabitantID = cohabitant.cohabitantID;
        this.tenantID = cohabitant.tenantID;
        this.lastName = cohabitant.lastName;
        this.firstName = cohabitant.firstName;
        this.phoneNumber = cohabitant.phoneNumber;
        this.dob = cohabitant.dob;
        this.gender = cohabitant.gender;
        this.citizenIdentityCard = cohabitant.citizenIdentityCard;
    }

    public LocalDate getDateOfBirthDay() {
        return dob;
    }

    public void setDateOfBirthDay(LocalDate dob) {
        this.dob = dob;
    }

    public String getCohabitantID() {
        return cohabitantID;
    }

    public void setCohabitantID(String cohabitantID) {
        this.cohabitantID = cohabitantID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenIdentityCard() {
        return citizenIdentityCard;
    }

    public void setCitizenIdentityCard(String citizenIdentityCard) {
        this.citizenIdentityCard = citizenIdentityCard;
    }

    @Override
    public String toString() {
        return "Cohabitant{" +
                "cohabitantID='" + cohabitantID + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", citizenIdentityCard='" + citizenIdentityCard + '\'' +
                '}';
    }

}
