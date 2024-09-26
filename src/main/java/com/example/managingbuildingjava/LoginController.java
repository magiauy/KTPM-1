package com.example.managingbuildingjava;

import BUS.CustomersAccountBUS;
import BUS.StaffsAccountBUS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
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

        // Lấy Stage hiện tại từ nút đăng nhập
        Stage stage = (Stage) usernameField.getScene().getWindow();

        CustomersAccountBUS customersAccountBUS = new CustomersAccountBUS();
        String validLogin = customersAccountBUS.checkLogin(username, password);

        if (!validLogin.equals("0")) {
            try {
                Customer customer = new Customer();
                customer.setID(validLogin); // Set ID for Customer if needed
                customer.start(new Stage());

                // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            StaffsAccountBUS staffsAccountBUS = new StaffsAccountBUS();
            validLogin = staffsAccountBUS.checkLogin(username, password);
            if (!validLogin.equals("0")) {
                try {
                    BuildingManager manager = new BuildingManager();
                    manager.setID(validLogin); // Set ID for BuildingManager
                    manager.start(new Stage());

                    // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (username.equals("admin") && password.equals("admin")) {
                    validLogin = "1";
                }
                if (!validLogin.equals("0")) {
                    try {
                        Boss boss = new Boss();
                        boss.start(new Stage());

                        // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showError("Invalid username or password.");
                }
            }
        }
    }

    @FXML
    void enter(KeyEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username and password cannot be empty!");
            return;
        }

        // Lấy Stage hiện tại từ nút đăng nhập
        Stage stage = (Stage) usernameField.getScene().getWindow();

        CustomersAccountBUS customersAccountBUS = new CustomersAccountBUS();
        String validLogin = customersAccountBUS.checkLogin(username, password);

        if (!validLogin.equals("0")) {
            try {
                Customer customer = new Customer();
                customer.setID(validLogin); // Set ID for Customer if needed
                customer.start(new Stage());

                // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            StaffsAccountBUS staffsAccountBUS = new StaffsAccountBUS();
            validLogin = staffsAccountBUS.checkLogin(username, password);
            if (!validLogin.equals("0")) {
                try {
                    BuildingManager manager = new BuildingManager();
                    manager.setID(validLogin); // Set ID for BuildingManager
                    manager.start(new Stage());

                    // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (username.equals("admin") && password.equals("admin")) {
                    validLogin = "1";
                }
                if (!validLogin.equals("0")) {
                    try {
                        Boss boss = new Boss();
                        boss.start(new Stage());

                        // Đóng cửa sổ đăng nhập sau khi đăng nhập thành công
                        stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showError("Invalid username or password.");
                }
            }
        }
    }
    

    private void showError(String message) {
        System.err.println("Error: " + message);
    }

}
