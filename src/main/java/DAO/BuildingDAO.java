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

    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT ISNULL(MAX(CAST(SUBSTRING(buildingID, 2, LEN(buildingID) - 1) AS INT)), 0) FROM Building";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int lastId = rs.getInt(1);
            return "B" + (lastId + 1);
        }
        return "B1"; // Nếu bảng rỗng thì bắt đầu từ APT1
    }
   
    @Override
    public int insert(Building building) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

            if (building.getBuildingId() == null) {
                String newId = generateNewID(connection);
                building.setBuildingId(newId);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Building (buildingId, name, city, address, numberOfApartment) VALUES (?, ?, ?, ?, ?)");

            preparedStatement.setString(1, building.getBuildingId());
            preparedStatement.setString(2, building.getNameBuilding());
            preparedStatement.setString(3, building.getCity_Building());
            preparedStatement.setString(4, building.getAddress_Building());
            preparedStatement.setInt(5, building.getNumberOfApartment_Building());

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
                    "UPDATE Building SET name = ?, city = ?, address = ?, numberOfApartment = ? WHERE buildingID = ?");
            preparedStatement.setString(1, building.getNameBuilding());
            preparedStatement.setString(2, building.getCity_Building());
            preparedStatement.setString(3, building.getAddress_Building());
            preparedStatement.setInt(4, building.getNumberOfApartment_Building());
            preparedStatement.setString(5, building.getBuildingId());

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
                String address_Building = resultSet.getString("address");
                int numberOfApartment_Building = resultSet.getInt("numberOfApartment");

                Building building = new Building(buildingId, nameBuilding, city_Building, address_Building, numberOfApartment_Building);
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
                String address_Building = resultSet.getString("address");
                int numberOfApartment_Building = resultSet.getInt("numberOfApartment");

                building = new Building(buildingId, nameBuilding, city_Building, address_Building, numberOfApartment_Building);
            }
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return building;
    }

    public ArrayList<Building> getBuildingsWithoutManager() {
        ArrayList<Building> buildingsWithoutManager = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();

            // Câu truy vấn để lấy các tòa nhà không có quản lý
            String sql = "SELECT b.* " +
                    "FROM Building b " +
                    "LEFT JOIN BuildingManager bm ON b.buildingID = bm.buildingID " +
                    "WHERE bm.buildingID IS NULL";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Duyệt qua kết quả trả về
            while (resultSet.next()) {
                String buildingId = resultSet.getString("buildingID");
                String nameBuilding = resultSet.getString("name");
                String city_Building = resultSet.getString("city");
                String address_Building = resultSet.getString("address");
                int numberOfApartment_Building = resultSet.getInt("numberOfApartment");

                // Tạo đối tượng Building và thêm vào danh sách
                Building building = new Building(buildingId, nameBuilding, city_Building, address_Building, numberOfApartment_Building);
                buildingsWithoutManager.add(building);
            }

            // Đóng tài nguyên
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buildingsWithoutManager;
    }

}
