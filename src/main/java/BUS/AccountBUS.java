package BUS;

import java.util.ArrayList;

import com.example.managingbuildingjava.Boss;

import DAO.AcountDAO;
import DTO.Acount;
import javafx.application.Platform;

public class AccountBUS {
    private ArrayList<Acount> listAcounts = new ArrayList<>();

    public AccountBUS() {
        // You can initialize anything here if needed
    }

    public ArrayList<Acount> getAll() {
        AcountDAO acountDAO = AcountDAO.getInstance();
        return acountDAO.selectAll();
    }

    public boolean checkLogin(String username, String password) {
        ArrayList<Acount> listAccounts = getAll();
        for (Acount acc : listAccounts) {
            if (acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                switch (acc.getLoai()) {
                    case "admin":
                        System.out.println("Admin logged in successfully!");
                       // Gọi phương thức từ JavaFX Application Thread
                        return true; // Login successful
                    default:
                        System.out.println("Unknown account type!");
                        break;
                }
            }
        }
        System.out.println("Login failed!"); // Invalid credentials
        return false; // Login failed
    }
}
