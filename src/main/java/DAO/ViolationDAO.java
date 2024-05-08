package DAO;

import DTO.Violation;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViolationDAO implements DAOInterface<Violation> {
    public static ViolationDAO getInstance() {
        return new ViolationDAO();
    }
    @Override
    public int insert(Violation violation) {
        int rowsAffected = 0;
        String query = "INSERT INTO Violation (violationID, name, price) VALUES (?, ?, ?)";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violation.getViolationID());
            preparedStatement.setString(2, violation.getName());
            preparedStatement.setDouble(3, violation.getPrice());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(Violation violation) {
        int rowsAffected = 0;
        String query = "UPDATE Violation SET name=?, price=? WHERE violationID=?";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violation.getName());
            preparedStatement.setDouble(2, violation.getPrice());
            preparedStatement.setString(3, violation.getViolationID());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(String violationID) {
        int rowsAffected = 0;
        String query = "DELETE FROM Violation WHERE violationID=?";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violationID);
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public ArrayList<Violation> selectAll() {
        ArrayList<Violation> violations = new ArrayList<>();
        String query = "SELECT * FROM Violation";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Violation violation = new Violation(
                        resultSet.getString("violationID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"));
                violations.add(violation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violations;
    }

    @Override
    public Violation selectById(String violationID) {
        Violation violation = null;
        String query = "SELECT * FROM Violation WHERE violationID=?";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violationID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                violation = new Violation(
                        resultSet.getString("violationID"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violation;
    }
}
