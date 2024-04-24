package BUS;

import java.util.ArrayList;
import DAO.AcountDAO;
import DTO.Acount;
import javafx.application.Platform;

public class AccountBUS {
    private ArrayList<Acount> listAccount = new ArrayList<>();

    public AccountBUS() {
        // Initialize the list in the constructor if needed
        this.listAccount = getAll();
    }
    public ArrayList<Acount> getAll() {
        AcountDAO accountDAO = AcountDAO.getInstance();
        return accountDAO.selectAll();
    }
    public String checkLogin(String username, String password ) {
        for (Acount account : listAccount) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account.getId(); // Đăng nhập thành công
            }
        }
        return "0"; // Đăng nhập không thành công
    }

    public String getUserType(String username, String password) {
        // Kiểm tra thông tin đăng nhập trong danh sách tài khoản
        for (Acount account : listAccount) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return account.getRole();
            }
        }
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy
    }
  
}
