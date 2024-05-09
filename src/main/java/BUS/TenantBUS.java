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
    private ArrayList<Tenant> tenants = new ArrayList<>();
    private static TenantBUS instance;

    public static TenantBUS getInstance() {
        if (instance == null) {
            instance = new TenantBUS();
        }
        return instance;
    }

    public TenantBUS() {
        this.tenants = TenantDAO.getInstance().selectAll();
    }

    public ArrayList<Tenant> getAll() {
        TenantDAO tenantDAO = TenantDAO.getInstance();
        return tenantDAO.selectAll();
    }

    public boolean add(Tenant tenant) {
        boolean check = TenantDAO.getInstance().insert(tenant) != 0;
        if (check) {
            this.tenants.add(tenant);
        }
        return check;
    }

    public boolean delete(Tenant tenant) {
        boolean check = TenantDAO.getInstance().delete(tenant.getTenantID()) != 0;
        if (check) {
            this.tenants.remove(tenant);
        }
        return check;
    }

    public boolean update(Tenant tenant) {
        boolean check = TenantDAO.getInstance().update(tenant) != 0;
        if (check) {
            int index = getIndexByTenantID(tenant.getTenantID());
            if (index != -1) {
                this.tenants.set(index, tenant);
            }
        }
        return check;
    }

    private int getIndexByTenantID(String tenantID) {
        for (int i = 0; i < tenants.size(); i++) {
            if (tenants.get(i).getTenantID().equals(tenantID)) {
                return i;
            }
        }
        return -1;
    }
    // Phương thức lấy thông tin một Tenant dựa trên ID
    public Tenant getTenantById(String tenantID) {
        return TenantDAO.getInstance().selectById(tenantID);
    }
    public void setInfor(Label fullName, Label phone, Label dob, Label gender, Label cccd, String ID){
        Tenant tenant = getTenantById(ID);
        if(tenant != null){
            fullName.setText("Họ & Tên: "+ tenant.getLastName() + " " + tenant.getFirstName());
            phone.setText("SDT: "+tenant.getPhoneNumber());
            dob.setText("Dob: "+ tenant.getDateOfBirthDay());
            gender.setText("Giới tính: "+tenant.getGender());
            cccd.setText("CCCD: "+tenant.getCitizenIdentityCard());
        }
    }
}
