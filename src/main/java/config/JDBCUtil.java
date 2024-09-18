//String dbUrl = "jdbc:sqlserver://DESKTOP-KRD2V7D: 1433;" +
//        "user=sa; password=123; databaseName=QuanLyThuVien; encrypt=false";

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;


public class JDBCUtil {
    private static String namedb = "quanlychothuecanho";
    private static String username="root";
	private static String password="";
//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
////              Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////              String url = "jdbc:sqlserver://PHAMNAM:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
////              String userName = "sa";
////              String password = "123456789";
////
////         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////         String url = "jdbc:sqlserver://TEN:1433;databaseName=qlcanho;trustServerCertificate=true";
////         String userName = "sa";
////         String password = "12345678";
////
//             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//             String url =
//             "jdbc:sqlserver://DESKTOP-2O5BBS1:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
//             String userName = "sa";
//             String password = "123456789";
//
////             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
////             String url = "jdbc:sqlserver://KAI:1433;databaseName=quanlychothuecanho;trustServerCertificate=true";
////             String userName = "sa";
////             String password = "123456";
//
//            connection = DriverManager.getConnection(url, userName, password);
////            printInfo(connection);
////            String sql = "SELECT * FROM Apartment";
////            try (Statement statement = connection.createStatement();
////                    ResultSet resultSet = statement.executeQuery(sql)) {
//////                System.out.println("Danh sách thông tin căn hộ:");
//////                System.out.println("---------------------------------");
//////                while (resultSet.next()) {
//////                    String maCanHo = resultSet.getString("apartmentID");
//////                    String soPhong = resultSet.getString("roomNumber");
//////                    System.out.println("Mã căn hộ: " + maCanHo);
//////                    System.out.println("Số phòng: " + soPhong);
//////                    System.out.println();
//////                }
//////                System.out.println("Kết nối thành công.");
////            } catch (SQLException e) {
////                e.printStackTrace();
////
////                System.out.println("Lỗi khi thực hiện truy vấn: " + e.getMessage());
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            System.out.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
//        }
//        return connection;
//    }
    public static Connection getConnection() {
        Connection connection = null;
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/"+namedb;

            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
//                System.out.println("Đã đóng kết nối với cơ sở dữ liệu.");
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