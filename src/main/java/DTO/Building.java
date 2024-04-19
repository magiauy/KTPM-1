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
public class Building {
    private String buildingId;
    private String name;
    private String city;
    private String district;
    private String address;
    private int numberOfApartment;

    public Building(String buildingId, String name, String city, String district,
                    String address, int numberOfApartment) {
        this.buildingId = buildingId;
        this.name = name;
        this.city = city;
        this.district = district;
        this.address = address;
        this.numberOfApartment = numberOfApartment;
    }

    public Building() {
        this.buildingId = "";
        this.name = "";
        this.city = "";
        this.district = "";
        this.address = "";
        this.numberOfApartment = 0;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumberOfApartment() {
        return numberOfApartment;
    }

    public void setNumberOfApartment(int numberOfApartment) {
        this.numberOfApartment = numberOfApartment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, buildingId, city, district, name,
                numberOfApartment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Building other = (Building) obj;
        return Objects.equals(address, other.address) && Objects.equals(buildingId, other.buildingId)
                && Objects.equals(city, other.city)
                && Objects.equals(district, other.district)
                && Objects.equals(name, other.name)
                && numberOfApartment == other.numberOfApartment;
    }

    @Override
    public String toString() {
        return STR."Building [buildingId=\{buildingId}, name=\{name}, city=\{city}, district=\{district}, address=\{address}, numberOfApartment=\{numberOfApartment}]";
    }
}
