package DAO;

import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import DTO.Tenant;

public class TenantDAO implements DAOInterface<Tenant> {

    public static TenantDAO getInstance() {
        return new TenantDAO();
    }

    @Override
    public int insert(Tenant tenant) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, tenant.getTenantID());
            preparedStatement.setString(2, tenant.getLastName());
            preparedStatement.setString(3, tenant.getFirstName());
            preparedStatement.setString(4, tenant.getPhoneNumber());
            preparedStatement.setDate(5, java.sql.Date.valueOf(tenant.getDateOfBirthDay()));
            preparedStatement.setString(6, tenant.getGender());
            preparedStatement.setString(7, tenant.getCitizenIdentityCard());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Tenant tenant) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Tenant SET lastName = ?, firstName = ?, phoneNumber = ?, dob = ?, gender = ?, citizenIdentityCard = ? WHERE tenantID = ?");
            preparedStatement.setString(1, tenant.getLastName());
            preparedStatement.setString(2, tenant.getFirstName());
            preparedStatement.setString(3, tenant.getPhoneNumber());
            preparedStatement.setDate(4, java.sql.Date.valueOf(tenant.getDateOfBirthDay()));
            preparedStatement.setString(5, tenant.getGender());
            preparedStatement.setString(6, tenant.getCitizenIdentityCard());
            preparedStatement.setString(7, tenant.getTenantID());

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
                    "DELETE FROM Tenant WHERE tenantID = ?");
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
    public ArrayList<Tenant> selectAll() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Tenant";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                Tenant tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
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
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
            }
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }
}
