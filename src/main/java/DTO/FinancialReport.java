package DTO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class FinancialReport {
    private String financialReportID;
    private String buildingID;
    private String buildingManagerID;
    private LocalDate date;
    private Float monthlyRevenue;
    private Float monthlyOpex;
    private Float monthlyProfit;

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
                           LocalDate date, Float monthlyRevenue, Float monthlyOpex, Float monthlyProfit) {
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
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Float getMonthlyRevenue() {
        return monthlyRevenue;
    }
    public void setMonthlyRevenue(Float monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }
    public Float getMonthlyOpex() {
        return monthlyOpex;
    }
    public void setMonthlyOpex(Float monthlyOpex) {
        this.monthlyOpex = monthlyOpex;
    }
    public Float getMonthlyProfit() {
        return monthlyProfit;
    }
    public void setMonthlyProfit(Float monthlyProfit) {
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
