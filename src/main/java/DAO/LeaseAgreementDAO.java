/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.LeaseAgreement;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class LeaseAgreementDAO implements DAOInterface<LeaseAgreement>{
    public static LeaseAgreementDAO getInstance(){
        return new LeaseAgreementDAO();
    }

    @Override
    public int insert(LeaseAgreement t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO LeaseAgreement (leaseAgreementID, apartmentID, tenantID, buildingManagerID, signingDate, leaseStartDate, leaseEndDate, leaseTerm, deposit, monthlyRent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getLeaseAgreementID());
            preparedStatement.setString(2, t.getApartmentID());
            preparedStatement.setString(3, t.getTenantID());
            preparedStatement.setString(4, t.getBuildingManagerID());
            preparedStatement.setDate(5, Date.valueOf(t.getSigningDate()));
            preparedStatement.setDate(6, Date.valueOf(t.getLeaseStartDate()));
            preparedStatement.setDate(7, Date.valueOf(t.getLeaseEndDate()));
            preparedStatement.setDouble(8, t.getLeaseTerm());
            preparedStatement.setDouble(9, t.getDeposit());
            preparedStatement.setDouble(10, t.getMonthlyRent());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(LeaseAgreement t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE LeaseAgreement SET apartmentID = ?, tenantID = ?, buildingManagerID = ?, signingDate = ?, leaseStartDate = ?, leaseEndDate = ?, leaseTerm = ?, deposit = ?, monthlyRent = ? WHERE leaseAgreementID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, t.getApartmentID());
            preparedStatement.setString(2, t.getTenantID());
            preparedStatement.setString(3, t.getBuildingManagerID());
            preparedStatement.setDate(4, Date.valueOf(t.getSigningDate()));
            preparedStatement.setDate(5, Date.valueOf(t.getLeaseStartDate()));
            preparedStatement.setDate(6, Date.valueOf(t.getLeaseEndDate()));
            preparedStatement.setDouble(7, t.getLeaseTerm());
            preparedStatement.setDouble(8, t.getDeposit());
            preparedStatement.setDouble(9, t.getMonthlyRent());
            preparedStatement.setString(10, t.getLeaseAgreementID());

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
            String sql = "DELETE FROM LeaseAgreement WHERE leaseAgreementID = ?";
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
    public ArrayList<LeaseAgreement> selectAll() {
        ArrayList<LeaseAgreement> leaseAgreements = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM LeaseAgreement";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                LeaseAgreement leaseAgreement = createLeaseAgreementFromResultSet(resultSet);
                leaseAgreements.add(leaseAgreement);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaseAgreements;
    }

    @Override
    public LeaseAgreement selectById(String ID) {
        LeaseAgreement leaseAgreement = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM LeaseAgreement WHERE leaseAgreementID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                leaseAgreement = createLeaseAgreementFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaseAgreement;
    }

    private LeaseAgreement createLeaseAgreementFromResultSet(ResultSet resultSet) throws SQLException {
        String leaseAgreementID = resultSet.getString("leaseAgreementID");
        String apartmentID = resultSet.getString("apartmentID");
        String tenantID = resultSet.getString("tenantID");
        String buildingManagerID = resultSet.getString("buildingManagerID");
        LocalDate signingDate = resultSet.getDate("signingDate").toLocalDate();
        LocalDate leaseStartDate = resultSet.getDate("leaseStartDate").toLocalDate();
        LocalDate leaseEndDate = resultSet.getDate("leaseEndDate").toLocalDate();
        Double leaseTerm = resultSet.getDouble("leaseTerm");
        Double deposit = resultSet.getDouble("deposit");
        Double monthlyRent = resultSet.getDouble("monthlyRent");

        return new LeaseAgreement(leaseAgreementID, apartmentID, tenantID, buildingManagerID, signingDate, leaseStartDate, leaseEndDate, leaseTerm, deposit, monthlyRent);
    }


}
