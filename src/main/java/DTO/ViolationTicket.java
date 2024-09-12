package DTO;

import java.time.LocalDate;
import java.util.Date;

public class ViolationTicket {
    private String violationTicketID;
    private String violationID;
    private String monthlyRentBillID;
    private int quantity;
    private Double price;
    private LocalDate date;
    private String note;

    public ViolationTicket() {
    }

    public ViolationTicket(String violationTicketID, String violationID, String monthlyRentBillID, int quantity, Double price, LocalDate date, String note) {
        this.violationTicketID = violationTicketID;
        this.violationID = violationID;
        this.monthlyRentBillID = monthlyRentBillID;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
        this.note = note;
    }

    public String getViolationTicketID() {
        return violationTicketID;
    }

    public void setViolationTicketID(String violationTicketID) {
        this.violationTicketID = violationTicketID;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ViolationTicket{" +
                "violationTicketID='" + violationTicketID + '\'' +
                ", violationID='" + violationID + '\'' +
                ", monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
