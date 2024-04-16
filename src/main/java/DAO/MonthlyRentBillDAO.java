/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.MonthlyRentBill;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class MonthlyRentBillDAO implements DAOInterface<MonthlyRentBill>{
    public static MonthlyRentBillDAO getInstance(){
        return new MonthlyRentBillDAO();
    }

    @Override
    public int insert(MonthlyRentBill monthlyRentBill) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, monthlyRentBill.getMonthlyRentBillID());
            preparedStatement.setString(2, monthlyRentBill.getApartmentID());
            preparedStatement.setInt(3, monthlyRentBill.getTenantID());
            preparedStatement.setInt(4, monthlyRentBill.getLeaseAgreementID());
            preparedStatement.setDate(5, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(6, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(7, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(8, monthlyRentBill.getStatus());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(MonthlyRentBill monthlyRentBill) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE MonthlyRentBill SET apartmentID = ?, tenantID = ?, leaseAgreementID = ?, date = ?, repaymentPeriod = ?, totalPayment = ?, status = ? WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, monthlyRentBill.getApartmentID());
            preparedStatement.setInt(2, monthlyRentBill.getTenantID());
            preparedStatement.setInt(3, monthlyRentBill.getLeaseAgreementID());
            preparedStatement.setDate(4, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(5, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(6, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(7, monthlyRentBill.getStatus());
            preparedStatement.setInt(8, monthlyRentBill.getMonthlyRentBillID());

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
            String sql = "DELETE FROM MonthlyRentBill WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

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
    public ArrayList<MonthlyRentBill> selectAll() {
        ArrayList<MonthlyRentBill> bills = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM MonthlyRentBill";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                MonthlyRentBill bill = createMonthlyRentBillFromResultSet(resultSet);
                bills.add(bill);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    @Override
    public MonthlyRentBill selectById(String ID) {
        MonthlyRentBill bill = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                bill = createMonthlyRentBillFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    private MonthlyRentBill createMonthlyRentBillFromResultSet(ResultSet resultSet) throws SQLException {
        int monthlyRentBillID = resultSet.getInt("monthlyRentBillID");
        String apartmentID = resultSet.getString("apartmentID");
        int tenantID = resultSet.getInt("tenantID");
        int leaseAgreementID = resultSet.getInt("leaseAgreementID");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int repaymentPeriod = resultSet.getInt("repaymentPeriod");
        Double totalPayment = resultSet.getDouble("totalPayment");
        String status = resultSet.getString("status");

        return new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, leaseAgreementID, date, repaymentPeriod, totalPayment, status);
    }
    
}
