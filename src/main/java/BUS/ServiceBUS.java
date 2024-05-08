package BUS;

import DAO.ServiceDAO;
import DTO.Service;

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
}
