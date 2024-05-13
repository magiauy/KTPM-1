package BUS;

import DAO.AdminsAccountDAO;
import DTO.AdminsAccount;

import java.util.ArrayList;

public class AdminsAccountBUS {
    private ArrayList<AdminsAccount> listAccount = new ArrayList<>();

    private static AdminsAccountBUS instance;
    public static AdminsAccountBUS getInstance() {
        if (instance == null) {
            instance = new AdminsAccountBUS();
        }
        return instance;
    }

    public AdminsAccountBUS() {
        // Initialize the list in the constructor if needed
        this.listAccount = getAll();
    }

    public ArrayList<AdminsAccount> getAll() {
        AdminsAccountDAO accountDAO = AdminsAccountDAO.getInstance();
        return accountDAO.selectAll();
    }

    public String checkLogin(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            return "1";
        }
        return "0"; // Đăng nhập không thành công
    }
}
