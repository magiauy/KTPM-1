package com.example.managingbuildingjava;
import BUS.AdminsAccountBUS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Boss extends Application {
    private static Boss instance;
    public static Boss getInstance() {
        if (instance == null) {
            instance = new Boss();
        }
        return instance;
    }
    private static Stage bossStage;

    public static Stage getBossStage() {
        return bossStage;
    }

    public static void setBossStage(Stage bossStage) {
        Boss.bossStage = bossStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        bossStage = primaryStage;
        BossController.getInstance().setID("Admin");
        openBossView();
    }
    public static void openBossView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Boss.class.getResource("Boss-view-Page0.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        bossStage.setTitle("Admin");
        bossStage.setScene(scene);
        bossStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
