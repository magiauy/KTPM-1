package com.example.managingbuildingjava;

import DTO.Apartment;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class BuildingManagerController implements Initializable {

    private ObservableList<Apartment> apartmentsList;
    @FXML
    private TextField TxtField__P1__1;

    @FXML
    private TextField TxtField__P1__2;

    @FXML
    private TextField TxtField__P1__3;

    @FXML
    private TextField TxtField__P1__5;

    @FXML
    private TextField TxtField__P1__search;

    @FXML
    private TextField TxtField__P2__search;

    @FXML
    private Button bnt__P1__1;

    @FXML
    private Button bnt__P1__add;

    @FXML
    private Button bnt__P1__delete;

    @FXML
    private Button bnt__P1__update;

    @FXML
    private ComboBox<?> comboBox__P1__1;

    @FXML
    private ComboBox<?> comboBox__P1__2;

    @FXML
    private ComboBox<?> comboBox__P1__3;

    @FXML
    private TableColumn<Apartment, String> maCanHoTable;

    @FXML
    private TableColumn<Apartment, String> soPhongTable;

    @FXML
    private TableView<Apartment> canHoTable;

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
        loadPage("BuildingManager-view-Page2");
    }
    @FXML
    private void page3 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page3");
    }
    @FXML
    private void page4 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page4");
    }
    @FXML
    private void page5 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page5");
    }
    @FXML
    private void page6 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page6");
    }
    @FXML
    private void page7 (MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page7");
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
        apartmentsList = FXCollections.observableArrayList(
                new Apartment("abc", "a1", "aa1", 1.1, 2, 2, "as")
        );

        maCanHoTable.setCellValueFactory(new PropertyValueFactory<Apartment, String>("apartmentID"));
        soPhongTable.setCellValueFactory(new PropertyValueFactory<Apartment, String>("roomNumber"));

        canHoTable.setItems(apartmentsList);
    }



    @FXML
    void themCanHo(ActionEvent event) {
        Apartment apartment = new Apartment();
        apartment.setApartmentID(TxtField__P1__1.getText());
        apartment.setRoomNumber(TxtField__P1__2.getText());
        apartmentsList.add(apartment);
    }

    @FXML
    void suaCanHo(ActionEvent event) {
        System.out.println("sdfsd");
    }

}