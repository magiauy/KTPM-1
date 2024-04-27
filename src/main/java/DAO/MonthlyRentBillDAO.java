package DAO;

import DTO.MonthlyRentBill;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class MonthlyRentBillDAO implements DAOInterface<MonthlyRentBill> {

    public MonthlyRentBillDAO() {
    }

    public static MonthlyRentBillDAO getInstance() {
        return new MonthlyRentBillDAO();
    }

    @Override
    public int insert(MonthlyRentBill monthlyRentBill) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, monthlyRentBill.getMonthlyRentBillID());
            preparedStatement.setString(2, monthlyRentBill.getApartmentID());
            preparedStatement.setString(3, monthlyRentBill.getTenantID());
            preparedStatement.setString(4, monthlyRentBill.getLeaseAgreementID());
            preparedStatement.setDate(5, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(6, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(7, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(8, monthlyRentBill.getStatus());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(MonthlyRentBill monthlyRentBill) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE MonthlyRentBill SET apartmentID = ?, tenantID = ?, leaseAgreementID = ?, date = ?, repaymentPeriod = ?, totalPayment = ?, status = ? WHERE monthlyRentBillID = ?");

            preparedStatement.setString(1, monthlyRentBill.getApartmentID());
            preparedStatement.setString(2, monthlyRentBill.getTenantID());
            preparedStatement.setString(3, monthlyRentBill.getLeaseAgreementID());
            preparedStatement.setDate(4, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(5, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(6, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(7, monthlyRentBill.getStatus());
            preparedStatement.setString(8, monthlyRentBill.getMonthlyRentBillID());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String ID) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM MonthlyRentBill WHERE monthlyRentBillID = ?");
            preparedStatement.setString(1, ID);

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<MonthlyRentBill> selectAll() {
        ArrayList<MonthlyRentBill> monthlyRentBills = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM MonthlyRentBill";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                String apartmentID = resultSet.getString("apartmentID");
                String tenantID = resultSet.getString("tenantID");
                String leaseAgreementID = resultSet.getString("leaseAgreementID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int repaymentPeriod = resultSet.getInt("repaymentPeriod");
                double totalPayment = resultSet.getDouble("totalPayment");
                String status = resultSet.getString("status");

                MonthlyRentBill monthlyRentBill = new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status);
                monthlyRentBills.add(monthlyRentBill);
            }
            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBills;
    }

    @Override
    public MonthlyRentBill selectById(String ID) {
        MonthlyRentBill monthlyRentBill = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                String apartmentID = resultSet.getString("apartmentID");
                String tenantID = resultSet.getString("tenantID");
                String leaseAgreementID = resultSet.getString("leaseAgreementID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int repaymentPeriod = resultSet.getInt("repaymentPeriod");
                double totalPayment = resultSet.getDouble("totalPayment");
                String status = resultSet.getString("status");

                monthlyRentBill = new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBill;
    }
}
