package DTO;

import java.util.Date;

public class ViolationTicket {
    private String violationID;
    private String monthlyRentBillID;
    private Double price;
    private Date date;
    private String note;

    public ViolationTicket(String violationID, String monthlyRentBillID, Double price, Date date, String note) {
        this.violationID = violationID;
        this.monthlyRentBillID = monthlyRentBillID;
        this.price = price;
        this.date = date;
        this.note = note;
    }

    public ViolationTicket() {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
                "violationID='" + violationID + '\'' +
                ", monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
