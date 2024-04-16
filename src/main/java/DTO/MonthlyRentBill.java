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
    private int monthlyRentBillID;;
    private String apartmentID;
    private int tenantID;
    private int leaseAgreementID;
    private LocalDate date;
    private int repaymentPeriod;
    private Double totalPayment;
    private String status;

    public MonthlyRentBill(int monthlyRentBillID, String apartmentID, int tenantID,
                           int leaseAgreementID, LocalDate date, int repaymentPeriod,
                           Double totalPayment, String status) {
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

    public MonthlyRentBill(MonthlyRentBill monthlyRentBill) {
        this.monthlyRentBillID = monthlyRentBill.monthlyRentBillID;
        this.apartmentID = monthlyRentBill.apartmentID;
        this.tenantID = monthlyRentBill.tenantID;
        this.leaseAgreementID = monthlyRentBill.leaseAgreementID;
        this.date = monthlyRentBill.date;
        this.repaymentPeriod = monthlyRentBill.repaymentPeriod;
        this.totalPayment = monthlyRentBill.totalPayment;
        this.status = monthlyRentBill.status;
    }

    public int getMonthlyRentBillID() {
        return monthlyRentBillID;
    }

    public void setMonthlyRentBillID(int monthlyRentBillID) {
        this.monthlyRentBillID = monthlyRentBillID;
    }

    public String getApartmentID() {
        return apartmentID;
    }

    public void setApartmentID(String apartmentID) {
        this.apartmentID = apartmentID;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getLeaseAgreementID() {
        return leaseAgreementID;
    }

    public void setLeaseAgreementID(int leaseAgreementID) {
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
    public static ArrayList<MonthlyRentBill> getAllMonthlyRentBillsFromDatabase() {
        ArrayList<MonthlyRentBill> monthlyRentBills = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://PHAMNAM:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456789";

            try (Connection connection = DriverManager.getConnection(url, userName, password)) {
                String sql = "SELECT * FROM MonthlyRentBill";
                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int monthlyRentBillID = resultSet.getInt("monthlyRentBillID");
                    String apartmentID = resultSet.getString("apartmentID");
                    int tenantID = resultSet.getInt("tenantID");
                    int leaseAgreementID = resultSet.getInt("leaseAgreementID");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    int repaymentPeriod = resultSet.getInt("repaymentPeriod");
                    double totalPayment = resultSet.getDouble("totalPayment");
                    String status = resultSet.getString("status");

                    MonthlyRentBill monthlyRentBill = new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status);
                    monthlyRentBills.add(monthlyRentBill);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return monthlyRentBills;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
