package BUS;

import DAO.ServiceDAO;
import DTO.BuildingManager;
import DTO.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

public class ServiceBUS {
    private ArrayList<Service> services = new ArrayList<>();

    private static ServiceBUS instance;

    public static ServiceBUS getInstance() {
        if (instance == null) {
            instance = new ServiceBUS();
        }
        return instance;
    }

    public ServiceBUS() {
        this.services = ServiceDAO.getInstance().selectAll();
    }

    public ArrayList<Service> getAll() {
        ServiceDAO serviceDAO = ServiceDAO.getInstance();
        return serviceDAO.selectAll();
    }

    public boolean add(Service service) {
        boolean check = ServiceDAO.getInstance().insert(service) != 0;
        if (check) {
            this.services.add(service);
        }
        return check;
    }

    public boolean delete(Service service) {
        boolean check = ServiceDAO.getInstance().delete(service.getServiceID()) != 0;
        if (check) {
            this.services.remove(service);
        }
        return check;
    }

    public boolean update(Service service) {
        boolean check = ServiceDAO.getInstance().update(service) != 0;
        if (check) {
            int index = getIndexByServiceID(service.getServiceID());
            if (index != -1) {
                this.services.set(index, service);
            }
        }
        return check;
    }

    public int getIndexByServiceID(String serviceID) {
        for (int i = 0; i < this.services.size(); i++) {
            if (Objects.equals(this.services.get(i).getServiceID(), serviceID)) {
                return i;
            }
        }
        return -1; // Not found
    }

    public void setCombox(ComboBox<String> combox) {
        ObservableList<String> services = FXCollections.observableArrayList();

        for (Service service : ServiceBUS.getInstance().getAll()) {
            if (service.getType().equals("mobile")) {
                services.add(service.getName());
            }
        }

        combox.setItems(services);
    }

    public ArrayList<Service> search(String text, String type) {
        ArrayList<Service> result = new ArrayList<>();
        text = text.toLowerCase();
        if (text == null || type == null || type.isEmpty()) {
            return result;
        }

        switch (type) {
            case "Lọc Theo Loại" -> {
                for (Service i : services) {
                    if (i.getType().toLowerCase().contains(text)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

}
