package DTO;

import java.time.LocalDate;
import java.util.Date;

public class ServiceTicket {
    private String serviceTicketID;
    private String monthlyRentBillID;
    private String serviceID;
    private Double quantity;
    private Double totalAmount;
    private LocalDate date;
    private String note;

    public ServiceTicket() {}
    public ServiceTicket(ServiceTicket serviceTicket){
        this.serviceTicketID = serviceTicket.serviceTicketID;
        this.serviceID = serviceTicket.getServiceID();
        this.monthlyRentBillID = serviceTicket.getMonthlyRentBillID();
        this.quantity = serviceTicket.getQuantity();
        this.totalAmount = serviceTicket.getTotalAmount();
        this.date = serviceTicket.getDate();
        this.note = serviceTicket.getNote();
    }

    public ServiceTicket(String serviceTicketID, String serviceID, String monthlyRentBillID, Double quantity, Double totalAmount, LocalDate date, String note) {
        this.serviceTicketID = serviceTicketID;
        this.serviceID = serviceID;
        this.monthlyRentBillID = monthlyRentBillID;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.date = date;
        this.note = note;
    }

    public String getServiceTicketID() {

        return serviceTicketID;
    }

    public void setServiceTicketID(String serviceTicketID) {
        this.serviceTicketID = serviceTicketID;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
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
        return "ServiceTicket{" +
                "serviceID='" + serviceID + '\'' +
                ", monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", date=" + date +
                ", note='" + note + '\'' +
                '}';
    }
}
