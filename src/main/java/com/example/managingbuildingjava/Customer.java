package com.example.managingbuildingjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Customer extends Application {
    private static String userID;

    public static void setID(String id) {
        userID = id;
    }
    private static Customer instance;
    public static Customer getInstance() {
        if (instance == null) {
            instance = new Customer();
        }
        return instance;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Customer.primaryStage = primaryStage;
    }

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        CustomerController.getInstance().setID(userID);
        Customer.primaryStage = primaryStage;
        openCustomerView();
    }

    public static void openCustomerView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Customer.class.getResource("Customer-view-Page0.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            primaryStage.setTitle("Quản lý tòa nhà - Khách hàng");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                // Thực hiện các hành động cần thiết trước khi thoát ứng dụng
                System.out.println("Application is closing...");
                // Đóng ứng dụng
                primaryStage.close();
            });
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error loading Customer view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
