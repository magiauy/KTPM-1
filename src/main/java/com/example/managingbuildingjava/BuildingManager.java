package com.example.managingbuildingjava;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BuildingManager extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BuildingManager.class.getResource("BuildingManager-view-Page0.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 500);
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