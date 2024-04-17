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
        Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://PHAMNAM:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
            String userName = "sa";
            String password = "123456789";

            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Kết nối thành công.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException("Không thể kết nối đến cơ sở dữ liệu.", e);
        }
        return connection;
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