package com.example.managingbuildingjava;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Boss extends Application {

    private static Stage bossStage;
    private static Scene loginScene; 

    @Override
    public void start(Stage primaryStage) throws Exception {
        bossStage = primaryStage;
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        loginScene = new Scene(loginLoader.load(), 400, 300);
        bossStage.setScene(loginScene);
        bossStage.setTitle("Login");
        bossStage.show();
    }
    public static void openBossView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Boss.class.getResource("Boss-view-Page0.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 720);
        bossStage.setTitle("Admin");
        bossStage.setScene(scene);
        bossStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
