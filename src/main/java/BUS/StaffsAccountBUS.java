package BUS;

import java.util.ArrayList;
import DAO.StaffsAccountDAO;
import DTO.StaffsAccount;
import javafx.application.Platform;

public class StaffsAccountBUS {
    private ArrayList<StaffsAccount> listAccount = new ArrayList<>();
    public StaffsAccountBUS() {
        // Initialize the list in the constructor if needed
        this.listAccount = getAll();
    }
    public ArrayList<StaffsAccount> getAll() {
        StaffsAccountDAO accountDAO = StaffsAccountDAO.getInstance();
        return accountDAO.selectAll();
    }
    public String checkLogin(String username, String password ) {
        for (StaffsAccount account : listAccount) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account.getId(); // Đăng nhập thành công
            }
        }
        return "0"; // Đăng nhập không thành công
    }

}