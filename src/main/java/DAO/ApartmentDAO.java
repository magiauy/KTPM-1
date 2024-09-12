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

    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT ISNULL(MAX(CAST(SUBSTRING(apartmentID, 4, LEN(apartmentID) - 3) AS INT)), 0) FROM Apartment";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int lastId = rs.getInt(1);
            return "APT" + (lastId + 1);
        }
        return "APT1"; // Nếu bảng rỗng thì bắt đầu từ APT1
    }

    @Override
    public int insert(Apartment apartment) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

            if (apartment.getApartmentID() == null) {
                String newId = generateNewID(connection);
                apartment.setApartmentID(newId); // Gán ID mới cho đối tượng Apartment
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Apartment (apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            // Thiết lập các giá trị tham số trong câu lệnh SQL
            preparedStatement.setString(1, apartment.getApartmentID());
            preparedStatement.setString(2, (apartment.getBuildingID() != null) ? apartment.getBuildingID() : null);
            preparedStatement.setString(3, apartment.getRoomNumber());
            preparedStatement.setDouble(4, apartment.getArea());
            preparedStatement.setInt(5, apartment.getBedrooms());
            preparedStatement.setInt(6, apartment.getBathrooms());
            preparedStatement.setString(7, (apartment.getFurniture() != null) ? apartment.getFurniture() : null);
            preparedStatement.setString(8, (apartment.getStatus() != null) ? apartment.getStatus() : null);

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
            String sql = "UPDATE Apartment SET buildingID=?, roomNumber=?, area=?, bedrooms=?, bathrooms=?, furniture=?, status=? WHERE apartmentID=?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, apartment.getBuildingID());
            pst.setString(2, apartment.getRoomNumber());
            pst.setDouble(3, apartment.getArea());
            pst.setInt(4, apartment.getBedrooms());
            pst.setInt(5, apartment.getBathrooms());
            pst.setString(6, apartment.getFurniture());
            pst.setString(7, apartment.getStatus());
            pst.setString(8, apartment.getApartmentID());

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
            String sql = "SELECT B.apartmentID, " + // Added space after comma
                    "CASE " +
                    "    WHEN LA.apartmentID IS NOT NULL THEN N'Đã được thuê' " +
                    "    ELSE N'Còn trống' " + 
                    "END AS status, B.area, B.bathrooms, B.bedrooms, B.buildingID, B.furniture, B.roomNumber " + 
                                                                                                                    
                                                                                                                 
                                                                                                                  
                    "FROM Apartment B " + // Added space after B
                    "LEFT JOIN LeaseAgreement LA ON B.apartmentID = LA.apartmentID"; 

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
        Double area = resultSet.getDouble("area");
        int bedrooms = resultSet.getInt("bedrooms");
        int bathrooms = resultSet.getInt("bathrooms");
        String furniture = resultSet.getString("furniture");
        String status = resultSet.getString("status");

        return new Apartment(apartmentID, buildingID, roomNumber, area, bedrooms, bathrooms, furniture, status);
    }

    public ArrayList<Apartment> search(String keyword, String buildingManagerID) {
        ArrayList<Apartment> searchResults = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Apartment A JOIN BuildingManager B ON A.buildingID = B.buildingID WHERE (A.apartmentID LIKE ? OR A.roomNumber LIKE ? OR A.furniture LIKE ?) AND B.buildingManagerID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, buildingManagerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Apartment apartment = createApartmentFromResultSet(resultSet);
                searchResults.add(apartment);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
    
    public ArrayList<Apartment> getApartmentsByBuildingID(String buildingID) {
        ArrayList<Apartment> apartments = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Apartment WHERE buildingID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, buildingID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Apartment apartment = createApartmentFromResultSet(resultSet);
                apartments.add(apartment);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }

    

}
