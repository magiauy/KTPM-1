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
public class BuildingManager {
    private String buildingManagerId;
    private String buildingId;
    private String lastName;
    private String firstName;
    private String phoneNumber;
    private LocalDate dob;
    private String gender;
    private String position;
    private String citizenIdentityCard;
    private Float salary;


    public BuildingManager(String buildingManagerId, String buildingId, String lastName,
                    String firstName, String phoneNumber, LocalDate dobDate,
                    String gender, String citizenIdentityCard, Float salary, String position) {
            this.buildingManagerId = buildingManagerId;
            this.buildingId = buildingId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.phoneNumber = phoneNumber;
            this.dob = dobDate;
            this.gender = gender;
            this.citizenIdentityCard = citizenIdentityCard;
            this.salary = salary;
            this.position = position;
    }

    public BuildingManager() {
            this.buildingManagerId = "";
            this.buildingId = "";
            this.lastName = "";
            this.firstName = "";
            this.phoneNumber = "";
            this.dob = LocalDate.now();
            this.gender = "";
            this.citizenIdentityCard = "";
            this.salary = 0.0f;
            this.position="";
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getBuildingManagerId() {
            return buildingManagerId;
    }
    public void setBuildingManagerId(String buildingManagerId) {
            this.buildingManagerId = buildingManagerId;
    }
    public String getBuildingId() {
            return buildingId;
    }
    public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
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
    public LocalDate getDobDate() {
            return dob;
    }

    public void setDobDate(LocalDate dobDate) {
            this.dob = dobDate;
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
    public Float getSalary() {
            return salary;
    }
    public void setSalary(Float salary) {
            this.salary = salary;
    }
    
    public String getPosition() {
            return position;
    }

    public void setPosition(String position) {
            this.position = position;
    }
    @Override
    public int hashCode() {
            return Objects.hash(buildingId, buildingManagerId, citizenIdentityCard, dob,
                            firstName, gender, lastName,
                            phoneNumber, salary,position);
    }
    @Override
    public boolean equals(Object obj) {
            if (this == obj)
                    return true;
            if (obj == null)
                    return false;
            if (getClass() != obj.getClass())
                    return false;
            BuildingManager other = (BuildingManager) obj;
            return Objects.equals(buildingId, other.buildingId)
                            && Objects.equals(buildingManagerId, other.buildingManagerId)
                            && Objects.equals(citizenIdentityCard, other.citizenIdentityCard)
                            && Objects.equals(dob, other.dob)
                            && Objects.equals(position,other.position)
                            && Objects.equals(firstName, other.firstName)
                            && Objects.equals(gender, other.gender)
                            && Objects.equals(lastName, other.lastName)
                            && Objects.equals(phoneNumber, other.phoneNumber)
                            && Double.doubleToLongBits(salary) == Double
                                            .doubleToLongBits(other.salary);
    }

    @Override
    public String toString() {
        return "BuildingManager{" +
                "buildingManagerId='" + buildingManagerId + '\'' +
                ", buildingId='" + buildingId + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", citizenIdentityCard='" + citizenIdentityCard + '\'' +
                ", salary=" + salary +
                '}';
    }
}
