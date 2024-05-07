package DAO;

import DTO.ServiceTicket;
import config.JDBCUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceTicketDAO implements DAOInterface<ServiceTicket> {
    public static ServiceTicketDAO getInstance() {
        return new ServiceTicketDAO();
    }

    @Override
    public int insert(ServiceTicket t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO ServiceTicket (serviceTicketID, monthlyRentBillID, serviceID, quantity, totalAmount, date, note) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, t.getServiceTicketID());
            preparedStatement.setString(2, t.getMonthlyRentBillID());
            preparedStatement.setString(3, t.getServiceID());
            preparedStatement.setDouble(4, t.getQuantity());
            preparedStatement.setDouble(5, t.getTotalAmount());
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
    public int update(ServiceTicket t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE ServiceTicket SET monthlyRentBillID = ?, serviceID = ?, quantity = ?, totalAmount = ?, date = ?, note = ? " +
                            "WHERE serviceTicketID = ?");

            preparedStatement.setString(1, t.getMonthlyRentBillID());
            preparedStatement.setString(2, t.getServiceID());
            preparedStatement.setDouble(3, t.getQuantity());
            preparedStatement.setDouble(4, t.getTotalAmount());
            preparedStatement.setDate(5, Date.valueOf(t.getDate()));
            preparedStatement.setString(6, t.getNote());
            preparedStatement.setString(7, t.getServiceTicketID());

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
                    "DELETE FROM ServiceTicket WHERE serviceTicketID = ?");

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
    public ArrayList<ServiceTicket> selectAll() {
        ArrayList<ServiceTicket> serviceTickets = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ServiceTicket";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ServiceTicket serviceTicket = createServiceTicketFromResultSet(resultSet);
                serviceTickets.add(serviceTicket);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceTickets;
    }

    @Override
    public ServiceTicket selectById(String ID) {
        ServiceTicket serviceTicket = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ServiceTicket WHERE serviceTicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                serviceTicket = createServiceTicketFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviceTicket;
    }
    public int countRows() {
        int rowCount = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS rowCountValue FROM ServiceTicket");

            if (resultSet.next()) {
                rowCount = resultSet.getInt("rowCountValue");
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public ArrayList<String> getCurrentMonthMonthlyRentBillIDsByTenantID(String tenantID) {
        ArrayList<String> monthlyRentBillIDs = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT monthlyRentBillID " +
                    "FROM MonthlyRentBill " +
                    "WHERE tenantID = ? AND " +
                    "MONTH(date) = MONTH(GETDATE()) AND " +
                    "YEAR(date) = YEAR(GETDATE())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenantID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                monthlyRentBillIDs.add(monthlyRentBillID);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBillIDs;
    }

    public ArrayList<String> getMonthlyRentBillIDsByTenantID(String tenantID) {
        ArrayList<String> monthlyRentBillIDs = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT monthlyRentBillID " +
                    "FROM MonthlyRentBill " +
                    "WHERE tenantID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, tenantID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                monthlyRentBillIDs.add(monthlyRentBillID);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBillIDs;
    }

    private ServiceTicket createServiceTicketFromResultSet(ResultSet resultSet) throws SQLException {
        String serviceTicketID = resultSet.getString("serviceTicketID");
        String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
        String serviceID = resultSet.getString("serviceID");
        Double quantity = resultSet.getDouble("quantity");
        Double totalAmount = resultSet.getDouble("totalAmount");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        String note = resultSet.getString("note");

        return new ServiceTicket(serviceTicketID, monthlyRentBillID, serviceID, quantity, totalAmount, date, note);
    }
}
