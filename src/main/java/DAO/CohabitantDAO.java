/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Cohabitant;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class CohabitantDAO implements DAOInterface<Cohabitant>{
    public static CohabitantDAO getInstance(){
        return new CohabitantDAO();
    }

    @Override
    public int insert(Cohabitant t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Cohabitant (cohabitantID, tenantID, firstName, lastName, phoneNumber, dob, gender, citizenIdentityCard) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getCohabitantID());
            preparedStatement.setString(2, t.getTenantID());
            preparedStatement.setString(3, t.getFirstName());
            preparedStatement.setString(4, t.getLastName());
            preparedStatement.setString(5, t.getPhoneNumber());
            preparedStatement.setDate(6, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(7, t.getGender());
            preparedStatement.setString(8, t.getCitizenIdentityCard());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Cohabitant t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Cohabitant SET tenantID = ?, firstName = ?, lastName = ?, phoneNumber = ?, dob = ?, gender = ?, citizenIdentityCard = ? WHERE cohabitantID = ?");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getTenantID());
            preparedStatement.setString(2, t.getFirstName());
            preparedStatement.setString(3, t.getLastName());
            preparedStatement.setString(4, t.getPhoneNumber());
            preparedStatement.setDate(5, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(6, t.getGender());
            preparedStatement.setString(7, t.getCitizenIdentityCard());
            preparedStatement.setString(8, t.getCohabitantID());

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
            String sql = "DELETE FROM Cohabitant WHERE cohabitantID = ?";
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
    public ArrayList<Cohabitant> selectAll() {
        ArrayList<Cohabitant> cohabitants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Cohabitant";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Cohabitant cohabitant = createCohabitantFromResultSet(resultSet);
                cohabitants.add(cohabitant);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cohabitants;
    }
    @Override
    public Cohabitant selectById(String t) {
        Cohabitant cohabitant = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Cohabitant WHERE cohabitantID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cohabitant = createCohabitantFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cohabitant;
    }
    private Cohabitant createCohabitantFromResultSet(ResultSet resultSet) throws SQLException {
        String cohabitantID = resultSet.getString("cohabitantID");
        String tenantID = resultSet.getString("tenantID");
        String lastName = resultSet.getString("lastName");
        String firstName = resultSet.getString("firstName");
        String phoneNumber = resultSet.getString("phoneNumber");
        LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
        String gender = resultSet.getString("gender");
        String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

        return new Cohabitant(cohabitantID, tenantID, firstName, lastName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
    }

    
}
