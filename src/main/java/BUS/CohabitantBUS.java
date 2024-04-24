package BUS;

import DAO.CohabitantDAO;
import DAO.TenantDAO;
import DTO.Cohabitant;
import DTO.Tenant;

import java.util.ArrayList;

public class CohabitantBUS {
    private ArrayList<Cohabitant> listCohabitant = new ArrayList<>();
    public CohabitantBUS() {
        this.listCohabitant = CohabitantDAO.getInstance().selectAll();
    }

    public ArrayList<Cohabitant> getAll(){
        return this.listCohabitant;
    }

    public boolean add(Cohabitant cohabitant){
        boolean check = CohabitantDAO.getInstance().insert(cohabitant)!=0;
        if (check){
            this.listCohabitant.add(cohabitant);
        }
        return check;
    }

    public boolean delete(Cohabitant cohabitant){
        boolean check = CohabitantDAO.getInstance().delete(cohabitant.getTenantID())!=0;
        if (check){
            this.listCohabitant.remove(cohabitant);
        }
        return check;
    }

    public boolean update(Cohabitant cohabitant){
        boolean check = CohabitantDAO.getInstance().update(cohabitant)!=0;
        if (check){
            this.listCohabitant.set(getIndexByApartmentID(cohabitant.getCohabitantID()), cohabitant);
        }
        return check;
    }

    public int getIndexByApartmentID(String cohabitantID){
        int i=0;
        int vitri=-1;
        while (i < this.listCohabitant.size() && vitri == -1){
            if (listCohabitant.get(i).getCohabitantID().equals(cohabitantID)){
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }
}
