/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.BuildingManager;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class BuildingManagerDAO implements DAOInterface<BuildingManager>{
    public static BuildingManagerDAO getInstance(){
        return new BuildingManagerDAO();
    }

    @Override
    public int insert(BuildingManager t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO BuildingManager (buildingManagerId, buildingId, lastName_BuildingManager, firstName_BuildingManager, phoneNumber_BuildingManager, dateOfBirthDay, gender_BuildingManager, citizenIdentityCard_BuildingManager, salary_BuildingManager) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getBuildingManagerId());
            preparedStatement.setString(2, t.getBuildingId());
            preparedStatement.setString(3, t.getLastName_BuildingManager());
            preparedStatement.setString(4, t.getFirstName_BuildingManager());
            preparedStatement.setString(5, t.getPhoneNumber_BuildingManager());
            preparedStatement.setDate(6, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(7, t.getGender_BuildingManager());
            preparedStatement.setString(8, t.getCitizenIdentityCard_BuildingManager());
            preparedStatement.setDouble(9, t.getSalary_BuildingManager());
            ketQua = preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(BuildingManager t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE BuildingManager SET buildingId = ?, lastName_BuildingManager = ?, firstName_BuildingManager = ?, phoneNumber_BuildingManager = ?, dateOfBirthDay = ?, gender_BuildingManager = ?, citizenIdentityCard_BuildingManager = ?, salary_BuildingManager = ? WHERE buildingManagerId = ?");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getBuildingId());
            preparedStatement.setString(2, t.getLastName_BuildingManager());
            preparedStatement.setString(3, t.getFirstName_BuildingManager());
            preparedStatement.setString(4, t.getPhoneNumber_BuildingManager());
            preparedStatement.setDate(5, Date.valueOf(t.getDateOfBirthDay()));
            preparedStatement.setString(6, t.getGender_BuildingManager());
            preparedStatement.setString(7, t.getCitizenIdentityCard_BuildingManager());
            preparedStatement.setDouble(8, t.getSalary_BuildingManager());
            preparedStatement.setString(9, t.getBuildingManagerId());

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
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM BuildingManager WHERE buildingManagerId = ?");

            // Thiết lập giá trị tham số trong câu lệnh SQL
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
    public ArrayList<BuildingManager> selectAll() {
        ArrayList<BuildingManager> buildingManagers = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM BuildingManager";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                BuildingManager buildingManager = createBuildingManagerFromResultSet(resultSet);
                buildingManagers.add(buildingManager);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingManagers;
    }

    @Override
    public BuildingManager selectById(String ID) {
        BuildingManager buildingManager = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM BuildingManager WHERE buildingManagerId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                buildingManager = createBuildingManagerFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingManager;
    }

    private BuildingManager createBuildingManagerFromResultSet(ResultSet resultSet) throws SQLException {
        String buildingManagerId = resultSet.getString("buildingManagerId");
        String buildingId = resultSet.getString("buildingId");
        String lastName_BuildingManager = resultSet.getString("lastName_BuildingManager");
        String firstName_BuildingManager = resultSet.getString("firstName_BuildingManager");
        String phoneNumber_BuildingManager = resultSet.getString("phoneNumber_BuildingManager");
        LocalDate dateOfBirthDay = resultSet.getDate("dateOfBirthDay").toLocalDate();
        String gender_BuildingManager = resultSet.getString("gender_BuildingManager");
        String citizenIdentityCard_BuildingManager = resultSet.getString("citizenIdentityCard_BuildingManager");
        double salary_BuildingManager = resultSet.getDouble("salary_BuildingManager");

        return new BuildingManager(buildingManagerId, buildingId, lastName_BuildingManager, firstName_BuildingManager, phoneNumber_BuildingManager, dateOfBirthDay, gender_BuildingManager, citizenIdentityCard_BuildingManager, salary_BuildingManager);
    }

    
}
