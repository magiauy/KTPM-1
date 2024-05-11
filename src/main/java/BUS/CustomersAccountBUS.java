package BUS;

import java.util.ArrayList;
import DAO.CustomersAccountDAO;
import DTO.CustomersAccount;
import javafx.application.Platform;

public class CustomersAccountBUS {
    private ArrayList<CustomersAccount> listAccount = new ArrayList<>();
    public CustomersAccountBUS() {
        // Initialize the list in the constructor if needed
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

}