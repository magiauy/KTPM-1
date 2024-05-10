package BUS;

import DAO.ServiceTicketDAO;
import DAO.ViolationTicketDAO;
import DTO.*;
import com.example.managingbuildingjava.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.time.LocalDate;
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

    public void setTable(TableView<ViolatioUsage> table__P3__2){
        String mrbID = "";
        try{
            mrbID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<String> ticketId = new ArrayList<>();
        ArrayList<String> vioID =  new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<LocalDate> date = new ArrayList<>();
        ArrayList<String> note = new ArrayList<>();

        for (ViolationTicket violationTicket : ViolationTicketBUS.getInstance().getAll()) {
            if (Objects.equals(violationTicket.getMonthlyRentBillID(), mrbID)) {
                vioID.add(violationTicket.getViolationID());
                ticketId.add(violationTicket.getViolationTicketID());

                date.add(violationTicket.getDate());
                note.add(violationTicket.getNote());
                price.add(violationTicket.getPrice());
            }
        }
        ArrayList<Violation> violations = ViolationBUS.getInstance().getAll();
        for (String sID : vioID) {
            for (Violation violation : violations){
                if(violation.getViolationID().equals(sID)){
                    name.add(violation.getName());
                }
            }
        }

        ObservableList<ViolatioUsage> data = FXCollections.observableArrayList();
        for (int i = 0; i < name.size(); i++) {
            ViolatioUsage violatioUsage = new ViolatioUsage(ticketId.get(i), name.get(i), price.get(i), date.get(i), note.get(i));
            data.add(violatioUsage);
        }
        table__P3__2.setItems(data);
    }
}
