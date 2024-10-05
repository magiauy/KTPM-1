package DAO;

import DTO.Apartment;
import config.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import DTO.Tenant;

public class TenantDAO implements DAOInterface<Tenant> {

    public static TenantDAO getInstance() {
        return new TenantDAO();
    }

    public String generateNewID(Connection conn) throws SQLException {
        String query = "SELECT ISNULL(MAX(CAST(SUBSTRING(tenantID, 2, LEN(tenantID) - 1) AS INT)), 0) FROM Tenant";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            int lastId = rs.getInt(1);
            return "T" + (lastId + 1);
        }
        return "T1"; // Nếu bảng rỗng thì bắt đầu từ T1
    }

    @Override
    public int insert(Tenant tenant) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();

            if (tenant.getTenantID() == null) {
                String newId = generateNewID(connection);
                tenant.setTenantID(newId); // Gán ID mới cho đối tượng Apartment
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Tenant (tenantID, lastName, firstName, phoneNumber, dob, gender, citizenIdentityCard) VALUES (?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, tenant.getTenantID());
            preparedStatement.setString(2, tenant.getLastName());
            preparedStatement.setString(3, tenant.getFirstName());
            preparedStatement.setString(4, tenant.getPhoneNumber());
            preparedStatement.setDate(5, java.sql.Date.valueOf(tenant.getDateOfBirthDay()));
            preparedStatement.setString(6, tenant.getGender());
            preparedStatement.setString(7, tenant.getCitizenIdentityCard());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int update(Tenant tenant) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Tenant SET lastName = ?, firstName = ?, phoneNumber = ?, dob = ?, gender = ?, citizenIdentityCard = ? WHERE tenantID = ?");
            preparedStatement.setString(1, tenant.getLastName());
            preparedStatement.setString(2, tenant.getFirstName());
            preparedStatement.setString(3, tenant.getPhoneNumber());
            preparedStatement.setDate(4, java.sql.Date.valueOf(tenant.getDateOfBirthDay()));
            preparedStatement.setString(5, tenant.getGender());
            preparedStatement.setString(6, tenant.getCitizenIdentityCard());
            preparedStatement.setString(7, tenant.getTenantID());

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public int delete(String ID) {
        int ketQua = 0;
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Tenant WHERE tenantID = ?");
            preparedStatement.setString(1, ID);

            ketQua = preparedStatement.executeUpdate();

            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ketQua;
    }

    @Override
    public ArrayList<Tenant> selectAll() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM Tenant";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                Tenant tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
                tenants.add(tenant);
            }

            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenants;
    }

    @Override
    public Tenant selectById(String ID) {
        Tenant tenant = null;
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM Tenant WHERE tenantID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
            }
            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenant;
    }

    public ArrayList<Tenant> tenantNotInLA() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();

            // Sửa câu lệnh SQL để lấy tất cả thông tin cần thiết từ bảng Tenant
            String sql = "SELECT T.tenantID, T.lastName, T.firstName, T.phoneNumber, T.dob, T.gender, T.citizenIdentityCard " +
                    "FROM Tenant T " +
                    "LEFT JOIN LeaseAgreement LA ON T.tenantID = LA.tenantID " +
                    "WHERE LA.tenantID IS NULL;";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Lấy dữ liệu từ ResultSet
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                // Tạo đối tượng Tenant từ các thông tin đã lấy
                Tenant tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
                tenants.add(tenant);
            }

            // Đóng các kết nối và tài nguyên
            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenants;
    }

    public ArrayList<Tenant> tenantsInLA() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();

            // Câu lệnh SQL để lấy tất cả tenant có trong hợp đồng
            String sql = "SELECT T.tenantID, T.lastName, T.firstName, T.phoneNumber, T.dob, T.gender, T.citizenIdentityCard " +
                    "FROM Tenant T " +
                    "INNER JOIN LeaseAgreement LA ON T.tenantID = LA.tenantID";  // Lấy các tenant có trong LeaseAgreement

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                // Lấy dữ liệu từ ResultSet
                String tenantID = resultSet.getString("tenantID");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
                String gender = resultSet.getString("gender");
                String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

                // Tạo đối tượng Tenant từ các thông tin đã lấy
                Tenant tenant = new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
                tenants.add(tenant);
            }

            // Đóng các kết nối và tài nguyên
            resultSet.close();
            statement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tenants;
    }



    private Tenant createTenantFromResultSet(ResultSet resultSet) throws SQLException {
        String tenantID = resultSet.getString("tenantID");
        String lastName = resultSet.getString("lastName");
        String firstName = resultSet.getString("firstName");
        String phoneNumber = resultSet.getString("phoneNumber");
        LocalDate dateOfBirthDay = resultSet.getDate("dob").toLocalDate();
        String gender = resultSet.getString("gender");
        String citizenIdentityCard = resultSet.getString("citizenIdentityCard");

        return new Tenant(tenantID, lastName, firstName, phoneNumber, dateOfBirthDay, gender, citizenIdentityCard);
    }

    public ArrayList<Tenant> search(String keyword, String buildingManagerID){
        ArrayList<Tenant> searchResults = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MonthlyRentBill A JOIN LeaseAgreement B ON A.apartmentID = B.tenantID WHERE (A.tenantID LIKE ? OR A.lastName LIKE ? OR A.firstName LIKE ?) AND B.buildingManagerID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            preparedStatement.setString(4, buildingManagerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Tenant tenant = createTenantFromResultSet(resultSet);
                searchResults.add(tenant);
            }

            resultSet.close();
            preparedStatement.close();
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
