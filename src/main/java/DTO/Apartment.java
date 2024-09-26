/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author NGOC
 */
public class Apartment {
    private String apartmentID;
    private String buildingID;
    private Double area;
    private int bedrooms;
    private int bathrooms;
    private String furniture;
    private String status;

    public Apartment(String apartmentID, String buildingID,
            Double area, int bedrooms, int bathrooms, String furniture ,String status) {
        this.apartmentID = apartmentID;
        this.buildingID = buildingID;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.furniture = furniture;
        this.status=status;
    }

    public Apartment() {
    }

    public Apartment(Apartment apartment) {
        this.apartmentID = apartment.apartmentID;
        this.buildingID = apartment.buildingID;
        this.area = apartment.area;
        this.bedrooms = apartment.bedrooms;
        this.bathrooms = apartment.bathrooms;
        this.furniture = apartment.furniture;
        this.status=apartment.status;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }


    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getFurniture() {
        return furniture;
    }

    public String getStatus() {
        return status;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentID='" + apartmentID + '\'' +
                ", buildingID='" + buildingID + '\'' +
                ", area=" + area +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", furniture='" + furniture + '\'' +
                ", furniture='" + status + '\'' +
                '}';
    }
}
