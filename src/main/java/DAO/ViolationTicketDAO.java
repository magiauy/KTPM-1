package DAO;

import DTO.ViolationTicket;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViolationTicketDAO implements DAOInterface<ViolationTicket> {
    public static ViolationTicketDAO getInstance() {
        return new ViolationTicketDAO();
    }

    @Override
    public int insert(ViolationTicket t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO ViolationTicket (violationTicketID, violationID, monthlyRentBillID, price, date, note) VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, t.getViolationTicketID());
            preparedStatement.setString(2, t.getViolationID());
            preparedStatement.setString(3, t.getMonthlyRentBillID());
            preparedStatement.setDouble(4, t.getPrice());
            preparedStatement.setDate(5, Date.valueOf(t.getDate()));
            preparedStatement.setString(6, t.getNote());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(ViolationTicket t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE ViolationTicket SET violationID = ?, monthlyRentBillID = ?, price = ?, date = ?, note = ? WHERE violationTicketID = ?");

            preparedStatement.setString(1, t.getViolationID());
            preparedStatement.setString(2, t.getMonthlyRentBillID());
            preparedStatement.setDouble(3, t.getPrice());
            preparedStatement.setDate(4, Date.valueOf(t.getDate()));
            preparedStatement.setString(5, t.getNote());
            preparedStatement.setString(6, t.getViolationTicketID());

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
                    "DELETE FROM ViolationTicket WHERE violationTicketID = ?");

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
    public ArrayList<ViolationTicket> selectAll() {
        ArrayList<ViolationTicket> violationTickets = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ViolationTicket";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ViolationTicket violationTicket = createViolationTicketFromResultSet(resultSet);
                violationTickets.add(violationTicket);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violationTickets;
    }

    @Override
    public ViolationTicket selectById(String ID) {
        ViolationTicket violationTicket = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ViolationTicket WHERE violationTicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                violationTicket = createViolationTicketFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return violationTicket;
    }

    private ViolationTicket createViolationTicketFromResultSet(ResultSet resultSet) throws SQLException {
        String violationTicketID = resultSet.getString("violationTicketID");
        String violationID = resultSet.getString("violationID");
        String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
        Double price = resultSet.getDouble("price");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String note = resultSet.getString("note");

        return new ViolationTicket(violationTicketID, violationID, monthlyRentBillID, price, date, note);
    }
}
