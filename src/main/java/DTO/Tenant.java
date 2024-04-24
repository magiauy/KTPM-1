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
public class Tenant {
    private String tenantID;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private LocalDate dateOfBirthDay;
    private String gender;
    private String citizenIdentityCard;

    public Tenant(String tenantID, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirthDay, String gender, String citizenIdentityCard) {
        this.tenantID = tenantID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirthDay = dateOfBirthDay;
        this.gender = gender;
        this.citizenIdentityCard = citizenIdentityCard;
    }

    public Tenant() {
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

    public LocalDate getDateOfBirthDay() {
        return dateOfBirthDay;
    }

    public void setDateOfBirthDay(LocalDate dateOfBirthDay) {
        this.dateOfBirthDay = dateOfBirthDay;
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

}
