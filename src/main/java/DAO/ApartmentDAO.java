/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Apartment;

import java.sql.*;

import config.JDBCUtil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NGOC
 */
public class ApartmentDAO implements DAOInterface<Apartment>{
    public static ApartmentDAO getInstance(){
        return new ApartmentDAO();
    }
    @Override
    public int insert(Apartment apartment) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Apartment (apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture) VALUES (?, ?, ?, ?, ?, ?, ?)");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, (apartment.getApartmentID() != null) ? apartment.getApartmentID() : null);
            preparedStatement.setString(2, (apartment.getBuildingID() != null) ? apartment.getBuildingID() : null);
            preparedStatement.setString(3, (apartment.getRoomNumber() != null) ? apartment.getRoomNumber() : null);
            preparedStatement.setDouble(4, apartment.getArea());
            preparedStatement.setInt(5, apartment.getBedrooms());
            preparedStatement.setInt(6, apartment.getBathrooms());
            //Luu Y
//            preparedStatement.setDouble(4, (t.getArea() != null) ? t.getArea() : null);
//            preparedStatement.setInt(5, (t.getBedrooms() == -1) ? t.getBedrooms() : 0);
//            preparedStatement.setInt(6, (t.getBathrooms() == -1) ? t.getBathrooms() : 0);
            //??
            preparedStatement.setString(7, (apartment.getFurniture() != null) ? apartment.getFurniture() : null);

            // Thực thi câu lệnh SQL và nhận kết quả
            ketQua = preparedStatement.executeUpdate();

            // Đóng các tài nguyên
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    @Override
    public int update(Apartment apartment) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE Apartment SET buildingID=?, roomNumber=?, area=?, bedrooms=?, bathrooms=?, furniture=? WHERE apartmentID=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, apartment.getBuildingID());
            pst.setString(2, apartment.getRoomNumber());
            pst.setDouble(3, apartment.getArea());
            pst.setInt(4, apartment.getBedrooms());
            pst.setInt(5, apartment.getBathrooms());
            pst.setString(6, apartment.getFurniture());
            pst.setString(7, apartment.getApartmentID());

            ketQua = pst.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketQua;
    }
    @Override
    public int delete(String apartmentID) {
        int ketqua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM Apartment WHERE apartmentID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, apartmentID);

            ketqua = pst.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (SQLException ex) {
            Logger.getLogger(ApartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ketqua;
    }
    @Override
    public ArrayList<Apartment> selectAll() {
        ArrayList<Apartment> apartments = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Apartment";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Apartment apartment = createApartmentFromResultSet(resultSet);
                apartments.add(apartment);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }
    @Override
    public Apartment selectById(String ID) {
        Apartment apartment = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Apartment WHERE apartmentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                apartment = createApartmentFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartment;
    }
    private Apartment createApartmentFromResultSet(ResultSet resultSet) throws SQLException {
        String apartmentID = resultSet.getString("apartmentID");
        String buildingID = resultSet.getString("buildingID");
        String roomNumber = resultSet.getString("roomNumber");
        double area = resultSet.getDouble("area");
        int bedrooms = resultSet.getInt("bedrooms");
        int bathrooms = resultSet.getInt("bathrooms");
        String furniture = resultSet.getString("furniture");

        return new Apartment(apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture);
    }
}
