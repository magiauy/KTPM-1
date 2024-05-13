package com.example.managingbuildingjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BuildingManager extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        BuildingManagerController.getInstance().setID("BM1");
        BuildingManager.primaryStage = primaryStage;
        openBuildingManagerView();
    }
    
    public static void openBuildingManagerView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
            BuildingManager.class.getResource("BuildingManager-view-Page0.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1024, 720);
            primaryStage.setTitle("Quản lý tòa nhà!");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Application is closing...");
                primaryStage.close();
            });
            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Error loading BuildingManager view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
