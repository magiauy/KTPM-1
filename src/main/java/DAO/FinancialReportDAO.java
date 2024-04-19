package DAO;

import DTO.FinancialReport;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FinancialReportDAO implements DAOInterface<FinancialReport> {
    public FinancialReportDAO() {
    }

    public static FinancialReportDAO getInstance(){
        return new FinancialReportDAO();
    }
    @Override
    public int insert(FinancialReport financialReport) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO FinancialReport (financialReportID, buildingID, buildingManagerID, date, monthlyRevenue, monthlyOpex, monthlyProfit) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, financialReport.getFinancialReportID());
            preparedStatement.setString(2, financialReport.getBuildingID());
            preparedStatement.setString(3, financialReport.getBuildingManagerID());
            preparedStatement.setDate(4, new Date(financialReport.getDate().getTime()));
            preparedStatement.setDouble(5, financialReport.getMonthlyRevenue());
            preparedStatement.setDouble(6, financialReport.getMonthlyOpex());
            preparedStatement.setDouble(7, financialReport.getMonthlyProfit());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(FinancialReport financialReport) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE FinancialReport SET buildingID = ?, buildingManagerID = ?, date = ?, monthlyRevenue = ?, monthlyOpex = ?, monthlyProfit = ? WHERE financialReportID = ?");

            preparedStatement.setString(1, financialReport.getBuildingID());
            preparedStatement.setString(2, financialReport.getBuildingManagerID());
            preparedStatement.setDate(3, new Date(financialReport.getDate().getTime()));
            preparedStatement.setDouble(4, financialReport.getMonthlyRevenue());
            preparedStatement.setDouble(5, financialReport.getMonthlyOpex());
            preparedStatement.setDouble(6, financialReport.getMonthlyProfit());
            preparedStatement.setString(7, financialReport.getFinancialReportID());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(String ID) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM FinancialReport WHERE financialReportID = ?");
            preparedStatement.setString(1, ID);

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<FinancialReport> selectAll() {
        ArrayList<FinancialReport> financialReports = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM FinancialReport";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String financialReportID = resultSet.getString("financialReportID");
                String buildingID = resultSet.getString("buildingID");
                String buildingManagerID = resultSet.getString("buildingManagerID");
                Date date = resultSet.getDate("Date");
                double monthlyRevenue = resultSet.getDouble("monthlyRevenue");
                double monthlyOpex = resultSet.getDouble("monthlyOpex");
                double monthlyProfit = resultSet.getDouble("monthlyProfit");

                FinancialReport financialReport = new FinancialReport(financialReportID, buildingID, buildingManagerID, date, monthlyRevenue, monthlyOpex, monthlyProfit);
                financialReports.add(financialReport);
            }
            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return financialReports;
    }


    @Override
    public FinancialReport selectById(String ID) {
        FinancialReport financialReport = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM FinancialReport WHERE financialReportID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String financialReportID = resultSet.getString("financialReportID");
                String buildingID = resultSet.getString("buildingID");
                String buildingManagerID = resultSet.getString("buildingManagerID");
                Date date = resultSet.getDate("Date");
                double monthlyRevenue = resultSet.getDouble("monthlyRevenue");
                double monthlyOpex = resultSet.getDouble("monthlyOpex");
                double monthlyProfit = resultSet.getDouble("monthlyProfit");

                financialReport = new FinancialReport(financialReportID, buildingID, buildingManagerID, date, monthlyRevenue, monthlyOpex, monthlyProfit);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return financialReport;
    }
}