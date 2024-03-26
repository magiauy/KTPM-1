package config;

import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        JDBCUtil.printInfo(connection);
        JDBCUtil.closeConnection(connection);
    }
}
