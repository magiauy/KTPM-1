package BUS;

import DAO.CohabitantDAO;
import DTO.Cohabitant;
import DTO.MonthlyRentBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class CohabitantBUS {
    private ArrayList<Cohabitant> listCohabitant = new ArrayList<>();

    public CohabitantBUS() {
        this.listCohabitant = CohabitantDAO.getInstance().selectAll();
    }
    private static CohabitantBUS instance;
    public static CohabitantBUS getInstance() {
        if (instance == null) {
            instance = new CohabitantBUS();
        }
        return instance;
    }
    public ArrayList<Cohabitant> getAll() {
        return this.listCohabitant;
    }

    public boolean add(Cohabitant cohabitant) {
        boolean check = CohabitantDAO.getInstance().insert(cohabitant) != 0;
        if (check) {
            this.listCohabitant.add(cohabitant);
        }
        return check;
    }

    public boolean delete(Cohabitant cohabitant) {
        boolean check = CohabitantDAO.getInstance().delete(cohabitant.getCohabitantID()) != 0;
        if (check) {
            this.listCohabitant.remove(cohabitant);
        }
        return check;
    }

    public boolean update(Cohabitant cohabitant) {
        boolean check = CohabitantDAO.getInstance().update(cohabitant) != 0;
        if (check) {
            this.listCohabitant.set(getIndexByCohabitantID(cohabitant.getCohabitantID()), cohabitant);
        }
        return check;
    }

    public int getIndexByCohabitantID(String cohabitantID) {
        int i = 0;
        int index = -1;
        while (i < this.listCohabitant.size() && index == -1) {
            if (listCohabitant.get(i).getCohabitantID().equals(cohabitantID)) {
                index = i;
            } else {
                i++;
            }
        }
        return index;
    }

    private ArrayList<Cohabitant> cohabitants = new ArrayList<>();


    public ArrayList<Cohabitant> getCohabitantsWithTenantId(String tenantID) {
        ArrayList<Cohabitant> cohabitantsWithTenantID = new ArrayList<>();
        for (Cohabitant cohabitant : cohabitants) {
            if (Objects.equals(cohabitant.getTenantID(), tenantID)) {
                cohabitantsWithTenantID.add(cohabitant);
            }
        }
        return  cohabitantsWithTenantID;
    }
}
