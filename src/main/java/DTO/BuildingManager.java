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
    private String lastName_BuildingManager;
    private String firstName_BuildingManager;
    private String phoneNumber_BuildingManager;
    private LocalDate dateOfBirthDay;
    private String gender_BuildingManager;
    private String citizenIdentityCard_BuildingManager;
    private Float salary_BuildingManager;

    public BuildingManager(String buildingManagerId, String buildingId, String lastName_BuildingManager,
                    String firstName_BuildingManager, String phoneNumber_BuildingManager, LocalDate dobDate,
                    String gender_BuildingManager, String citizenIdentityCard_BuildingManager, Float salary_BuildingManager) {
            this.buildingManagerId = buildingManagerId;
            this.buildingId = buildingId;
            this.lastName_BuildingManager = lastName_BuildingManager;
            this.firstName_BuildingManager = firstName_BuildingManager;
            this.phoneNumber_BuildingManager = phoneNumber_BuildingManager;
            this.dateOfBirthDay = dobDate;
            this.gender_BuildingManager = gender_BuildingManager;
            this.citizenIdentityCard_BuildingManager = citizenIdentityCard_BuildingManager;
            this.salary_BuildingManager = salary_BuildingManager;
    }

    public BuildingManager() {
            this.buildingManagerId = "";
            this.buildingId = "";
            this.lastName_BuildingManager = "";
            this.firstName_BuildingManager = "";
            this.phoneNumber_BuildingManager = "";
            this.dateOfBirthDay = LocalDate.now();
            this.gender_BuildingManager = "";
            this.citizenIdentityCard_BuildingManager = "";
            this.salary_BuildingManager = (float) 0;
    }
    public LocalDate getDateOfBirthDay() {
        return dateOfBirthDay;
    }
    public void setDateOfBirthDay(LocalDate dateOfBirthDay) {
        this.dateOfBirthDay = dateOfBirthDay;
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
    public String getLastName_BuildingManager() {
            return lastName_BuildingManager;
    }
    public void setLastName_BuildingManager(String lastName_BuildingManager) {
            this.lastName_BuildingManager = lastName_BuildingManager;
    }
    public String getFirstName_BuildingManager() {
            return firstName_BuildingManager;
    }
    public void setFirstName_BuildingManager(String firstName_BuildingManager) {
            this.firstName_BuildingManager = firstName_BuildingManager;
    }
    public String getPhoneNumber_BuildingManager() {
            return phoneNumber_BuildingManager;
    }
    public void setPhoneNumber_BuildingManager(String phoneNumber_BuildingManager) {
            this.phoneNumber_BuildingManager = phoneNumber_BuildingManager;
    }
    public LocalDate getDobDate() {
            return dateOfBirthDay;
    }

    public void setDobDate(LocalDate dobDate) {
            this.dateOfBirthDay = dobDate;
    }

    public String getGender_BuildingManager() {
            return gender_BuildingManager;
    }
    public void setGender_BuildingManager(String gender_BuildingManager) {
            this.gender_BuildingManager = gender_BuildingManager;
    }
    public String getCitizenIdentityCard_BuildingManager() {
            return citizenIdentityCard_BuildingManager;
    }
    public void setCitizenIdentityCard_BuildingManager(String citizenIdentityCard_BuildingManager) {
            this.citizenIdentityCard_BuildingManager = citizenIdentityCard_BuildingManager;
    }
    public Float getSalary_BuildingManager() {
            return salary_BuildingManager;
    }
    public void setSalary_BuildingManager(Float salary_BuildingManager) {
            this.salary_BuildingManager = salary_BuildingManager;
    }
    @Override
    public int hashCode() {
            return Objects.hash(buildingId, buildingManagerId, citizenIdentityCard_BuildingManager, dateOfBirthDay,
                            firstName_BuildingManager, gender_BuildingManager, lastName_BuildingManager,
                            phoneNumber_BuildingManager, salary_BuildingManager);
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
                            && Objects.equals(citizenIdentityCard_BuildingManager, other.citizenIdentityCard_BuildingManager)
                            && Objects.equals(dateOfBirthDay, other.dateOfBirthDay)
                            && Objects.equals(firstName_BuildingManager, other.firstName_BuildingManager)
                            && Objects.equals(gender_BuildingManager, other.gender_BuildingManager)
                            && Objects.equals(lastName_BuildingManager, other.lastName_BuildingManager)
                            && Objects.equals(phoneNumber_BuildingManager, other.phoneNumber_BuildingManager)
                            && Double.doubleToLongBits(salary_BuildingManager) == Double
                                            .doubleToLongBits(other.salary_BuildingManager);
    }
    @Override
    public String toString() {
            return "BuildingManager [buildingManagerId=" + buildingManagerId + ", buildingId=" + buildingId
                            + ", lastName_BuildingManager=" + lastName_BuildingManager + ", firstName_BuildingManager="
                            + firstName_BuildingManager + ", phoneNumber_BuildingManager=" + phoneNumber_BuildingManager
                            + ", dobDate=" + dateOfBirthDay + ", gender_BuildingManager=" + gender_BuildingManager
                            + ", citizenIdentityCard_BuildingManager=" + citizenIdentityCard_BuildingManager
                            + ", salary_BuildingManager=" + salary_BuildingManager + "]";
    }
}
