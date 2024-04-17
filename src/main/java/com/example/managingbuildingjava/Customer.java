package com.example.managingbuildingjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Customer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Customer.class.getResource("Customer-view-Page0.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 720);
        stage.setTitle("Quản lý tòa nhà!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            // Thực hiện các hành động cần thiết trước khi thoát ứng dụng
            System.out.println("Application is closing...");
            // Đóng ứng dụng
            System.exit(0);
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}