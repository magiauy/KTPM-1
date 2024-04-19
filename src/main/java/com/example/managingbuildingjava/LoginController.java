package com.example.managingbuildingjava;

import BUS.AccountBUS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private AccountBUS accountBUS = new AccountBUS(); // Placeholder
    private Stage bossStage;

    public LoginController() {
        // Constructor mặc định được thêm vào
    }

    // Thêm constructor có tham số để nhận Stage từ Boss
    public LoginController(Stage bossStage) {
        this.bossStage = bossStage;
    }
    // Constructor to receive Stage reference

    @FXML
    protected void onHelloButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate username and password (replace with actual validation logic)
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty!");
            return;
        }

        // Call checkLogin method (implementation in AccountBUS)
        boolean validLogin = accountBUS.checkLogin(username, password);

        if (validLogin) {
            System.out.println("Login successful!");
            try {
                String userType = accountBUS.getUserType(username, password);
                if (userType.equals("admin"))
                    Boss.main(null);
                if (userType.equals("khachhang"))
                    BuildingManager.openBuildingManagerView();
                if (userType.equals("quanli"))
                   Customer.openCustomerView();

            } catch (Exception e) {
                System.out.println("Error opening Boss view: " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        System.err.println("Error: " + message); // Placeholder for user feedback
    }

}
