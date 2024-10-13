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
    private String nameBuilding;
    private String city_Building;
//    private String district_Building;
    private String address_Building;
    private int numberOfApartment_Building;

    public Building(String buildingId, String nameBuilding, String city_Buiding,
                    String address_Building, int numberOfApartment_Building) {
        this.buildingId = buildingId;
        this.nameBuilding = nameBuilding;
        this.city_Building = city_Buiding;
//        this.district_Building = district_Building;
        this.address_Building = address_Building;
        this.numberOfApartment_Building = numberOfApartment_Building;
    }

    public Building() {
        this.buildingId = "";
        this.nameBuilding = "";
        this.city_Building = "";
//        this.district_Building = "";
        this.address_Building = "";
        this.numberOfApartment_Building = 0;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public String getCity_Building() {
        return city_Building;
    }

    public void setCity_Building(String city_Building) {
        this.city_Building = city_Building;
    }

    public String getAddress_Building() {
        return address_Building;
    }

    public void setAddress_Building(String address_Building) {
        this.address_Building = address_Building;
    }

    public int getNumberOfApartment_Building() {
        return numberOfApartment_Building;
    }

    public void setNumberOfApartment_Building(int numberOfApartment_Building) {
        this.numberOfApartment_Building = numberOfApartment_Building;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address_Building, buildingId, city_Building, nameBuilding,
                numberOfApartment_Building);
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
        return Objects.equals(address_Building, other.address_Building) && Objects.equals(buildingId, other.buildingId)
                && Objects.equals(city_Building, other.city_Building)
                && Objects.equals(nameBuilding, other.nameBuilding)
                && numberOfApartment_Building == other.numberOfApartment_Building;
    }

    @Override
    public String toString() {
        return "Building [buildingId=" + buildingId + ", nameBuilding=" + nameBuilding + ", city_Buiding="
                + city_Building +  ", address_Building=" + address_Building
                + ", numberOfApartment_Building=" + numberOfApartment_Building + "]";
    }
}
