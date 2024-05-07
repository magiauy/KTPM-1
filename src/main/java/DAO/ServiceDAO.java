package DAO;

import DTO.Service;
import config.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;

public class ServiceDAO implements DAOInterface<Service>{
    public static ServiceDAO getInstance(){
        return new ServiceDAO();
    }

    @Override
    public int insert(Service t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Service (serviceID, name, pricePerUnit, unit, type) VALUES (?, ?, ?, ?, ?)");

            // Set parameter values in the SQL statement
            preparedStatement.setString(1, t.getServiceID());
            preparedStatement.setString(2, t.getName());
            preparedStatement.setDouble(3, t.getPricePerUnit());
            preparedStatement.setString(4, t.getUnit());
            preparedStatement.setString(5, t.getType());

            result = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Service t) {
        int result = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Service SET name = ?, pricePerUnit = ?, unit = ?, type = ? WHERE serviceID = ?");

            // Set parameter values in the SQL statement
            preparedStatement.setString(1, t.getName());
            preparedStatement.setDouble(2, t.getPricePerUnit());
            preparedStatement.setString(3, t.getUnit());
            preparedStatement.setString(4, t.getType());
            preparedStatement.setString(5, t.getServiceID());

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
            String sql = "DELETE FROM Service WHERE serviceID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

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
    public ArrayList<Service> selectAll() {
        ArrayList<Service> services = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Service";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Service service = createServiceFromResultSet(resultSet);
                services.add(service);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public Service selectById(String t) {
        Service service = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Service WHERE serviceID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, t);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                service = createServiceFromResultSet(resultSet);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return service;
    }

    private Service createServiceFromResultSet(ResultSet resultSet) throws SQLException {
        String serviceID = resultSet.getString("serviceID");
        String name = resultSet.getString("name");
        double pricePerUnit = resultSet.getDouble("pricePerUnit");
        String unit = resultSet.getString("unit");
        String type = resultSet.getString("type");

        return new Service(serviceID, name, pricePerUnit, unit, type);
    }
}
