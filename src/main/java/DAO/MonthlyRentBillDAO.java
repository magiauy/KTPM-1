package DAO;

import DTO.MonthlyRentBill;
import DTO.Tenant;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class MonthlyRentBillDAO implements DAOInterface<MonthlyRentBill> {

    public MonthlyRentBillDAO() {
    }

    public static MonthlyRentBillDAO getInstance() {
        return new MonthlyRentBillDAO();
    }

    @Override
    public int insert(MonthlyRentBill monthlyRentBill) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO MonthlyRentBill (monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, monthlyRentBill.getMonthlyRentBillID());
            preparedStatement.setString(2, monthlyRentBill.getApartmentID());
            preparedStatement.setString(3, monthlyRentBill.getTenantID());
            preparedStatement.setDate(4, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(5, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(6, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(7, monthlyRentBill.getStatus());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(MonthlyRentBill monthlyRentBill) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE MonthlyRentBill SET apartmentID = ?, tenantID = ?, date = ?, repaymentPeriod = ?, totalPayment = ?, status = ? WHERE monthlyRentBillID = ?");

            preparedStatement.setString(1, monthlyRentBill.getApartmentID());
            preparedStatement.setString(2, monthlyRentBill.getTenantID());
            preparedStatement.setDate(3, Date.valueOf(monthlyRentBill.getDate()));
            preparedStatement.setInt(4, monthlyRentBill.getRepaymentPeriod());
            preparedStatement.setDouble(5, monthlyRentBill.getTotalPayment());
            preparedStatement.setString(6, monthlyRentBill.getStatus());
            preparedStatement.setString(7, monthlyRentBill.getMonthlyRentBillID());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String ID) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM MonthlyRentBill WHERE monthlyRentBillID = ?");
            preparedStatement.setString(1, ID);

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<MonthlyRentBill> selectAll() {
        ArrayList<MonthlyRentBill> monthlyRentBills = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM MonthlyRentBill";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                String apartmentID = resultSet.getString("apartmentID");
                String tenantID = resultSet.getString("tenantID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int repaymentPeriod = resultSet.getInt("repaymentPeriod");
                double totalPayment = resultSet.getDouble("totalPayment");
                String status = resultSet.getString("status");

                MonthlyRentBill monthlyRentBill = new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status);
                monthlyRentBills.add(monthlyRentBill);
            }
            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBills;
    }

    @Override
    public MonthlyRentBill selectById(String ID) {
        MonthlyRentBill monthlyRentBill = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill WHERE monthlyRentBillID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
                String apartmentID = resultSet.getString("apartmentID");
                String tenantID = resultSet.getString("tenantID");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                int repaymentPeriod = resultSet.getInt("repaymentPeriod");
                double totalPayment = resultSet.getDouble("totalPayment");
                String status = resultSet.getString("status");

                monthlyRentBill = new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthlyRentBill;
    }

    private MonthlyRentBill createMonthlyRentBillFromResultSet(ResultSet resultSet) throws SQLException {
        String monthlyRentBillID = resultSet.getString("monthlyRentBillID");
        String apartmentID = resultSet.getString("apartmentID");
        String tenantID = resultSet.getString("tenantID");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        int repaymentPeriod = resultSet.getInt("repaymentPeriod");
        double totalPayment = resultSet.getDouble("totalPayment");
        String status = resultSet.getString("status");

        return new MonthlyRentBill(monthlyRentBillID, apartmentID, tenantID, date, repaymentPeriod, totalPayment, status);
    }

    public ArrayList<MonthlyRentBill> search(String keyword, String buildingManagerID){
        ArrayList<MonthlyRentBill> searchResults = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill A JOIN LeaseAgreement B ON A.apartmentID = B.apartmentID WHERE (A.monthlyRentBillID LIKE ? OR A.apartmentID LIKE ? OR A.tenantID LIKE ? OR A.status LIKE ?) AND B.buildingManagerID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, "%" + keyword + "%");
            preparedStatement.setString(5, buildingManagerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MonthlyRentBill monthlyRentBill = createMonthlyRentBillFromResultSet(resultSet);
                searchResults.add(monthlyRentBill);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }

    public ArrayList<MonthlyRentBill> fill(String buildingManagerID, LocalDate dayStart, LocalDate dayEnd) {
        ArrayList<MonthlyRentBill> searchResults = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill A " +
                    "JOIN LeaseAgreement B ON A.apartmentID = B.apartmentID " +
                    "WHERE A.date BETWEEN ? AND ? " +
                    "AND B.buildingManagerID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(dayStart));
            preparedStatement.setDate(2, Date.valueOf(dayEnd));
            preparedStatement.setString(3, buildingManagerID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                MonthlyRentBill monthlyRentBill = createMonthlyRentBillFromResultSet(resultSet);
                searchResults.add(monthlyRentBill);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
    
    public ArrayList<MonthlyRentBill> getMonthlyRentBillsByApartmentID(String apartmentID) {
        ArrayList<MonthlyRentBill> rentBills = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill WHERE apartmentID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, apartmentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MonthlyRentBill rentBill = createMonthlyRentBillFromResultSet(resultSet);
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
