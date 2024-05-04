package BUS;

import DAO.TenantDAO;
import DTO.MonthlyRentBill;
import DTO.Tenant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Objects;

public class TenantBUS {
    private TenantDAO tenantDAO;

    public TenantBUS() {
        tenantDAO = TenantDAO.getInstance();
    }
    private static TenantBUS instance;
    public static TenantBUS getInstance() {
        if (instance == null) {
            instance = new TenantBUS();
        }
        return instance;
    }

    // Phương thức thêm mới một Tenant
    public boolean addTenant(Tenant tenant) {
        int result = tenantDAO.insert(tenant);
        return result > 0;
    }


    // Phương thức cập nhật thông tin một Tenant
    public boolean updateTenant(Tenant tenant) {
        int result = tenantDAO.update(tenant);
        return result > 0;
    }

    // Phương thức xóa một Tenant dựa trên ID
    public boolean deleteTenant(String tenantID) {
        int result = tenantDAO.delete(tenantID);
        return result > 0;
    }

    // Phương thức lấy danh sách tất cả các Tenant
    public ArrayList<Tenant> getAllTenants() {
        return tenantDAO.selectAll();
    }

    // Phương thức lấy thông tin một Tenant dựa trên ID
    public Tenant getTenantById(String tenantID) {
        return tenantDAO.selectById(tenantID);
    }
    public void setInfor(Label fullName, Label phone, Label dob, Label gender, Label cccd, String ID){
        Tenant tenant = getTenantById(ID);
        if(tenant != null){
            fullName.setText("Họ & Tên: "+tenant.getLastName() + " " + tenant.getFirstName());
            phone.setText("SDT: "+tenant.getPhoneNumber());
            dob.setText("Dob: "+ tenant.getDateOfBirthDay());
            gender.setText("Giới tính: "+tenant.getGender());
            cccd.setText("CCCD: "+tenant.getCitizenIdentityCard());
        }
    }
}
