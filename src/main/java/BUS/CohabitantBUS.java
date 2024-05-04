package BUS;

import DAO.CohabitantDAO;
import DTO.Cohabitant;

import java.time.LocalDate;
import java.util.ArrayList;

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

}
