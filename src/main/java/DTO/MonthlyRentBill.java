/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class MonthlyRentBill {
    private String monthlyRentBillID;;
    private String apartmentID;
    private String tenantID;
    private String leaseAgreementID;
    private LocalDate date;
    private int repaymentPeriod;
    private Double totalPayment;
    private String status;

    public MonthlyRentBill(String monthlyRentBillID, String apartmentID, String tenantID, String leaseAgreementID, LocalDate date, int repaymentPeriod, Double totalPayment, String status) {
        this.monthlyRentBillID = monthlyRentBillID;
        this.apartmentID = apartmentID;
        this.tenantID = tenantID;
        this.leaseAgreementID = leaseAgreementID;
        this.date = date;
        this.repaymentPeriod = repaymentPeriod;
        this.totalPayment = totalPayment;
        this.status = status;
    }

    public MonthlyRentBill() {
    }

    public MonthlyRentBill(MonthlyRentBill monthlyRentBill){
        this.monthlyRentBillID = monthlyRentBill.monthlyRentBillID;
        this.apartmentID = monthlyRentBill.apartmentID;
        this.tenantID = monthlyRentBill.tenantID;
        this.leaseAgreementID = monthlyRentBill.leaseAgreementID;
        this.date = monthlyRentBill.date;
        this.repaymentPeriod = monthlyRentBill.repaymentPeriod;
        this.totalPayment = monthlyRentBill.totalPayment;
        this.status = monthlyRentBill.status;
    }

    public String getMonthlyRentBillID() {
        return monthlyRentBillID;
    }

    public void setMonthlyRentBillID(String monthlyRentBillID) {
        this.monthlyRentBillID = monthlyRentBillID;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public String getTenantID() {
        return tenantID;
    }

    public void setTenantID(String tenantID) {
        this.tenantID = tenantID;
    }

    public String getLeaseAgreementID() {
        return leaseAgreementID;
    }

    public void setLeaseAgreementID(String leaseAgreementID) {
        this.leaseAgreementID = leaseAgreementID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(int repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonthlyRentBill{" +
                "monthlyRentBillID='" + monthlyRentBillID + '\'' +
                ", apartmentID='" + apartmentID + '\'' +
                ", tenantID='" + tenantID + '\'' +
                ", leaseAgreementID='" + leaseAgreementID + '\'' +
                ", date=" + date +
                ", repaymentPeriod=" + repaymentPeriod +
                ", totalPayment=" + totalPayment +
                ", status='" + status + '\'' +
                '}';
    }
}
