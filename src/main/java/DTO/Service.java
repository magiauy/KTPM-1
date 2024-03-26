/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author NGOC
 */
public class Service {
    private String serviceID;
    private String monthlyRentBillID;
    private String name;
    private Double quantity;
    private Double pricePerUnit;
    private String unit;
    private Double totalAmount;
    public Service(String serviceID, String monthlyRentBillID, String name,
                   Double quantity, Double pricePerUnit, String unit, Double totalAmount) {
        this.serviceID = serviceID;
        this.monthlyRentBillID = monthlyRentBillID;
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
        this.totalAmount = totalAmount;
    }
    public Service() {
    }
    public Service(Service service) {
        this.serviceID = service.serviceID;
        this.monthlyRentBillID = service.monthlyRentBillID;
        this.name = service.name;
        this.quantity = service.quantity;
        this.pricePerUnit = service.pricePerUnit;
        this.unit = service.unit;
        this.totalAmount = service.totalAmount;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getMonthlyRentBillID() {
        return monthlyRentBillID;
    }

    public void setMonthlyRentBillID(String monthlyRentBillID) {
        this.monthlyRentBillID = monthlyRentBillID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", unit='" + unit + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
