package BUS;

import DAO.ViolationDAO;
import DTO.Violation;

import java.util.ArrayList;
import java.util.Objects;

public class ViolationBUS {
    private ArrayList<Violation> violations = new ArrayList<>();

    private static ViolationBUS instance;

    public static ViolationBUS getInstance() {
        if (instance == null) {
            instance = new ViolationBUS();
        }
        return instance;
    }

    public ViolationBUS() {
        this.violations = ViolationDAO.getInstance().selectAll();
    }

    public ArrayList<Violation> getAll() {
        ViolationDAO violationDAO = ViolationDAO.getInstance();
        return violationDAO.selectAll();
    }

    public boolean add(Violation violation) {
        boolean check = ViolationDAO.getInstance().insert(violation) != 0;
        if (check) {
            this.violations.add(violation);
        }
        return check;
    }

    public boolean delete(Violation violation) {
        boolean check = ViolationDAO.getInstance().delete(violation.getViolationID()) != 0;
        if (check) {
            this.violations.remove(violation);
        }
        return check;
    }

    public boolean update(Violation violation) {
        boolean check = ViolationDAO.getInstance().update(violation) != 0;
        if (check) {
            int index = getIndexByViolationID(violation.getViolationID());
            if (index != -1) {
                this.violations.set(index, violation);
            }
        }
        return check;
    }

    public int getIndexByViolationID(String violationID) {
        for (int i = 0; i < this.violations.size(); i++) {
            if (Objects.equals(this.violations.get(i).getViolationID(), violationID)) {
                return i;
            }
        }
        return -1; // Not found
    }
}
