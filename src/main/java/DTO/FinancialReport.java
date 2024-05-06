package DTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FinancialReport {
    private String financialReportID;
    private String buildingID;
    private String buildingManagerID;
    private Date date;
    private double monthlyRevenue;
    private double monthlyOpex;
    private double monthlyProfit;

    public FinancialReport(FinancialReport financialReport){
        financialReport.date = date;
        financialReport.financialReportID = financialReportID;
        financialReport.monthlyOpex = monthlyOpex;
        financialReport.monthlyProfit = monthlyProfit;
        financialReport.buildingManagerID = buildingManagerID;
        financialReport.buildingID = buildingID;
        financialReport.monthlyRevenue  = monthlyRevenue;

    }
    public FinancialReport(){
    }
    public FinancialReport(String financialReportID, String buildingID, String buildingManagerID,
                           Date date, double monthlyRevenue, double monthlyOpex, double monthlyProfit) {
        this.financialReportID = financialReportID;
        this.buildingID = buildingID;
        this.buildingManagerID = buildingManagerID;
        this.date = date;
        this.monthlyRevenue = monthlyRevenue;
        this.monthlyOpex = monthlyOpex;
        this.monthlyProfit = monthlyProfit;
    }
    public String getFinancialReportID() {
        return financialReportID;
    }
    public void setFinancialReportID(String financialReportID) {
        this.financialReportID = financialReportID;
    }
    public String getBuildingID() {
        return buildingID;
    }
    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }
    public String getBuildingManagerID() {
        return buildingManagerID;
    }
    public void setBuildingManagerID(String buildingManagerID) {
        this.buildingManagerID = buildingManagerID;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public double getMonthlyRevenue() {
        return monthlyRevenue;
    }
    public void setMonthlyRevenue(double monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }
    public double getMonthlyOpex() {
        return monthlyOpex;
    }
    public void setMonthlyOpex(double monthlyOpex) {
        this.monthlyOpex = monthlyOpex;
    }
    public double getMonthlyProfit() {
        return monthlyProfit;
    }
    public void setMonthlyProfit(double monthlyProfit) {
        this.monthlyProfit = monthlyProfit;
    }
    @Override
    public String toString() {
        return "FinancialReport{" +
                "financialReportID=" + financialReportID +
                ", buildingID=" + buildingID +
                ", buildingManagerID=" + buildingManagerID +
                ", date=" + date +
                ", monthlyRevenue=" + monthlyRevenue +
                ", monthlyOpex=" + monthlyOpex +
                ", monthlyProfit=" + monthlyProfit +
                '}';
    }
}
