package DAO;

import java.util.ArrayList;
import java.sql.Connection;
import DTO.Acount;
import config.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AcountDAO implements DAOInterface<Acount> {

    private static AcountDAO instance;

    private AcountDAO() {
    }

    public static AcountDAO getInstance() {
        if (instance == null) {
            instance = new AcountDAO();
        }
        return instance;
    }

    @Override
    public int insert(Acount t) {

        return 0;
    }

    @Override
    public int update(Acount t) {

        return 0;
    }

    @Override
    public int delete(String id) {

        return 0;
    }

    @Override
    public ArrayList<Acount> selectAll() {
        ArrayList<Acount> accounts = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM TaiKhoan");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                Acount account = new Acount(id, username, password, role);
                accounts.add(account);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }
    
    @Override
    public Acount selectById(String id) {
        return null;
    }
}
