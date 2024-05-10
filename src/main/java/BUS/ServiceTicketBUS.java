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
    public void regisServ(String servID, String note, LocalDate currentDate){
        String servTID = "SERVT" + (ServiceTicketDAO.getInstance().countRows() + 1);

        String mrBillID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();

        Double price = 0.0;
        for (Service service : ServiceBUS.getInstance().getAll()){
            if (service.getServiceID().equals(servID)){
                price = service.getPricePerUnit();
            }
        }

        ServiceTicket serviceTicket = new ServiceTicket();
        serviceTicket.setServiceTicketID(servTID);
        serviceTicket.setServiceID(servID);
        serviceTicket.setNote(note);
        serviceTicket.setDate(currentDate);
        serviceTicket.setMonthlyRentBillID(mrBillID);
        serviceTicket.setQuantity(1.0);
        serviceTicket.setTotalAmount(price);

        if(ServiceTicketBUS.getInstance().add(serviceTicket)){
            CustomerController.getInstance().showAlert("Thành công", "Đã đăng ký thành công", Alert.AlertType.CONFIRMATION);
        }
        else{
            CustomerController.getInstance().showAlert("Lỗi", "Vui lòng thử lại", Alert.AlertType.ERROR);
        }
    }

//    public void setTableRegisServ(TableView<String[]> registeredSerTable ){
//        ArrayList<String> servName = new ArrayList<>();
//        ArrayList<String> note = new ArrayList<>();
//        ArrayList<Double> price = new ArrayList<>();
//        ArrayList<LocalDate> date = new ArrayList<>();
//        ArrayList<String> serviceID = new ArrayList<>();
//
//        LocalDate currentDate = LocalDate.now();
//        Month currentMonth = currentDate.getMonth();
//
//        String mrBillID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();
//
//        for (ServiceTicket serviceTicket : ServiceTicketBUS.getInstance().getAll()){
//            if (mrBillID.equals(serviceTicket.getServiceTicketID()) && currentMonth.equals(serviceTicket.getDate().getMonth())){
//                price.add(serviceTicket.getTotalAmount());
//                date.add(serviceTicket.getDate());
//                note.add(serviceTicket.getNote());
//
//                serviceID.add(serviceTicket.getServiceID());
//            }
//        }
//        int count = 0;
//        for (Service service : ServiceBUS.getInstance().getAll()){
//            if (service.getServiceID().equals(serviceID.get(count))){
//                count++;
//                servName.add(service.getName());
//            }
//        }
//
//        String[][] data = new String[servName.size()][5];
//        for (int i = 0; i < servName.size(); i++) {
//            data[i][0] = servName.get(i);
//            data[i][1] = note.get(i);
//            data[i][2] = String.valueOf(price.get(i));
//            data[i][3] = date.get(i).toString();
//            data[i][4] = serviceID.get(i);
//        }
//
//        registeredSerTable.setItems(FXCollections.observableArrayList(data));
//    }
    public void setTableRegisServ(TableView<ServiceUsuage> registeredSerTable) {
        ArrayList<String> servName = new ArrayList<>();
        ArrayList<String> note = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<LocalDate> date = new ArrayList<>();
        ArrayList<String> serviceID = new ArrayList<>();

        try {
            LocalDate currentDate = LocalDate.now();
            Month currentMonth = currentDate.getMonth();

            String mrBillID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();
            if (mrBillID == null) {
                return;
            }

            for (ServiceTicket serviceTicket : ServiceTicketBUS.getInstance().getAll()) {
                if (mrBillID.equals(serviceTicket.getMonthlyRentBillID()) && currentMonth.equals(serviceTicket.getDate().getMonth())) {
                    price.add(serviceTicket.getTotalAmount());
                    date.add(serviceTicket.getDate());
                    note.add(serviceTicket.getNote());

                    serviceID.add(serviceTicket.getServiceID());

                }
            }
            if (serviceID.isEmpty()){
                return;
            }
            ArrayList<Service> services = ServiceBUS.getInstance().getAll();
            for (String sID : serviceID) {
               for (Service service : services){
                   if(service.getServiceID().equals(sID)){
                       servName.add(service.getName());
                   }
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ObservableList<ServiceUsuage> data = FXCollections.observableArrayList();
        for (int i = 0; i < servName.size(); i++) {
            ServiceUsuage serviceUsage = new ServiceUsuage(servName.get(i), String.valueOf(price.get(i)), String.valueOf(date.get(i)), note.get(i));
            data.add(serviceUsage);
        }
        registeredSerTable.setItems(data);
    }

    public void repairInforRegis(ComboBox<String> comboBox, LocalDate date, String note){
            String serName = comboBox.getValue();
            String serID = "";
            for (Service service : ServiceBUS.getInstance().getAll()) {
                if (service.getName().equals(serName)) {
                    serID = service.getServiceID();
                }
            }
            regisServ(serID, note, date);
    }

    public void setTableOldRegisServ(TableView<ServiceUsuage> registeredSerTable) {
        ArrayList<String> servName = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<LocalDate> date = new ArrayList<>();
        ArrayList<String> serviceID = new ArrayList<>();
        ArrayList<Double> quantity = new ArrayList<>();

        try {
            LocalDate currentDate = LocalDate.now();

            String mrBillID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();
            if (mrBillID == null) {
                return;
            }

            for (ServiceTicket serviceTicket : ServiceTicketBUS.getInstance().getAll()) {
                if (mrBillID.equals(serviceTicket.getMonthlyRentBillID()) && serviceTicket.getDate().isBefore(currentDate.withDayOfMonth(1))) {
                    price.add(serviceTicket.getTotalAmount());
                    date.add(serviceTicket.getDate());
                    quantity.add(serviceTicket.getQuantity());

                    serviceID.add(serviceTicket.getServiceID());

                }
            }
            if (serviceID.isEmpty()){
                return;
            }
            ArrayList<Service> services = ServiceBUS.getInstance().getAll();
            for (String sID : serviceID) {
                for (Service service : services){
                    if(service.getServiceID().equals(sID)){
                        servName.add(service.getName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ObservableList<ServiceUsuage> data = FXCollections.observableArrayList();
        for (int i = 0; i < servName.size(); i++) {
            ServiceUsuage serviceUsage = new ServiceUsuage(servName.get(i), String.valueOf(price.get(i)),quantity.get(i), String.valueOf(date.get(i)));
            data.add(serviceUsage);
        }

        registeredSerTable.setItems(data);
    }

}
