package DTO;

import java.time.LocalDate;

public class ServiceUsuage {
    private String name;
    private String totalAmount;
    private Double quantity;
    private String date;
    private String note;

    public ServiceUsuage(String name, String totalAmout, String date, String note) {
        this.name = name;
        this.totalAmount = totalAmout;
        this.date = date;
        this.note = note;
    }

    public ServiceUsuage(String name, String totalAmount, Double quantity, String date) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.quantity = quantity;
        this.date = date;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
        return "ServiceUsuage{" +
                "name='" + name + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", quantity=" + quantity +
                ", date='" + date + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
