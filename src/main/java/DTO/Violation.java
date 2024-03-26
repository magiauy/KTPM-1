/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author NGOC
 */
public class Violation {
    private String violationID;
    private String monthlyRentBillID;
    private String name;
    private Double totalAmount;
    private String note;

    public Violation(String violationID, String monthlyRentBillID, String name,
                     Double totalAmount, String note) {
        this.violationID = violationID;
        this.monthlyRentBillID = monthlyRentBillID;
        this.name = name;
        this.totalAmount = totalAmount;
        this.note = note;
    }

    public Violation() {
    }

    public Violation(Violation violation) {
        this.violationID = violation.violationID;
        this.monthlyRentBillID = violation.monthlyRentBillID;
        this.name = violation.name;
        this.totalAmount = violation.totalAmount;
        this.note = violation.note;
    }

    public String getViolationID() {
        return violationID;
    }

    public void setViolationID(String violationID) {
        this.violationID = violationID;
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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Getter and Setter methods...

    @Override
    public String toString() {
        return "Violation{" +
                "violationID='" + violationID + '\'' +
                ", monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", name='" + name + '\'' +
                ", totalAmount=" + totalAmount +
                ", note='" + note + '\'' +
                '}';
    }
}
