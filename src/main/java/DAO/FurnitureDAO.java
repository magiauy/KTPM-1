/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Furniture;
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

    @Override
    public int insert(Furniture t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO Furniture (furnitureID, apartmentID, nameFurniture, conditionFurniture, price) VALUES (?, ?, ?, ?, ?)";
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
            String sql  = "UPDATE Furniture SET apartmentID = ?, nameFurniture = ?, conditionFurniture = ?, price = ? WHERE furnitureID = ?";
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
        String nameFurniture = resultSet.getString("nameFurniture");
        String conditionFurniture = resultSet.getString("conditionFurniture");
        Double price = resultSet.getDouble("price");

        return new Furniture(furnitureID, apartmentID, nameFurniture, conditionFurniture, price);
    }


}
