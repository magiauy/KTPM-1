package BUS;

import DAO.BuildingDAO;
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
        boolean check = ViolationTicketDAO.getInstance().delete(violationTicket.getViolationTicketID()) != 0;
        if (check) {
            this.violationTickets.remove(violationTicket);
        }
        return check;
    }

   public boolean update(ViolationTicket violationTicket) {
        boolean updated = ViolationTicketDAO.getInstance().update(violationTicket) != 0;
        if (updated) {
            int index = getIndexByBuildingId(violationTicket.getViolationTicketID());
            if (index != -1) {
                this.violationTickets.set(index, violationTicket);
            }
        }
        return updated;
    }
    private int getIndexByBuildingId(String ViolationTicketID) {
        for (int i = 0; i < violationTickets.size(); i++) {
            if (violationTickets.get(i).getViolationTicketID().equals(ViolationTicketID)) {
                return i; // Trả về chỉ mục khi tìm thấy
            }
        }
        return -1; 
    }
    public ArrayList<String> getCurrentMonthMonthlyRentBillIDsByTenantID(String tenantID) {
        return ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID());
    }

    public ArrayList<ViolationTicket> search(Double text1,Double text2,String type){
        ArrayList<ViolationTicket> result = new ArrayList<>();

        if(text1==null || text2==null){
            return result;

        }
        switch(type){
            case "Lọc Theo Giá"->{
                for(ViolationTicket i : violationTickets ){
                   Double price = i.getPrice();
                   if(price>=text1 && price<=text2){
                    result.add(i);
                   }
                }
            }
        }
        return result;
    }



}
