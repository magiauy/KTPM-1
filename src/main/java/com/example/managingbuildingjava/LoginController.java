package com.example.managingbuildingjava;

import BUS.CustomersAccountBUS;
import BUS.StaffsAccountBUS;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

    @FXML
    private Label lbvalidate_pw;

    @FXML
    private Label lbvalidate_username;


    public LoginController() {
    }

    public LoginController(Stage bossStage) {
        this.bossStage = bossStage;
    }

    @FXML
    protected void onHelloButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()||password.isEmpty()) {
            lbvalidate_username.setText("Không được để trống");
            lbvalidate_pw.setText("Không được để trống");
            return;
        } else {
            lbvalidate_username.setText("");
            lbvalidate_pw.setText("");
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
                    lbvalidate_pw.setText("Mật khẩu không đúng");
                    showError("Invalid username or password.");
                }
            }
        }
    }

    @FXML
    void enter(KeyEvent event) {
        onHelloButtonClick();
    }
    

    private void showError(String message) {
        System.err.println("Error: " + message);
    }

}
