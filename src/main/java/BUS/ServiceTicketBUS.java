package BUS;

import DAO.ServiceTicketDAO;
import DTO.MonthlyRentBill;
import DTO.Service;
import DTO.ServiceTicket;
import DTO.ServiceUsuage;
import com.example.managingbuildingjava.CustomerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    public int countRows(){
        return ServiceTicketDAO.getInstance().countRows() + 1;
    }
    public ArrayList<String> getCurrentMonthMonthlyRentBillIDsByTenantID(String tenantID) {
        return ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID());
    }


    public ArrayList<ServiceTicket> search(LocalDate day1, LocalDate day2, String type) {
        ArrayList<ServiceTicket> resul = new ArrayList<>();
        if (day1 == null || day2 == null) {
            return resul;
        }

        switch (type) {
            case "Tìm Theo Ngày" -> {
                for (ServiceTicket i : serviceTickets) {
                    LocalDate res = i.getDate();
                    if (!res.isBefore(day1) && !res.isAfter(day2)) {
                        resul.add(i);
                    }
                }
            }

        }

        return resul;

    }

    public ArrayList<ServiceTicket> searchID(String Text, String type) {
        ArrayList<ServiceTicket> resul = new ArrayList<>();
        if (Text == null) {
            return resul;
        }
        Text = Text.toLowerCase();
        switch (type) {
            case "Tìm Theo Mã" -> {
                for (ServiceTicket i : serviceTickets) {
                    if (i.getServiceTicketID().toLowerCase().contains(Text) || i.getServiceID().toLowerCase()
                            .contains(Text) || i.getMonthlyRentBillID().toLowerCase().contains(Text)) {
                        resul.add(i);
                    }
                }
            }

        }

        return resul;

    }

}
