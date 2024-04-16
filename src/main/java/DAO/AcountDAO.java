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
        ArrayList<Acount> result = new ArrayList<>();
        try {
            Connection con = (Connection) JDBCUtil.getConnection();
            String sql = "SELECT * FROM TaiKhoan";
            PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = (ResultSet) pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getNString("username");
                String password = rs.getNString("password");
                String loai = rs.getNString("loai");
                Acount ac = new Acount(id, username, password, loai);
                result.add(ac);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    public Acount selectById(String id) {
        return null;
    }
}
