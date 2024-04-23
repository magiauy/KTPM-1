package BUS;

import DAO.TenantDAO;
import DTO.Tenant;
import java.util.ArrayList;

public class TenantBUS {
    private TenantDAO tenantDAO;

    public TenantBUS() {
        tenantDAO = TenantDAO.getInstance();
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
}
