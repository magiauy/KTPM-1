package DAO;

import DTO.CustomersAccount;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class CustomersAccountDAO implements DAOInterface<CustomersAccount> {
    public static CustomersAccountDAO getInstance() {
        return new CustomersAccountDAO();
    }

    @Override
    public int insert(CustomersAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO CustomersAccount (username, password, id) VALUES (?, ?, ?)");

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
    public int update(CustomersAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE CustomersAccount SET password = ? WHERE username = ?");

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
                    "DELETE FROM CustomersAccount WHERE username = ?");

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
    public ArrayList<CustomersAccount> selectAll() {
        ArrayList<CustomersAccount> customersAccounts = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM CustomersAccount";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                CustomersAccount customersAccount = new CustomersAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
                customersAccounts.add(customersAccount);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersAccounts;
    }

    @Override
    public CustomersAccount selectById(String ID) {
        CustomersAccount customersAccount = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM CustomersAccount WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customersAccount = new CustomersAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersAccount;
    }
}
