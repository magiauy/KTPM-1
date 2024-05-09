package DAO;

import DTO.AdminsAccount;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class AdminsAccountDAO implements DAOInterface<AdminsAccount> {
    public static AdminsAccountDAO getInstance() {
        return new AdminsAccountDAO();
    }

    @Override
    public int insert(AdminsAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO AdminsAccount (username, password, id) VALUES (?, ?, ?)");

            preparedStatement.setString(1, t.getUsername());
            preparedStatement.setString(2, t.getPassword());
            preparedStatement.setString(3, t.getId());

            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(AdminsAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE AdminsAccount SET password = ? WHERE username = ?");

            preparedStatement.setString(1, t.getPassword());
            preparedStatement.setString(2, t.getUsername());

            result = preparedStatement.executeUpdate();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM AdminsAccount WHERE username = ?");

            preparedStatement.setString(1, t);

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<AdminsAccount> selectAll() {
        ArrayList<AdminsAccount> adminsAccounts = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM AdminsAccount";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                AdminsAccount adminsAccount = new AdminsAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
                adminsAccounts.add(adminsAccount);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminsAccounts;
    }

    @Override
    public AdminsAccount selectById(String ID) {
        AdminsAccount adminsAccount = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM AdminsAccount WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                adminsAccount = new AdminsAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminsAccount;
    }
}
