/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author NGOC
 */
public class Furniture {
    private String furnitureID;
    private String apartmentID;
    private String nameFurniture;
    private String conditionFurniture;
    private Double price;
    public Furniture(String furniture, String apartmentID, String name, String condition, Double price) {
        this.furnitureID = furniture;
        this.apartmentID = apartmentID;
        this.nameFurniture = name;
        this.conditionFurniture = condition;
        this.price = price;
    }

    public String getFurnitureID() {
        return furnitureID;
    }

    public void setFurnitureID(String furnitureID) {
        this.furnitureID = furnitureID;
    }

    public String getNameFurniture() {
        return nameFurniture;
    }

    public void setNameFurniture(String nameFurniture) {
        this.nameFurniture = nameFurniture;
    }

    public String getConditionFurniture() {
        return conditionFurniture;
    }

    public void setConditionFurniture(String conditionFurniture) {
        this.conditionFurniture = conditionFurniture;
    }

    public Furniture() {
    }
    public Furniture(Furniture f){
        furnitureID = f.furnitureID;
        apartmentID = f.apartmentID;
        nameFurniture = f.nameFurniture;
        conditionFurniture = f.conditionFurniture;
        price = f.price;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getCondition() {
        return conditionFurniture;
    }
    public void setCondition(String condition) {
        this.conditionFurniture = condition;
    }
    public String getName() {
        return nameFurniture;
    }
    public void setName(String name) {
        this.nameFurniture = name;
    }
    public String getApartmentID() {
        return apartmentID;
    }
    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }
    public String getFurniture() {
        return furnitureID;
    }
    public void setFurniture(String furniture) {
        this.furnitureID = furniture;
    }
    @Override
    public String toString() {
        return "Furniture{" +
                "furniture='" + furnitureID + '\'' +
                ", apartmentID='" + apartmentID + '\'' +
                ", name='" + nameFurniture + '\'' +
                ", condition='" + conditionFurniture + '\'' +
                ", price=" + price +
                '}';
    }
}
