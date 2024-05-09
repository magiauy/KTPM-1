package com.example.managingbuildingjava;

import BUS.AdminsAccountBUS;
import BUS.CustomersAccountBUS;
import BUS.StaffsAccountBUS;
import DTO.StaffsAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    private Stage primaryStage = new Stage();
    private Scene loginScene;
    private Scene bossScene;
    private Scene buiScene;
    private Scene cusScene;

    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel = new Label();


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;


        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        loginScene = new Scene(loginLoader.load(), 498, 272);


        FXMLLoader bossLoader = new FXMLLoader(getClass().getResource("Boss-view-Page0.fxml"));
        Pane bossPane = bossLoader.load();
        bossScene = new Scene(bossPane, 1024, 720);

        FXMLLoader BuildingLoader = new FXMLLoader(getClass().getResource("BuildingManager-view-Page0.fxml"));
        Pane Building = BuildingLoader.load();
        buiScene = new Scene(Building, 1024, 720);

        FXMLLoader CustomerLoader = new FXMLLoader(getClass().getResource("Customer-view-Page0.fxml"));
        Pane Customer = CustomerLoader.load();
        cusScene = new Scene(Customer, 1182, 720);

        usernameField = (TextField) loginLoader.getNamespace().get("usernameField");
        passwordField = (PasswordField) loginLoader.getNamespace().get("passwordField");
        statusLabel = (Label) loginLoader.getNamespace().get("statusLabel");
        Button loginButton = (Button) loginLoader.getNamespace().get("loginButton");


        // Xử lý sự kiện khi nhấn nút đăng nhập
        loginButton.setOnAction(event -> {
            handleLogin();
        });

        // Hiển thị cửa sổ đăng nhập
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate username and password
//        if (username.isEmpty() || password.isEmpty()) {
//            showStatusMessage("Username and password cannot be empty!");
//            return;
//        }

        // Call checkLogin method (implementation in AccountBUS)
        CustomersAccountBUS cus = new CustomersAccountBUS();
        String validLogin = cus.checkLogin(username, password);
        System.out.println("validLogin: " + validLogin);
        if (!validLogin.equals("0")) {
            CustomerController.getInstance().setID(validLogin);
            primaryStage.setScene(cusScene);
        } else {
            StaffsAccountBUS staff = new StaffsAccountBUS();
            validLogin = staff.checkLogin(username, password);
            if (!validLogin.equals("0")) {
                BuildingManagerController.getInstance().setID(validLogin);
                primaryStage.setScene(buiScene);
            }
            else {
                AdminsAccountBUS adminsAccountBUS = new AdminsAccountBUS();
                validLogin = adminsAccountBUS.checkLogin(username, password);
                if (!validLogin.equals("0")) {
                    BossController.getInstance().setID(validLogin);
                    primaryStage.setScene(bossScene);
                } else {
                    System.out.println("ko dn dc");
                }
            }
//
//                else{
//                    //        showStatusMessage("Invalid username or password.");
//                }

        }

//    private void showStatusMessage(String message) {
//        statusLabel.setText(message);
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
    }
}