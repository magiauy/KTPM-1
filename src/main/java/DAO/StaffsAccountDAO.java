package DAO;

import DTO.StaffsAccount;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class StaffsAccountDAO implements DAOInterface<StaffsAccount> {
    public static StaffsAccountDAO getInstance() {
        return new StaffsAccountDAO();
    }

    @Override
    public int insert(StaffsAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO StaffsAccount (username, password, id) VALUES (?, ?, ?)");

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
    public int update(StaffsAccount t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE StaffsAccount SET password = ? WHERE username = ?");

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
                    "DELETE FROM StaffsAccount WHERE username = ?");

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
    public ArrayList<StaffsAccount> selectAll() {
        ArrayList<StaffsAccount> staffsAccounts = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM StaffsAccount";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                StaffsAccount staffsAccount = new StaffsAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
                staffsAccounts.add(staffsAccount);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffsAccounts;
    }

    @Override
    public StaffsAccount selectById(String ID) {
        StaffsAccount staffsAccount = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM StaffsAccount WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                staffsAccount = new StaffsAccount(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("id"));
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffsAccount;
    }
}
