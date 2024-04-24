package com.example.managingbuildingjava;

import BUS.*;
import DTO.*;
import DTO.BuildingManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class BuildingManagerController implements Initializable {
    private static BuildingManagerController instance;
    public static BuildingManagerController getInstance() {
        if (instance == null) {
            instance = new BuildingManagerController();
        }
        return instance;
    }

    private static String ID;
    public void setID (String ID){
        BuildingManagerController.ID = ID;
    }

    public String getID() {
        return ID;
    }
    //

    public TextField TxtField__P1__search;
    public Label txtField__P1__1;
    public TextField TxtField__P2__search;
    public TextField TxtField__P4__search;
    public Button bnt__P1__search;
    public TextField TxtField__P3__search;
    @FXML
    private Button bnt__P1__add;

    private volatile boolean stop = false;
    private volatile Thread thread;
    @FXML
    private BorderPane bp;
    @FXML
    private Pane mp;



    @FXML
    private void page0(MouseEvent event) {
        stop = false;
        TimeNow();
        bp.setCenter(mp);
    }

    @FXML
    private void page1(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page1");
    }

    @FXML
    private void page2(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page2");
    }

    @FXML
    private void page3(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page3");
    }

    @FXML
    private void page4(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page4");
    }

    @FXML
    private void page5(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page5");
    }

    @FXML
    private void page6(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page6");
    }

    @FXML
    private void page7(MouseEvent event) throws IOException {
        loadPage("BuildingManager-view-Page7");
    }

    @FXML
    private Label time, numberOfBuildings;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Number> barChart;
    private ObservableList<BuildingManager> buildingManagerList;

    private void loadPage(String page) throws IOException {
        stop = true;
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        bp.setCenter(root);
    }
    private void TimeNow() {
        thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            while (!stop) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                final String timenow = sdf.format((new Date()));
                Platform.runLater(() -> {
                    if (time != null)
                        time.setText(timenow);
                });
            }
        });
        thread.start();
    }
    @FXML
    void Close_Clicked(MouseEvent event) {
        stop = true;
    }
    public void totalOfBuldings() {
        if (numberOfBuildings == null) {
            return;
        }
        try {
            BuildingBUS buildingBUS = new BuildingBUS();
            buildingBUS.setTotalNumberOfBuildings(numberOfBuildings);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updatePieChart() {
        if (pieChart == null) {
            return;
        }
        try {
            BuildingBUS buildingBUS = new BuildingBUS();
            buildingBUS.setLocationOfBuildings(pieChart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void drawBarChart() {
        if (barChart == null) {
            return;
        }

        try {
            buildingManagerList = FXCollections.observableArrayList();
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            buildingManagerBUS.getGenderOfBDManager(barChart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField TxtField__P1__1 = new TextField();

    @FXML
    private TextField TxtField__P1__2 = new TextField();

    @FXML
    private TextField TxtField__P1__3 = new TextField();

    @FXML
    private TextField TxtField__P1__4 = new TextField();

    @FXML
    private TextField TxtField__P1__5 = new TextField();

    @FXML
    private Button bnt__P1__1;

    @FXML
    private Button bnt__P1__delete;

    @FXML
    private Button bnt__P1__update;

    @FXML
    private ComboBox<String> comboBox__P1__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P1__2 = new ComboBox<>() ;

    @FXML
    private ComboBox<String> comboBox__P1__3 = new ComboBox<>();

    @FXML
    private TableColumn<Apartment, String> dienTichTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, String> maCanHoTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, String> maToaNhaTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, String> noiThatTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, Integer> soPhongNguTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, String> soPhongTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, Integer> soPhongTamTable = new TableColumn<>();

    @FXML
    private TableView<Apartment> table__P1__1 = new TableView<>();

    private ObservableList<Apartment> apartmentObservableList;

    public ObservableList<Apartment> getApartmentList(){
        ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        List<Apartment> apartments = apartmentBUS.getAll();
        apartmentObservableList.addAll(apartments);
        return apartmentObservableList;
    }

    private void refreshFormApartment(){
        TxtField__P1__1.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__3.setText("");
        TxtField__P1__4.setText("");
        TxtField__P1__5.setText("");
        comboBox__P1__3.getSelectionModel().clearSelection();
    }

    private void initApartment(){
        maCanHoTable.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        maToaNhaTable.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
        soPhongTable.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        dienTichTable.setCellValueFactory(new PropertyValueFactory<>("area"));
        soPhongNguTable.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        soPhongTamTable.setCellValueFactory(new PropertyValueFactory<>("bathrooms"));
        noiThatTable.setCellValueFactory(new PropertyValueFactory<>("furniture"));
        apartmentObservableList = getApartmentList();
        table__P1__1.setItems(apartmentObservableList);
    }

    @FXML
    void showApartment(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        TxtField__P1__1.setText(selectedApartment.getApartmentID());
        TxtField__P1__2.setText(selectedApartment.getRoomNumber());
        TxtField__P1__3.setText(selectedApartment.getArea());
        TxtField__P1__4.setText(String.valueOf(selectedApartment.getBedrooms()));
        TxtField__P1__5.setText(String.valueOf(selectedApartment.getBathrooms()));
        comboBox__P1__3.setValue(selectedApartment.getFurniture());
    }

    @FXML
    void suaCanHo(ActionEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        selectedApartment.setApartmentID(TxtField__P1__1.getText());
        selectedApartment.setBuildingID("B1");
        selectedApartment.setRoomNumber(TxtField__P1__2.getText());
        selectedApartment.setArea(TxtField__P1__3.getText());
        selectedApartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
        selectedApartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
        selectedApartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        boolean updateSuccess = apartmentBUS.update(selectedApartment);
        System.out.println(updateSuccess);
        if (updateSuccess) {
            int selectedIndex = table__P1__1.getSelectionModel().getSelectedIndex();
            apartmentObservableList.set(selectedIndex, selectedApartment);
            table__P1__1.refresh();
            refreshFormApartment();
        } else {
            System.err.println("Không thể cập nhật căn hộ trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themCanHo(ActionEvent event) {
        try {
            Apartment newApartment = new Apartment();
            BuildingManagerBUS bus = new BuildingManagerBUS();
            List<BuildingManager> buildingManagers = bus.getAll();

            for (BuildingManager buildingManager : buildingManagers) {
                if (ID.equals(buildingManager.getBuildingManagerId())) {
                    newApartment.setBuildingID(buildingManager.getBuildingId());
                }
            }

            newApartment.setApartmentID(TxtField__P1__1.getText());
            newApartment.setRoomNumber(TxtField__P1__2.getText());
            newApartment.setArea(TxtField__P1__3.getText());
            newApartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
            newApartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
            newApartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());

            ApartmentBUS apartmentBUS = new ApartmentBUS();
            apartmentBUS.add(newApartment);

            apartmentObservableList.add(newApartment);
            refreshFormApartment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaCanHo(ActionEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        if (selectedApartment!=null){
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            boolean deleteSuccess = apartmentBUS.delete(selectedApartment);
            if (deleteSuccess) {
                apartmentObservableList.remove(selectedApartment);
                table__P1__1.refresh();
                refreshFormApartment();
            } else{
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    @FXML
    private TextField TxtField__P2_1__1 = new TextField();

    @FXML
    private TextField TxtField__P2_1__2 = new TextField();

    @FXML
    private TextField TxtField__P2_1__3 = new TextField();

    @FXML
    private TextField TxtField__P2_1__4 = new TextField();

    @FXML
    private DatePicker TxtField__P2_1__41 = new DatePicker();

    @FXML
    private TextField TxtField__P2_1__5 = new TextField();

    @FXML
    private TextField TxtField__P2_1__51 = new TextField();

    @FXML
    private TextField TxtField__P2_1__search = new TextField();

    @FXML
    private TextField TxtField__P2__1 = new TextField();

    @FXML
    private TextField TxtField__P2__2 = new TextField();

    @FXML
    private TextField TxtField__P2__3 = new TextField();

    @FXML
    private TextField TxtField__P2__4 = new TextField();

    @FXML
    private DatePicker TxtField__P2__5 = new DatePicker();

    @FXML
    private TextField TxtField__P2__51 = new TextField();

    @FXML
    private Button bnt__P2_1__1;

    @FXML
    private Button bnt__P2_1__add;

    @FXML
    private Button bnt__P2_1__delete;

    @FXML
    private Button bnt__P2_1__update;

    @FXML
    private Button bnt__P2__1;

    @FXML
    private Button bnt__P2__add;

    @FXML
    private Button bnt__P2__delete;

    @FXML
    private Button bnt__P2__update;

    @FXML
    private TableColumn<Cohabitant, String> cCCDCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<?, String> cCCDTable = new TableColumn<>();

    @FXML
    private ComboBox<String> comboBox__P2_1__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P2_1__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P2_1__3 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P2__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P2__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P2__3 = new ComboBox<>();

    @FXML
    private TableColumn<Cohabitant, String> gioiTinhCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> gioiTinhKhachHangTable = new TableColumn<>();

    @FXML
    private TableColumn<Cohabitant, String> hoCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> hoKhachHangTable = new TableColumn<>();

    @FXML
    private TableColumn<Cohabitant, String> maCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> maKhachHangTable_1 = new TableColumn<>();

    @FXML
    private TableColumn<Cohabitant, String> maKhachHangTable_2 = new TableColumn<>();

    @FXML
    private TableColumn<Cohabitant, String> ngaySinhCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> ngaySinhKhachHangTable = new TableColumn<>();

    @FXML
    private TableColumn<Cohabitant, String> soDienThoaiCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> soDienThoaiKhachHangTable = new TableColumn<>();

    @FXML
    private TableView<Cohabitant> table__P2_1__1 = new TableView<>();

    @FXML
    private TableView<Tenant> table__P2__1 = new TableView<>();

    @FXML
    private TableColumn<Cohabitant, String> tenCuDanTable = new TableColumn<>();

    @FXML
    private TableColumn<Tenant, String> tenKhachHangTable = new TableColumn<>();

    private ObservableList<Tenant> tenantObservableList ;

    public ObservableList<Tenant> getTenantObservableList(){
        ObservableList<Tenant> tenantObservableLists = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        List<Tenant> tenants = tenantBUS.getAll();
        tenantObservableLists.addAll(tenants);
        return tenantObservableLists;
    }

    private ObservableList<Cohabitant> cohabitantObservableList; ;

    public ObservableList<Cohabitant> getCohabitantObservableList(){
        ObservableList<Cohabitant> cohabitantObservableLists = FXCollections.observableArrayList();
        CohabitantBUS cohabitantBUS = new CohabitantBUS();
        List<Cohabitant> cohabitants = cohabitantBUS.getAll();
        cohabitantObservableLists.addAll(cohabitants);
        return cohabitantObservableLists;
    }

    public void initTenant(){
        maKhachHangTable_1.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        hoKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tenKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        soDienThoaiKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        ngaySinhKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("dateOfBirthDay"));
        gioiTinhKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("gender"));
        cCCDTable.setCellValueFactory(new PropertyValueFactory<>("citizenIdentityCard"));

        tenantObservableList = getTenantObservableList();
        table__P2__1.setItems(tenantObservableList);
    }

    public void initCohabitant(){
        maCuDanTable.setCellValueFactory(new PropertyValueFactory<>("cohabitantID"));
        maKhachHangTable_2.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        hoCuDanTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tenCuDanTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        soDienThoaiCuDanTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        gioiTinhCuDanTable.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ngaySinhCuDanTable.setCellValueFactory(new PropertyValueFactory<>("dateOfBirthDay"));
        cCCDCuDanTable.setCellValueFactory(new PropertyValueFactory<>("citizenIdentityCard"));

        cohabitantObservableList = getCohabitantObservableList();
        table__P2_1__1.setItems(cohabitantObservableList);
    }

    public void refreshFormCohabitant(){
        TxtField__P2_1__1.setText("");
        TxtField__P2_1__2.setText("");
        TxtField__P2_1__3.setText("");
        TxtField__P2_1__4.setText("");
        TxtField__P2_1__5.setText("");
        TxtField__P2_1__41.setValue(null);
        TxtField__P2_1__51.setText("");
        comboBox__P2_1__3.getSelectionModel().clearSelection();
    }

    public void refreshFormTenant(){
        TxtField__P2__1.setText("");
        TxtField__P2__2.setText("");
        TxtField__P2__3.setText("");
        TxtField__P2__4.setText("");
        TxtField__P2__5.setValue(null);
        TxtField__P2__51.setText("");
        comboBox__P2__3.getSelectionModel().clearSelection();
    }

    @FXML
    void showTenant(MouseEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
        TxtField__P2__1.setText(selectedTenant.getTenantID());
        TxtField__P2__2.setText(selectedTenant.getFirstName());
        TxtField__P2__3.setText(selectedTenant.getLastName());
        TxtField__P2__4.setText(selectedTenant.getPhoneNumber());
        TxtField__P2__5.setValue(selectedTenant.getDateOfBirthDay());
        comboBox__P2__3.setValue(selectedTenant.getGender());
        TxtField__P2__51.setText(selectedTenant.getCitizenIdentityCard());
    }

    @FXML
    void showCohabitant(MouseEvent event) {
        Cohabitant selectedCohabitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        TxtField__P2_1__1.setText(selectedCohabitant.getCohabitantID());
        TxtField__P2_1__2.setText(selectedCohabitant.getTenantID());
        TxtField__P2_1__3.setText(selectedCohabitant.getFirstName());
        TxtField__P2_1__4.setText(selectedCohabitant.getLastName());
        TxtField__P2_1__5.setText(selectedCohabitant.getPhoneNumber());
        comboBox__P2_1__3.setValue(selectedCohabitant.getGender());
        TxtField__P2_1__41.setValue(selectedCohabitant.getDateOfBirthDay());
        TxtField__P2_1__51.setText(selectedCohabitant.getCitizenIdentityCard());
    }

    @FXML
    void suaCuDan(ActionEvent event) {
        Cohabitant selectedCohabitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        selectedCohabitant.setCohabitantID(TxtField__P2_1__1.getText());
        selectedCohabitant.setTenantID(TxtField__P2_1__2.getText());
        selectedCohabitant.setFirstName(TxtField__P2_1__3.getText());
        selectedCohabitant.setLastName(TxtField__P2_1__4.getText());
        selectedCohabitant.setPhoneNumber(TxtField__P2_1__5.getText());
        selectedCohabitant.setDateOfBirthDay(TxtField__P2_1__41.getValue());
        selectedCohabitant.setGender(comboBox__P2_1__3.getSelectionModel().getSelectedItem());
        selectedCohabitant.setCitizenIdentityCard(TxtField__P2_1__51.getText());
        CohabitantBUS cohabitantBUS = new CohabitantBUS();
        boolean updateSuccess = cohabitantBUS.update(selectedCohabitant);
        if (updateSuccess) {
            int selectedIndex = table__P2_1__1.getSelectionModel().getSelectedIndex();
            cohabitantObservableList.set(selectedIndex, selectedCohabitant);
            table__P2_1__1.refresh();
            refreshFormCohabitant();
        } else {
            System.err.println("Không thể cập nhật căn hộ trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void suaKhachHang(ActionEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
        selectedTenant.setTenantID(TxtField__P2__1.getText());
        selectedTenant.setFirstName(TxtField__P2__2.getText());
        selectedTenant.setLastName(TxtField__P2__3.getText());
        selectedTenant.setPhoneNumber(TxtField__P2__4.getText());
        selectedTenant.setDateOfBirthDay(TxtField__P2__5.getValue());
        selectedTenant.setGender(comboBox__P2__3.getSelectionModel().getSelectedItem());
        selectedTenant.setCitizenIdentityCard(TxtField__P2__51.getText());
        TenantBUS tenantBUS = new TenantBUS();
        boolean updateSuccess = tenantBUS.update(selectedTenant);
        System.out.println(updateSuccess);
        if (updateSuccess) {
            int selectedIndex = table__P2__1.getSelectionModel().getSelectedIndex();
            tenantObservableList.set(selectedIndex, selectedTenant);
            table__P2__1.refresh();
            refreshFormTenant();
        } else {
            System.err.println("Không thể cập nhật căn hộ trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themCuDan(ActionEvent event) {
        try{
            Cohabitant cohabitant = new Cohabitant();
            cohabitant.setCohabitantID(TxtField__P2_1__1.getText());
            cohabitant.setTenantID(TxtField__P2_1__2.getText());
            cohabitant.setFirstName(TxtField__P2_1__3.getText());
            cohabitant.setLastName(TxtField__P2_1__4.getText());
            cohabitant.setPhoneNumber(TxtField__P2_1__5.getText());
            cohabitant.setDateOfBirthDay(TxtField__P2_1__41.getValue());
            cohabitant.setGender(comboBox__P2_1__3.getSelectionModel().getSelectedItem());
            cohabitant.setCitizenIdentityCard(TxtField__P2_1__51.getText());

            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            cohabitantBUS.add(cohabitant);

            cohabitantObservableList.add(cohabitant);
            refreshFormCohabitant();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void themKhachHang(ActionEvent event) {
        try{
            Tenant tenant = new Tenant();
            tenant.setTenantID(TxtField__P2__1.getText());
            tenant.setFirstName(TxtField__P2__2.getText());
            tenant.setLastName(TxtField__P2__3.getText());
            tenant.setPhoneNumber(TxtField__P2__4.getText());
            tenant.setDateOfBirthDay(TxtField__P2__5.getValue());
            tenant.setGender(comboBox__P2__3.getSelectionModel().getSelectedItem());
            tenant.setCitizenIdentityCard(TxtField__P2__51.getText());

            TenantBUS tenantBUS = new TenantBUS();
            tenantBUS.add(tenant);

            tenantObservableList.add(tenant);
            refreshFormTenant();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void xoaCuDan(ActionEvent event) {
        Cohabitant selectedCohatitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        if (selectedCohatitant!=null){
            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            boolean deleteSuccess = cohabitantBUS.delete(selectedCohatitant);
            if (deleteSuccess) {
                cohabitantObservableList.remove(selectedCohatitant);
                table__P2_1__1.refresh();
                refreshFormCohabitant();
            } else{
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    @FXML
    void xoaKhachHang(ActionEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
        if (selectedTenant!=null){
            TenantBUS tenantBUS = new TenantBUS();
            boolean deleteSuccess = tenantBUS.delete(selectedTenant);
            if (deleteSuccess) {
                tenantObservableList.remove(selectedTenant);
                table__P2__1.refresh();
                refreshFormTenant();
            } else{
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            comboBox__P1__3.getItems().addAll("Cơ bản", "Tiện nghi", "Cao cấp");
            comboBox__P1__3.setPromptText("");
            initApartment();
            comboBox__P2__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2__3.setPromptText("");
            initTenant();
            initCohabitant();
            comboBox__P2_1__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2_1__3.setPromptText("");
            //Chạy page 0
            totalOfBuldings();
            updatePieChart();
            drawBarChart();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}