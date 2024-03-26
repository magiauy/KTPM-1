package com.example.managingbuildingjava;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class BuildingManagerController implements Initializable {
    private volatile boolean stop = false;
    private volatile Thread thread;
    int dem =0;
    @FXML
    private BorderPane bp;
    @FXML
    private Pane mp;
    @FXML
    private void page0 (MouseEvent event){
        stop = false;
        TimeNow();
        bp.setCenter(mp);
    }
    @FXML
    private void page1 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page2 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page3 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page4 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page5 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page6 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private void page7 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }
    @FXML
    private Label time;

    private void loadPage(String page) throws IOException {
        stop = true;
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        bp.setCenter(root);
    }

    private void TimeNow(){
        thread = new Thread(()->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format((new Date()));
                Platform.runLater(()->{
                    if (time!=null) time.setText(timenow);
                });
                dem++;
                System.out.println(dem);
            }
        });
        thread.start();
    }

    @FXML
    void Close_Clicked(MouseEvent event){
        stop = true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}