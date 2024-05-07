package BUS;

import DAO.ServiceTicketDAO;
import DTO.Service;
import DTO.ServiceTicket;
import com.example.managingbuildingjava.CustomerController;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ServiceTicketBUS {
    private ArrayList<ServiceTicket> serviceTickets = new ArrayList<>();

    private static ServiceTicketBUS instance;
    public static ServiceTicketBUS getInstance() {
        if (instance == null) {
            instance = new ServiceTicketBUS();
        }
        return instance;
    }

    public ServiceTicketBUS() {
        this.serviceTickets = ServiceTicketDAO.getInstance().selectAll();
    }

    public ArrayList<ServiceTicket> getAll() {
        ServiceTicketDAO serviceTicketDAO = ServiceTicketDAO.getInstance();
        return serviceTicketDAO.selectAll();
    }

    public boolean add(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().insert(serviceTicket) != 0;
        if (check) {
            this.serviceTickets.add(serviceTicket);
        }
        return check;
    }


    public boolean delete(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().delete(serviceTicket.getServiceTicketID()) != 0;
        if (check) {
            this.serviceTickets.remove(serviceTicket);
        }
        return check;
    }

    public boolean update(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().update(serviceTicket) != 0;
        if (check) {
            int index = getIndexByServiceTicketID(serviceTicket.getServiceTicketID());
            if (index != -1) {
                this.serviceTickets.set(index, serviceTicket);
            }
        }
        return check;
    }

    public int getIndexByServiceTicketID(String serviceTicketID) {
        for (int i = 0; i < this.serviceTickets.size(); i++) {
            if (Objects.equals(this.serviceTickets.get(i).getServiceTicketID(), serviceTicketID)) {
                return i;
            }
        }
        return -1; // Not found
    }
    public void regisFixedServ(String servID, String note){
        String servTID = "SERVT" + (ServiceTicketDAO.getInstance().countRows() + 1);
        String mrBillID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();
        Double price = 0.0;
        for (Service service : ServiceBUS.getInstance().getAll()){
            if (service.getServiceID().equals(servID)){
                price = service.getPricePerUnit();
            }
        }
        LocalDate currentDate = LocalDate.now();

        ServiceTicket serviceTicket = new ServiceTicket(servTID,mrBillID,servID, 1.0,price,currentDate,"note");
//        if(ServiceTicketBUS.getInstance().add(serviceTicket)){
//            CustomerController.getInstance().showAlert("Thành công", "Đã đăng ký thành công", Alert.AlertType.CONFIRMATION);?
//        }
//        else{
//            CustomerController.getInstance().showAlert("Lỗi", "Vui lòng thử lại", Alert.AlertType.ERROR);
//        }
        System.out.println(serviceTicket.getQuantity() + 1);
        System.out.println(servTID + " " + mrBillID + " " + servID + " " + 1.0 + " " + price + " " + currentDate + " " + note);
//        System.out.println(ServiceTicketBUS.getInstance().add(serviceTicket));
    }

}
