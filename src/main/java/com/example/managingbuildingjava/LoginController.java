package com.example.managingbuildingjava;

import BUS.CustomersAccountBUS;
import BUS.StaffsAccountBUS;
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

    private Stage bossStage;

    public LoginController() {
    }

    public LoginController(Stage bossStage) {
        this.bossStage = bossStage;
    }

    @FXML
    protected void onHelloButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty!");
            return;
        }


//        if (CustomersAccountBUS.checkLogin(username, password).equals("0") && StaffsAccountBUS.checkLogin(username, password).equals("0")) {
//            showError("Invalid username or password.");
//        } else {
//            System.out.println("Login successful!");
//            try {
//                if (username.equals("admin"))
//                    Boss.main(null);
//                if (StaffsAccountBUS.getUserType(username, password).equals("staff"))
//                    BuildingManager.openBuildingManagerView();
//                if (CustomersAccountBUS.getUserType(username, password).equals("tenant"))
//                    Customer.openCustomerView();
//            } catch (Exception e) {
//                System.out.println("Error opening Boss view: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
        CustomersAccountBUS customersAccountBUS = new CustomersAccountBUS();
        String validLogin = customersAccountBUS.checkLogin(username, password);
        if (!validLogin.equals("0")) {
            Customer.openCustomerView();
        } else {
            StaffsAccountBUS staffsAccountBUS = new StaffsAccountBUS();
            validLogin = staffsAccountBUS.checkLogin(username, password);
            if (!validLogin.equals("0")) {

                BuildingManager.openBuildingManagerView();
            }
//            else{
//                validLogin = AdminAccountBUS.checkLogin(username, password);
//                if (!validLogin.equals("0")) {
//
//                   Boss.main(null);
//                }
//                else{
//                    //        showStatusMessage("Invalid username or password.");
//                }

        }
    }
    private void showError(String message) {
        System.err.println("Error: " + message);
    }

}
