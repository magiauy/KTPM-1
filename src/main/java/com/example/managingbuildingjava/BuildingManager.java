package com.example.managingbuildingjava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BuildingManager extends Application {
    private static String userID;

    public static void setID(String id) {
        userID = id;
    }
    private static BuildingManager instance;

    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        BuildingManager.primaryStage = primaryStage;
    }

    public static BuildingManager getInstance() {
        if (instance == null) {
            instance = new BuildingManager();
        }
        return instance;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        // Pass the userID to BuildingManagerController
        BuildingManagerController.getInstance().setID(userID);
        BuildingManager.primaryStage = primaryStage;
        openBuildingManagerView();
    }

    public static void openBuildingManagerView() {
        try {
//          primaryStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(
            BuildingManager.class.getResource("BuildingManager-view-Page0.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            primaryStage.setTitle("Quản lý tòa nhà!");
            primaryStage.setScene(scene);
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
