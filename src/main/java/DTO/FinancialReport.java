package DTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FinancialReport {
    private int financialReportID;
    private String buildingID;
    private int buildingManagerID;
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
    public FinancialReport(int financialReportID, String buildingID, int buildingManagerID,
                           Date date, double monthlyRevenue, double monthlyOpex, double monthlyProfit) {
        this.financialReportID = financialReportID;
        this.buildingID = buildingID;
        this.buildingManagerID = buildingManagerID;
        this.date = date;
        this.monthlyRevenue = monthlyRevenue;
        this.monthlyOpex = monthlyOpex;
        this.monthlyProfit = monthlyProfit;
    }
    public int getFinancialReportID() {
        return financialReportID;
    }
    public void setFinancialReportID(int financialReportID) {
        this.financialReportID = financialReportID;
    }
    public String getBuildingID() {
        return buildingID;
    }
    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }
    public int getBuildingManagerID() {
        return buildingManagerID;
    }
    public void setBuildingManagerID(int buildingManagerID) {
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
    public static ArrayList<FinancialReport> getAllFinancialReportsFromDatabase() {
        ArrayList<FinancialReport> financialReports = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String url = "jdbc:sqlserver://PHAMNAM:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456789";

            try (Connection connection = DriverManager.getConnection(url, userName, password)) {
                String sql = "SELECT * FROM FinancialReport";
                PreparedStatement statement = connection.prepareStatement(sql);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int financialReportID = resultSet.getInt("financialReportID");
                    String buildingID = resultSet.getString("buildingID");
                    int buildingManagerID = resultSet.getInt("buildingManagerID");
                    Date date = resultSet.getDate("date");
                    double monthlyRevenue = resultSet.getDouble("monthlyRevenue");
                    double monthlyOpex = resultSet.getDouble("monthlyOpex");
                    double monthlyProfit = resultSet.getDouble("monthlyProfit");

                    FinancialReport financialReport = new FinancialReport(financialReportID, buildingID, buildingManagerID, date, monthlyRevenue, monthlyOpex, monthlyProfit);
                    financialReports.add(financialReport);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return financialReports;
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
