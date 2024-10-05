/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Apartment;
import DTO.Furniture;
import DTO.Tenant;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author NGOC
 */
public class FurnitureDAO implements DAOInterface<Furniture>{
    public static FurnitureDAO getInstance(){
        return new FurnitureDAO();
    }

    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT IFNULL(MAX(CAST(SUBSTRING(furnitureID, 5, LENGTH(furnitureID) - 4) AS INT)), 0) FROM Furniture";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int lastId = rs.getInt(1);
            return "FURN" + (lastId + 1);
        }
        return "FURN1"; // Nếu bảng rỗng thì bắt đầu từ APT1
    }

    @Override
    public int insert(Furniture t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO Furniture (furnitureID, apartmentID, name, condition, price) VALUES (?, ?, ?, ?, ?)";
            if (t.getFurnitureID() == null) {
                String newId = generateNewID(connection);
                t.setFurnitureID(newId); // Gán ID mới cho đối tượng Apartment
            }
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getFurnitureID());
            preparedStatement.setString(2, t.getApartmentID());
            preparedStatement.setString(3, t.getNameFurniture());
            preparedStatement.setString(4, t.getConditionFurniture());
            preparedStatement.setDouble(5, t.getPrice());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Furniture t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql  = "UPDATE Furniture SET apartmentID = ?, name = ?, condition = ?, price = ? WHERE furnitureID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, t.getApartmentID());
            preparedStatement.setString(2, t.getNameFurniture());
            preparedStatement.setString(3, t.getConditionFurniture());
            preparedStatement.setDouble(4, t.getPrice());
            preparedStatement.setString(5, t.getFurnitureID());

            // Thực thi câu lệnh SQL và nhận kết quả
            ketQua = preparedStatement.executeUpdate();

            // Đóng tài nguyên
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
            String sql = "DELETE FROM Furniture WHERE furnitureID = ?";
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
    public ArrayList<Furniture> selectAll() {
        ArrayList<Furniture> furnitures = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Furniture";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Furniture furniture = createFurnitureFromResultSet(resultSet);
                furnitures.add(furniture);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return furnitures;
    }

    @Override
    public Furniture selectById(String t) {
        Furniture furniture = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Furniture WHERE furnitureID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                furniture = createFurnitureFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return furniture;
    }

    private Furniture createFurnitureFromResultSet(ResultSet resultSet) throws SQLException {
        String furnitureID = resultSet.getString("furnitureID");
        String apartmentID = resultSet.getString("apartmentID");
        String nameFurniture = resultSet.getString("name");
        String conditionFurniture = resultSet.getString("condition");
        Double price = resultSet.getDouble("price");

        return new Furniture(furnitureID, apartmentID, nameFurniture, conditionFurniture, price);
    }

    public ArrayList<Furniture> search(String keyword, String buildingManagerID) {
        ArrayList<Furniture> searchResults = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * \n" +
                    "FROM Furniture A \n" +
                    "JOIN Apartment B ON A.apartmentID = B.apartmentID \n" +
                    "JOIN BuildingManager C ON B.buildingID = C.buildingID \n" +
                    "WHERE (A.furnitureID LIKE ? OR A.apartmentID LIKE ? OR A.name LIKE ?) \n" +
                    "AND C.buildingManagerID = ?\n";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, buildingManagerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Furniture furniture = createFurnitureFromResultSet(resultSet);
                searchResults.add(furniture);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
