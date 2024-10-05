package DAO;

import DTO.MonthlyRentBill;
import DTO.ServiceTicket;
import DTO.ViolationTicket;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ViolationTicketDAO implements DAOInterface<ViolationTicket> {
    public static ViolationTicketDAO getInstance() {
        return new ViolationTicketDAO();
    }

    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT IFNULL(MAX(CAST(SUBSTRING(violationTicketID, 3, LENGTH(violationTicketID) - 2) AS INT)), 0) FROM ViolationTicket";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int lastId = rs.getInt(1);
            return "VT" + (lastId + 1);
        }
        return "VT1"; // Nếu bảng rỗng thì bắt đầu từ APT1
    }

    @Override
    public int insert(ViolationTicket t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            if (t.getViolationTicketID() == null) {
                String newId = generateNewID(connection);
                t.setViolationTicketID(newId);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO ViolationTicket (violationTicketID, violationID, monthlyRentBillID, quantity , price, date, note) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, t.getViolationTicketID());
            preparedStatement.setString(2, t.getViolationID());
            preparedStatement.setString(3, t.getMonthlyRentBillID());
            preparedStatement.setInt(4, t.getQuantity());
            preparedStatement.setDouble(5, t.getPrice());
            preparedStatement.setDate(6, Date.valueOf(t.getDate()));
            preparedStatement.setString(7, t.getNote());

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
                    "UPDATE ViolationTicket SET violationID = ?, monthlyRentBillID = ?, quantity = ? , price = ?, date = ?, note = ? WHERE violationTicketID = ?");

            preparedStatement.setString(1, t.getViolationID());
            preparedStatement.setString(2, t.getMonthlyRentBillID());
            preparedStatement.setDouble(3, t.getQuantity());
            preparedStatement.setDouble(4, t.getPrice());
            preparedStatement.setDate(5, Date.valueOf(t.getDate()));
            preparedStatement.setString(6, t.getNote());
            preparedStatement.setString(7, t.getViolationTicketID());
            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String violationTicketID) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM ViolationTicket WHERE violationTicketID = ?");

            preparedStatement.setString(1, violationTicketID);

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
            String sql = "SELECT ViolationTicket.violationID,ViolationTicket.violationTicketID,ViolationTicket.Date,ViolationTicket.monthlyRentBillID,ViolationTicket.quantity,ViolationTicket.note,ViolationTicket.price\r\n" + //
                                "from Violation,ViolationTicket\r\n" + //
                                "where Violation.violationID=ViolationTicket.violationID";
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

    public List<ViolationTicket> selectByMonthlyRentBillID(String monthlyRentBillID) {
        ArrayList<ViolationTicket> violationTickets = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ViolationTicket WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, monthlyRentBillID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String violationTicketID = resultSet.getString("violationTicketID");
                String violationID = resultSet.getString("violationID");
                int quantity = resultSet.getInt("quantity");
                Double price = resultSet.getDouble("price");
                LocalDate date = resultSet.getDate("Date").toLocalDate();
                String note = resultSet.getString("note");

                ViolationTicket violationTicket = new ViolationTicket(violationTicketID, violationID, monthlyRentBillID, quantity, price, date, note);
                violationTickets.add(violationTicket);
            }

            resultSet.close();
            preparedStatement.close();
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
        int quatity = resultSet.getInt("quantity");
        Double price = resultSet.getDouble("price");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String note = resultSet.getString("note");

        return new ViolationTicket(violationTicketID, violationID, monthlyRentBillID, quatity, price, date, note);
    }

    public ArrayList<ViolationTicket> getidViolationTicket(String id) {
        ArrayList<ViolationTicket> rentBills = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ViolationTicket  WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ViolationTicket rentBill = createViolationTicketFromResultSet(resultSet);
                rentBills.add(rentBill);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentBills;
    }
}
