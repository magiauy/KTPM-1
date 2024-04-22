/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Apartment;
import DTO.Cohabitant;
import DTO.Tenant;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class TenantDAO implements DAOInterface<Tenant>{
    public static TenantDAO getInstance(){
        return new TenantDAO();
    }

    @Override
    public int insert(Tenant t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard) VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getTenantID());
            preparedStatement.setString(2, t.getLastName());
            preparedStatement.setString(3, t.getFirstName());
            preparedStatement.setString(4, t.getPhoneNumber());
            preparedStatement.setDate(5, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(6, t.getGender());
            preparedStatement.setString(7, t.getCitizenIdentityCard());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Tenant t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Tenant SET lastName = ?, firstName = ?, phoneNumber = ?, dob = ?, gender = ?, citizenIdentityCard = ? WHERE tenantID = ?");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getLastName());
            preparedStatement.setString(2, t.getFirstName());
            preparedStatement.setString(3, t.getPhoneNumber());
            preparedStatement.setDate(4, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(5, t.getGender());
            preparedStatement.setString(6, t.getCitizenIdentityCard());
            preparedStatement.setString(7, t.getTenantID());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(String t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "DELETE FROM Tenant WHERE tenantID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, t);

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<Tenant> selectAll() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Tenant";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Tenant tenant = createTenantFromResultSet(resultSet);
                tenants.add(tenant);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenants;
    }

    @Override
    public Tenant selectById(String ID) {
        Tenant tenant = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Tenant WHERE tenantID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                tenant = createTenantFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    private Tenant createTenantFromResultSet(ResultSet resultSet) throws SQLException {
        String tenantID = resultSet.getString("tenantID");
        String lastName = resultSet.getString("lastName");
        String firstName = resultSet.getString("firstName");
        String phoneNumber = resultSet.getString("phoneNumber");
        LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
        String gender = resultSet.getString("gender");
        String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

        return new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
    }
}
