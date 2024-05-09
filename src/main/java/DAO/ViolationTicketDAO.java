package DAO;

import DTO.ServiceTicket;
import DTO.ViolationTicket;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViolationTicketDAO implements DAOInterface<ViolationTicket> {
    public static ViolationTicketDAO getInstance() {
        return new ViolationTicketDAO();
    }
    @Override
    public int insert(ViolationTicket violationTicket) {
        int rowsAffected = 0;
        String query = "INSERT INTO ViolationTicket (violationID, monthlyRentBillID, price, Date, note) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violationTicket.getViolationID());
            preparedStatement.setString(2, violationTicket.getMonthlyRentBillID());
            preparedStatement.setDouble(3, violationTicket.getPrice());
            preparedStatement.setDate(4, java.sql.Date.valueOf(violationTicket.getDate()));
            preparedStatement.setString(5, violationTicket.getNote());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int update(ViolationTicket violationTicket) {
        int rowsAffected = 0;
        String query = "UPDATE ViolationTicket SET monthlyRentBillID=?, price=?, Date=?, note=? WHERE violationID=?";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violationTicket.getMonthlyRentBillID());
            preparedStatement.setDouble(2, violationTicket.getPrice());
            preparedStatement.setDate(3, java.sql.Date.valueOf(violationTicket.getDate()));

            preparedStatement.setString(4, violationTicket.getNote());
            preparedStatement.setString(5, violationTicket.getViolationID());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int delete(String violationID) {
        int rowsAffected = 0;
        String query = "DELETE FROM ViolationTicket WHERE violationID=?";
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
    public ArrayList<ViolationTicket> selectAll() {
        ArrayList<ViolationTicket> violationTickets = new ArrayList<>();
        String query = "SELECT * FROM ViolationTicket";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String violationID = resultSet.getString("violationID");
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                double price = resultSet.getDouble("price");
                LocalDate date = resultSet.getDate("Date").toLocalDate();
                String note = resultSet.getString("note");
                if (violationID != null && monthlyRentBillID != null && date != null) {
                    ViolationTicket violationTicket = new ViolationTicket(violationID, monthlyRentBillID, price, date,
                            note);
                    violationTickets.add(violationTicket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violationTickets;
    }

    @Override
    public ViolationTicket selectById(String violationID) {
        ViolationTicket violationTicket = null;
        String query = "SELECT * FROM ViolationTicket WHERE violationID=?";
        try (Connection connection = JDBCUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, violationID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                violationTicket = createViolationTicketFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violationTicket;
    }

    private ViolationTicket createViolationTicketFromResultSet(ResultSet resultSet) throws SQLException {
        String violationID = resultSet.getString("violationID");
        String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
        double price = resultSet.getDouble("price");
        LocalDate date = resultSet.getDate("Date").toLocalDate();
        String note = resultSet.getString("note");

        return new ViolationTicket(violationID, monthlyRentBillID, price, date, note);
    }

}
