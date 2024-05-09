package BUS;

import DAO.ViolationTicketDAO;
import DTO.ViolationTicket;

import java.util.ArrayList;
import java.util.Objects;

public class ViolationTicketBUS {
    private ArrayList<ViolationTicket> violationTickets = new ArrayList<>();

    private static ViolationTicketBUS instance;

    public static ViolationTicketBUS getInstance() {
        if (instance == null) {
            instance = new ViolationTicketBUS();
        }
        return instance;
    }

    public ViolationTicketBUS() {
        this.violationTickets = ViolationTicketDAO.getInstance().selectAll();
    }

    public ArrayList<ViolationTicket> getAll() {
        ViolationTicketDAO violationTicketDAO = ViolationTicketDAO.getInstance();
        return violationTicketDAO.selectAll();
    }

    public boolean add(ViolationTicket violationTicket) {
        boolean check = ViolationTicketDAO.getInstance().insert(violationTicket) != 0;
        if (check) {
            this.violationTickets.add(violationTicket);
        }
        return check;
    }

    public boolean delete(ViolationTicket violationTicket) {
        boolean check = ViolationTicketDAO.getInstance().delete(violationTicket.getViolationID()) != 0;
        if (check) {
            this.violationTickets.remove(violationTicket);
        }
        return check;
    }

    public boolean update(ViolationTicket violationTicket) {
        boolean check = ViolationTicketDAO.getInstance().update(violationTicket) != 0;
        if (check) {
            int index = getIndexByViolationID(violationTicket.getViolationID());
            if (index != -1) {
                this.violationTickets.set(index, violationTicket);
            }
        }
        return check;
    }

    public int getIndexByViolationID(String violationID) {
        for (int i = 0; i < this.violationTickets.size(); i++) {
            if (Objects.equals(this.violationTickets.get(i).getViolationID(), violationID)) {
                return i;
            }
        }
        return -1; // Not found
    }
}
