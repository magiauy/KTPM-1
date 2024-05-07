package DAO;

import DTO.Building;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BuildingDAO implements DAOInterface<Building> {
  

    public static BuildingDAO getInstance() {
        return new BuildingDAO();
    }

   
    @Override
    public int insert(Building building) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Building (buildingId, name, city, district, address, numberOfApartment) VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, building.getBuildingId());
            preparedStatement.setString(2, building.getNameBuilding());
            preparedStatement.setString(3, building.getCity_Building());
            preparedStatement.setString(4, building.getDistrict_Building());
            preparedStatement.setString(5, building.getAddress_Building());
            preparedStatement.setInt(6, building.getNumberOfApartment_Building());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
   


    public int update(Building building) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Building SET name = ?, city = ?, district = ?, address = ?, numberOfApartment = ? WHERE buildingID = ?");
            preparedStatement.setString(1, building.getNameBuilding());
            preparedStatement.setString(2, building.getCity_Building());
            preparedStatement.setString(3, building.getDistrict_Building());
            preparedStatement.setString(4, building.getAddress_Building());
            preparedStatement.setInt(5, building.getNumberOfApartment_Building());
            preparedStatement.setString(6, building.getBuildingId());

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
                    "DELETE FROM Building WHERE buildingID = ?");
            preparedStatement.setString(1, ID);

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ
        }
        return ketQua;
    }

    @Override
    public ArrayList<Building> selectAll() {
        ArrayList<Building> buildings = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Building";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String buildingId = resultSet.getString("buildingID");
                String nameBuilding = resultSet.getString("name");
                String city_Building = resultSet.getString("city");
                String district_Building = resultSet.getString("district");
                String address_Building = resultSet.getString("address");
                int numberOfApartment_Building = resultSet.getInt("numberOfApartment");

                Building building = new Building(buildingId, nameBuilding, city_Building, district_Building, address_Building, numberOfApartment_Building);
                buildings.add(building);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildings;
    }

    @Override
    public Building selectById(String ID) {
        Building building = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Building WHERE buildingID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String buildingId = resultSet.getString("buildingID");
                String nameBuilding = resultSet.getString("name");
                String city_Building = resultSet.getString("city");
                String district_Building = resultSet.getString("district");
                String address_Building = resultSet.getString("address");
                int numberOfApartment_Building = resultSet.getInt("numberOfApartment");

                building = new Building(buildingId, nameBuilding, city_Building, district_Building, address_Building, numberOfApartment_Building);
            }
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return building;
    }
}
