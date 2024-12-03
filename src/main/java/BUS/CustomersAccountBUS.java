package BUS;

import java.util.ArrayList;

import DAO.ApartmentDAO;
import DAO.CohabitantDAO;
import DAO.CustomersAccountDAO;
import DTO.Apartment;
import DTO.Cohabitant;
import DTO.CustomersAccount;
import javafx.application.Platform;

public class CustomersAccountBUS {
    private ArrayList<CustomersAccount> listAccount = new ArrayList<>();
    public CustomersAccountBUS() {
        this.listAccount = getAll();
    }
    public ArrayList<CustomersAccount> getAll() {
        CustomersAccountDAO accountDAO = CustomersAccountDAO.getInstance();
        return accountDAO.selectAll();
    }
    public String checkLogin(String username, String password ) {
        for (CustomersAccount account : listAccount) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account.getId(); // Đăng nhập thành công
            }
        }
        return "0"; // Đăng nhập không thành công
    }

    public boolean add(CustomersAccount customersAccount) {
        boolean check = CustomersAccountDAO.getInstance().insert(customersAccount) != 0;
        if (check) {
            this.listAccount.add(customersAccount);
        }
        return check;
    }

    public boolean delete(CustomersAccount customersAccount){
        boolean check = CustomersAccountDAO.getInstance().delete(customersAccount.getUsername())!=0;
        if (check){
            this.listAccount.remove(customersAccount);
        }
        return check;
    }


}