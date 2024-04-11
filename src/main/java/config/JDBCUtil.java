//String dbUrl = "jdbc:sqlserver://DESKTOP-KRD2V7D: 1433;" +
//        "user=sa; password=123; databaseName=QuanLyThuVien; encrypt=false";

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.*;

public class JDBCUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://DESKTOP-2O5BBS1:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456789";

            try (Connection connection = DriverManager.getConnection(url, userName, password)) {
                String sql = "SELECT * FROM Building";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                    System.out.println("Danh sách thông tin khách hàng:");
                    System.out.println("---------------------------------");
                    while (resultSet.next()) {
                        String maKH = resultSet.getString("buildingID");
                        String hoTen = resultSet.getString("name");
                        String diaChi = resultSet.getString("city");
                        System.out.println("Mã KH: " + maKH);
                        System.out.println("Họ Tên: " + hoTen);
                        System.out.println("City Building: " + diaChi);
                        System.out.println();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Đã đóng kết nối với cơ sở dữ liệu.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printInfo(Connection c) {
        try {
            if(c!=null) {

                DatabaseMetaData mtdt = c.getMetaData();
                System.out.println(mtdt.getDatabaseProductName());
                System.out.println(mtdt.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}