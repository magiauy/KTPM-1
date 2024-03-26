package DAO;

import DAO.DAOInterface;
import DTO.ManagementContract;
import config.JDBCUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ManagementContractDAO implements DAOInterface<ManagementContract> {
    public static ManagementContractDAO getInstance() {
        return new ManagementContractDAO();
    }

    @Override
    public int insert(ManagementContract t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "INSERT INTO ManagementContract (managementContract, buildingManagerId_ManagementContract, signingDate, leaseStartDate, leaseEndDate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, t.getManagementContract());
            preparedStatement.setString(2, t.getBuildingManagerId_ManagementContract());
            preparedStatement.setDate(3, Date.valueOf(t.getSigningDate()));
            preparedStatement.setDate(4, Date.valueOf(t.getLeaseStartDate()));
            preparedStatement.setDate(5, Date.valueOf(t.getLeaseEndDate()));

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(ManagementContract t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "UPDATE ManagementContract SET buildingManagerId_ManagementContract = ?, signingDate = ?, leaseStartDate = ?, leaseEndDate = ? WHERE managementContract = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, t.getBuildingManagerId_ManagementContract());
            preparedStatement.setDate(2, Date.valueOf(t.getSigningDate()));
            preparedStatement.setDate(3, Date.valueOf(t.getLeaseStartDate()));
            preparedStatement.setDate(4, Date.valueOf(t.getLeaseEndDate()));
            preparedStatement.setString(5, t.getManagementContract());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(String t) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "DELETE FROM ManagementContract WHERE managementContract = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, t);

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<ManagementContract> selectAll() {
        ArrayList<ManagementContract> contracts = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM ManagementContract";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ManagementContract contract = createManagementContractFromResultSet(resultSet);
                contracts.add(contract);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    @Override
    public ManagementContract selectById(String ID) {
        ManagementContract contract = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ManagementContract WHERE managementContract = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                contract = createManagementContractFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contract;
    }

    private ManagementContract createManagementContractFromResultSet(ResultSet resultSet) throws SQLException {
        String managementContract = resultSet.getString("managementContract");
        String buildingManagerId_ManagementContract = resultSet.getString("buildingManagerId_ManagementContract");
        LocalDate signingDate = resultSet.getDate("signingDate").toLocalDate();
        LocalDate leaseStartDate = resultSet.getDate("leaseStartDate").toLocalDate();
        LocalDate leaseEndDate = resultSet.getDate("leaseEndDate").toLocalDate();

        return new ManagementContract(managementContract, buildingManagerId_ManagementContract, signingDate, leaseStartDate, leaseEndDate);
    }
}
