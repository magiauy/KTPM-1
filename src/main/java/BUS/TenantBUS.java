package BUS;

import DAO.ApartmentDAO;
import DAO.TenantDAO;
import DTO.Apartment;
import DTO.Tenant;
import java.util.ArrayList;

public class TenantBUS {
    private ArrayList<Tenant> listTenant = new ArrayList<>();
    public TenantBUS() {
        this.listTenant = TenantDAO.getInstance().selectAll();
    }

    public ArrayList<Tenant> getAll(){
        return this.listTenant;
    }

    public boolean add(Tenant tenant){
        boolean check = TenantDAO.getInstance().insert(tenant)!=0;
        if (check){
            this.listTenant.add(tenant);
        }
        return check;
    }

    public boolean delete(Tenant tenant){
        boolean check = TenantDAO.getInstance().delete(tenant.getTenantID())!=0;
        if (check){
            this.listTenant.remove(tenant);
        }
        return check;
    }

    public boolean update(Tenant tenant){
        boolean check = TenantDAO.getInstance().update(tenant)!=0;
        if (check){
            this.listTenant.set(getIndexByApartmentID(tenant.getTenantID()), tenant);
        }
        return check;
    }

    public int getIndexByApartmentID(String tenentID){
        int i=0;
        int vitri=-1;
        while (i < this.listTenant.size() && vitri == -1){
            if (listTenant.get(i).getTenantID().equals(tenentID)){
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
}
