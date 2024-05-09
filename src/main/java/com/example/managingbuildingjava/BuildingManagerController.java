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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public void setID(String ID) {
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
    private ComboBox<String> comboBox__P1__2 = new ComboBox<>();

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

    public ObservableList<Apartment> getApartmentList() {
        ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        List<Apartment> apartments = apartmentBUS.getAll();
        apartmentObservableList.addAll(apartments);
        return apartmentObservableList;
    }

    private void refreshFormApartment() {
        TxtField__P1__1.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__3.setText("");
        TxtField__P1__4.setText("");
        TxtField__P1__5.setText("");
        comboBox__P1__3.getSelectionModel().clearSelection();
    }

    private void initApartment() {
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
        selectedApartment.setArea(TxtField__P1__3.getText() + "m\u00B2");
        selectedApartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
        selectedApartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
        selectedApartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        boolean updateSuccess = apartmentBUS.update(selectedApartment);
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
            newApartment.setArea(TxtField__P1__3.getText() + "m\u00B2");
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
        if (selectedApartment != null) {
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            boolean deleteSuccess = apartmentBUS.delete(selectedApartment);
            if (deleteSuccess) {
                apartmentObservableList.remove(selectedApartment);
                table__P1__1.refresh();
                refreshFormApartment();
            } else {
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

    private ObservableList<Tenant> tenantObservableList;

    public ObservableList<Tenant> getTenantObservableList() {
        ObservableList<Tenant> tenantObservableLists = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        List<Tenant> tenants = tenantBUS.getAll();
        tenantObservableLists.addAll(tenants);
        return tenantObservableLists;
    }

    private ObservableList<Cohabitant> cohabitantObservableList;;

    public ObservableList<Cohabitant> getCohabitantObservableList() {
        ObservableList<Cohabitant> cohabitantObservableLists = FXCollections.observableArrayList();
        CohabitantBUS cohabitantBUS = new CohabitantBUS();
        List<Cohabitant> cohabitants = cohabitantBUS.getAll();
        cohabitantObservableLists.addAll(cohabitants);
        return cohabitantObservableLists;
    }

    public void initTenant() {
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

    public void initCohabitant() {
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

    public void refreshFormCohabitant() {
        TxtField__P2_1__1.setText("");
        TxtField__P2_1__2.setText("");
        TxtField__P2_1__3.setText("");
        TxtField__P2_1__4.setText("");
        TxtField__P2_1__5.setText("");
        TxtField__P2_1__41.setValue(null);
        TxtField__P2_1__51.setText("");
        comboBox__P2_1__3.getSelectionModel().clearSelection();
    }

    public void refreshFormTenant() {
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
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void themKhachHang(ActionEvent event) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaCuDan(ActionEvent event) {
        Cohabitant selectedCohatitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        if (selectedCohatitant != null) {
            System.out.println(selectedCohatitant);
            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            boolean deleteSuccess = cohabitantBUS.delete(selectedCohatitant);
            if (deleteSuccess) {
                cohabitantObservableList.remove(selectedCohatitant);
                table__P2_1__1.refresh();
                refreshFormCohabitant();
            } else {
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    @FXML
    void xoaKhachHang(ActionEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
        if (selectedTenant != null) {
            TenantBUS tenantBUS = new TenantBUS();
            boolean deleteSuccess = tenantBUS.delete(selectedTenant);
            if (deleteSuccess) {
                tenantObservableList.remove(selectedTenant);
                table__P2__1.refresh();
                refreshFormTenant();
            } else {
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    //

    @FXML
    private TableColumn<MonthlyRentBill, String> ColumnP3__1 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, String> ColumnP3__2 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, String> ColumnP3__3 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, String> ColumnP3__4 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, LocalDate> ColumnP3__5 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, Integer> ColumnP3__6 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, DecimalFormat> ColumnP3__7 = new TableColumn<>();

    @FXML
    private TableColumn<MonthlyRentBill, String> ColumnP3__8 = new TableColumn<>();

    @FXML
    private TextField TxtField__P3__1 = new TextField();

    @FXML
    private TextField TxtField__P3__2 = new TextField();

    @FXML
    private TextField TxtField__P3__3 = new TextField();

    @FXML
    private TextField TxtField__P3__4 = new TextField();

    @FXML
    private TextField TxtField__P3__5 = new TextField();

    @FXML
    private TextField TxtField__P3__6 = new TextField();

    @FXML
    private Button bnt__P3__add;

    @FXML
    private Button bnt__P3__delete;

    @FXML
    private Button bnt__P3__update;

    @FXML
    private ComboBox<String> comboBox__P3__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P3__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P3__3 = new ComboBox<>();

    @FXML
    private DatePicker datePicker__P3 = new DatePicker();

    @FXML
    private TableView<MonthlyRentBill> table__P3__1 = new TableView<>();

    private ObservableList<MonthlyRentBill> monthlyRentBillObservableList;

    public ObservableList<MonthlyRentBill> getMonthlyRentBillObservableList() {
        ObservableList<MonthlyRentBill> monthlyRentBillsObservableLists = FXCollections.observableArrayList();
        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        List<MonthlyRentBill> monthlyRentBills = monthlyRentBillBUS.getAll();
        monthlyRentBillsObservableLists.addAll(monthlyRentBills);
        return monthlyRentBillsObservableLists;
    }

    public void initMonthlyRentBill() {
        ColumnP3__1.setCellValueFactory(new PropertyValueFactory<>("monthlyRentBillID"));
        ColumnP3__2.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        ColumnP3__3.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        ColumnP3__5.setCellValueFactory(new PropertyValueFactory<>("date"));
        ColumnP3__6.setCellValueFactory(new PropertyValueFactory<>("repaymentPeriod"));
        ColumnP3__7.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));
        ColumnP3__8.setCellValueFactory(new PropertyValueFactory<>("status"));

        monthlyRentBillObservableList = getMonthlyRentBillObservableList();
        table__P3__1.setItems(monthlyRentBillObservableList);
    }

    @FXML
    void showMonthlyRentBill(MouseEvent event) {
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
        TxtField__P3__1.setText(monthlyRentBill.getMonthlyRentBillID());
        TxtField__P3__2.setText(monthlyRentBill.getApartmentID());
        TxtField__P3__3.setText(monthlyRentBill.getTenantID());
        datePicker__P3.setValue(monthlyRentBill.getDate());
        TxtField__P3__5.setText(String.valueOf(monthlyRentBill.getRepaymentPeriod()));
        TxtField__P3__6.setText(String.valueOf(monthlyRentBill.getTotalPayment()));
        comboBox__P3__3.setValue(monthlyRentBill.getStatus());
    }

    public void refreshFormMonthlyRentBill() {
        TxtField__P3__1.setText("");
        TxtField__P3__2.setText("");
        TxtField__P3__3.setText("");
        datePicker__P3.setValue(null);
        TxtField__P3__5.setText("");
        TxtField__P3__6.setText("");
        comboBox__P3__3.getSelectionModel().clearSelection();
    }

    @FXML
    void suaPhieuThu(ActionEvent event) {
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
        monthlyRentBill.setMonthlyRentBillID(TxtField__P3__1.getText());
        monthlyRentBill.setApartmentID(TxtField__P3__2.getText());
        monthlyRentBill.setTenantID(TxtField__P3__3.getText());
        monthlyRentBill.setDate(datePicker__P3.getValue());
        monthlyRentBill.setRepaymentPeriod(Integer.parseInt(TxtField__P3__5.getText()));
        monthlyRentBill.setTotalPayment(Double.parseDouble(TxtField__P3__6.getText()));
        monthlyRentBill.setStatus(comboBox__P3__3.getSelectionModel().getSelectedItem());
        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        boolean updateSuccess = monthlyRentBillBUS.update(monthlyRentBill);
        if (updateSuccess) {
            int selectedIndex = table__P3__1.getSelectionModel().getSelectedIndex();
            monthlyRentBillObservableList.set(selectedIndex, monthlyRentBill);
            table__P3__1.refresh();
            refreshFormMonthlyRentBill();
        } else {
            System.err.println("Không thể cập nhật phiếu thu trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themPhieuThu(ActionEvent event) {
        try {
            MonthlyRentBill monthlyRentBill = new MonthlyRentBill();
            monthlyRentBill.setMonthlyRentBillID(TxtField__P3__1.getText());
            monthlyRentBill.setApartmentID(TxtField__P3__2.getText());
            monthlyRentBill.setTenantID(TxtField__P3__3.getText());
            monthlyRentBill.setDate(datePicker__P3.getValue());
            monthlyRentBill.setRepaymentPeriod(Integer.parseInt(TxtField__P3__5.getText()));
            monthlyRentBill.setTotalPayment(Double.parseDouble(TxtField__P3__6.getText()));
            monthlyRentBill.setStatus(comboBox__P3__3.getSelectionModel().getSelectedItem());

            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            monthlyRentBillBUS.add(monthlyRentBill);

            monthlyRentBillObservableList.add(monthlyRentBill);
            table__P3__1.setItems(monthlyRentBillObservableList);
            refreshFormMonthlyRentBill();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaPhieuThu(ActionEvent event) {
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
        if (monthlyRentBill != null) {
            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            boolean deleteSuccess = monthlyRentBillBUS.delete(monthlyRentBill);
            if (deleteSuccess) {
                monthlyRentBillObservableList.remove(monthlyRentBill);
                table__P3__1.refresh();
                refreshFormMonthlyRentBill();
            } else {
                System.err.println("Không thể xóa phiếu thu từ cơ sở dữ liệu.");
            }
        }
    }


    // Dich Vu

    @FXML
    private TableColumn<Service, String> madv = new TableColumn<>();

    @FXML
    private TableColumn<Service, String> tenDV;

    @FXML
    private TableColumn<Service, Double> giaDV;

    @FXML
    private TableColumn<Service, String> donVi;

    @FXML
    private TableColumn<Service, String> loai;

    @FXML
    private TableView<Service> table__P4__11 = new TableView<>();

    @FXML
    private ComboBox<String> fill_type;

    @FXML
    private TextField TxtField__P4__search1;

    @FXML
    private ComboBox<String> comboBox__P4__11;

    @FXML
    private ComboBox<String> comboBox__P4__21;

    @FXML
    private TextField TxtField__P4__11;

    @FXML
    private TextField TxtField__P4__31;

    @FXML
    private TextField TxtField__P4__51;

    @FXML
    private TextField TxtField__P4__61;

    private ObservableList<Service> ServiceList;
    private Service servicedelete;

    public void initService() {
        ServiceList = FXCollections.observableArrayList();
        ServiceBUS serviceBUS = new ServiceBUS();
        ArrayList<Service> services = serviceBUS.getAll();
        ServiceList.addAll(services);
        ObservableList<Service> observableList = FXCollections.observableArrayList(ServiceList);
        madv.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        tenDV.setCellValueFactory(new PropertyValueFactory<>("name"));
        giaDV.setCellValueFactory(new PropertyValueFactory<>("pricePerUnit"));
        donVi.setCellValueFactory(new PropertyValueFactory<>("unit"));
        loai.setCellValueFactory(new PropertyValueFactory<>("type"));
        table__P4__11.setItems(observableList);
        handleService();
    }

    public void showcomboboxService() {
        Platform.runLater(() -> {
            if (fill_type != null) {
                ObservableList<String> genders = FXCollections.observableArrayList(

                        "mobile",
                        "fixed");
                fill_type.setItems(genders);
            } else {
                System.err.println("comboBox__P1__1 is null. Check FXML and controller connection.");
            }
        });
    }

    public void handleService() {
        table__P4__11.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Service selectedRow = table__P4__11.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    TxtField__P4__11.setText(selectedRow.getServiceID());
                    TxtField__P4__31.setText(selectedRow.getName());
                    TxtField__P4__51.setText(String.valueOf(selectedRow.getPricePerUnit()));
                    TxtField__P4__61.setText(selectedRow.getUnit());
                    String type = selectedRow.getType();
                    if (type != null && !type.isEmpty()) {
                        fill_type.setValue(type);
                    } else {
                        fill_type.getSelectionModel().clearSelection();
                    }
                    servicedelete = selectedRow; // Lưu lại hàng đã chọn
                }
            }
        });
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleAddService() {
        String serviceID = TxtField__P4__11.getText();
        String name = TxtField__P4__31.getText();
        Double pricePerUnit = Double.parseDouble(TxtField__P4__51.getText());
        String unit = TxtField__P4__61.getText();
        String type = fill_type.getValue();

        if (serviceID.isEmpty() || name.isEmpty() || unit.isEmpty() || type == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }
        Service newService = new Service(serviceID, name, pricePerUnit, unit, type);
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean isSuccess = serviceBUS.add(newService);

        if (isSuccess) {
            showAlert("Thành Công", "Thêm Thành Công.", AlertType.CONFIRMATION);
            initService();
        } else {
            showAlert("Lỗi", "Không Thể Thêm.", AlertType.ERROR);
        }
    }

    public void confirmAndDelete(Object itemToDelete, String confirmMessage, Runnable onDelete) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác Nhận Xóa");
        alert.setHeaderText(confirmMessage);
        alert.setContentText("Hành động này không thể hoàn tác.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                onDelete.run();
            } else {
                System.out.println("Hủy bỏ xóa.");
            }
        });
    }

    public void handleDeleteService() {
        if (servicedelete == null) {
            showAlert("Lỗi", "Không có dịch vụ nào chọn để xóa", AlertType.ERROR);
            return;
        }
        confirmAndDelete(servicedelete, "Bạn có chắc chắn muốn xóa dịch vụ này không?", () -> {
            ServiceBUS ServiceBUS = new ServiceBUS();
            boolean deleteResult = ServiceBUS.delete(servicedelete);
            if (deleteResult) {
                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
                initService();
            } else {
                showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
            }
        });
    }

    public void handleUpdateService() {
        if (servicedelete == null) {
            showAlert("Lỗi", "Không có dịch vụ nào chọn để cập nhật", AlertType.ERROR);
            return;
        }
        String newName = TxtField__P4__31.getText();
        Double newPricePerUnit = Double.parseDouble(TxtField__P4__51.getText());
        String newUnit = TxtField__P4__61.getText();
        String newType = fill_type.getValue();

        if (newName.isEmpty() || newUnit.isEmpty() || newType == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin mới", AlertType.ERROR);
            return;
        }
        servicedelete.setName(newName);
        servicedelete.setPricePerUnit(newPricePerUnit);
        servicedelete.setUnit(newUnit);
        servicedelete.setType(newType);
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean updateResult = serviceBUS.update(servicedelete);

        if (updateResult) {
            showAlert("Thành Công", "Cập nhật thông tin thành công", AlertType.CONFIRMATION);
            initService();
        } else {
            showAlert("Thất Bại", "Không thể cập nhật thông tin", AlertType.ERROR);
        }
    }

    // Phiếu Dichj Vụ

    @FXML
    private TableColumn<ServiceTicket, String> maPDV;

    @FXML
    private TableColumn<ServiceTicket, String> maPhieuThu;

    @FXML
    private TableColumn<ServiceTicket, String> maDichVu;

    @FXML
    private TableColumn<ServiceTicket, Double> soLuong;

    @FXML
    private TableColumn<ServiceTicket, Double> thanhTien;

    @FXML
    private TableColumn<ServiceTicket, LocalDate> ngayTaoPhieu;

    @FXML
    private TableColumn<ServiceTicket, String> ghiChu;

    @FXML
    private TextField maPhieuDVField;

    @FXML
    private TextField maPhieuThuField;

    @FXML
    private TextField maDichVuField;

    @FXML
    private TextField soLuongField;

    @FXML
    private TextField thanhTien1Field;

    @FXML
    private DatePicker ngayGhiPicker;

    @FXML
    private TextArea ghiChuArea;

    @FXML
    private TableView<ServiceTicket> table__sericetiket;

    private ObservableList<ServiceTicket> serviceTicketslist;
    private ServiceTicket serviceTicket;
    private ServiceTicket serviceTicketdelete;

    public void initServiceTicket() {
        serviceTicketslist = FXCollections.observableArrayList();
        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        ArrayList<ServiceTicket> serviceTickets = serviceTicketBUS.getAll();
        serviceTicketslist.addAll(serviceTickets);
        ObservableList<ServiceTicket> observableList = FXCollections.observableArrayList(serviceTicketslist);
        maPDV.setCellValueFactory(new PropertyValueFactory<>("serviceTicketID"));
        maPhieuThu.setCellValueFactory(new PropertyValueFactory<>("monthlyRentBillID"));
        maDichVu.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        soLuong.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        thanhTien.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        ngayTaoPhieu.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ghiChu.setCellValueFactory(new PropertyValueFactory<>("Note"));
        table__sericetiket.setItems(observableList);
        handleServiceTicket();

    }

    public void handleServiceTicket() {
        table__sericetiket.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ServiceTicket selectedRow = table__sericetiket.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                  System.out.println(selectedRow.getTotalAmount());
                    maPhieuDVField.setText(selectedRow.getServiceTicketID());
                    maPhieuThuField.setText(selectedRow.getMonthlyRentBillID());
                    maDichVuField.setText(selectedRow.getServiceID());
                    soLuongField.setText(String.valueOf(selectedRow.getQuantity()));
                    thanhTien1Field.setText(String.valueOf(selectedRow.getTotalAmount()));
                    ngayGhiPicker.setValue(selectedRow.getDate());
                    ghiChuArea.setText(selectedRow.getNote());
                    serviceTicketdelete = selectedRow;
                }
            }
        });
    }

    public void handleAddServiceTicket() {
        String maPDV = maPhieuDVField.getText();
        String maPhieuThu = maPhieuThuField.getText();
        String maDichVu = maDichVuField.getText();
        Double soLuong = Double.parseDouble(soLuongField.getText());
        Double thanhTien = Double.parseDouble(thanhTien1Field.getText());
        LocalDate ngayGhi = ngayGhiPicker.getValue();
        String ghiChu = ghiChuArea.getText();

        if (maPDV.isEmpty() || maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        // Tạo đối tượng ServiceTicket mới
        ServiceTicket newTicket = new ServiceTicket(maPDV, maPhieuThu, maDichVu, soLuong, thanhTien, ngayGhi, ghiChu);

        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        boolean addResult = serviceTicketBUS.add(newTicket);
        if (addResult) {
            showAlert("Thành Công", "Thêm Phiếu Dịch Vụ Thành Công", AlertType.CONFIRMATION);
            initServiceTicket(); // Load lại danh sách sau khi thêm
        } else {
            showAlert("Thất Bại", "Không Thể Thêm Phiếu Dịch Vụ", AlertType.ERROR);
        }
    }

    public void handleDeleteServiceTicket() {
        if (serviceTicketdelete == null) {
            showAlert("Lỗi", "Không có phiếu dịch vụ nào được chọn để xóa", AlertType.ERROR);
            return;
        }

        confirmAndDelete(serviceTicketdelete, "Bạn có chắc chắn muốn xóa phiếu dịch vụ này không?", () -> {
            ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
            boolean deleteResult = serviceTicketBUS.delete(serviceTicketdelete);
            if (deleteResult) {
                showAlert("Thành Công", "Xóa Phiếu Dịch Vụ Thành Công", AlertType.CONFIRMATION);
                initServiceTicket();
            } else {
                showAlert("Thất Bại", "Không Thể Xóa Phiếu Dịch Vụ", AlertType.ERROR);
            }
        });
    }

    public void handleUpdateServiceTicket() {
        if (serviceTicketdelete == null) {
            showAlert("Lỗi", "Không có phiếu dịch vụ nào được chọn để sửa", AlertType.ERROR);
            return;
        }

        String maPDV = maPhieuDVField.getText();
        String maDichVu = maPhieuThuField.getText();

        String maPhieuThu = maDichVuField.getText();
        Double soLuong = Double.parseDouble(soLuongField.getText());
        Double thanhTien = Double.parseDouble(thanhTien1Field.getText());
        LocalDate ngayGhi = ngayGhiPicker.getValue();
        String ghiChu = ghiChuArea.getText();

        if (maPDV.isEmpty() || maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        System.out.println(maDichVu + maPhieuThu + maDichVu);

        serviceTicketdelete.setServiceTicketID(maPDV);
        serviceTicketdelete.setMonthlyRentBillID(maPhieuThu);
        serviceTicketdelete.setServiceID(maDichVu);
        serviceTicketdelete.setQuantity(soLuong);
        serviceTicketdelete.setTotalAmount(thanhTien);
        serviceTicketdelete.setDate(ngayGhi);
        serviceTicketdelete.setNote(ghiChu);

        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        boolean updateResult = serviceTicketBUS.update(serviceTicketdelete);
        if (updateResult) {
            showAlert("Thành Công", "Cập Nhật Phiếu Dịch Vụ Thành Công", AlertType.CONFIRMATION);
            initServiceTicket();
        } else {
            showAlert("Thất Bại", "Không Thể Cập Nhật Phiếu Dịch Vụ", AlertType.ERROR);
        }
    }
    // Phạt

    @FXML
    private TableView<Violation> tableViolation;

    @FXML
    private TableColumn<Violation, String> maphat;

    @FXML
    private TableColumn<Violation, String> vipham;

    @FXML
    private TableColumn<Violation, Double> tienphat;
    @FXML
    private TextField maPhatField;
    @FXML
    private TextField viphamField;
    @FXML
    private TextField tienPhatField;
    private ObservableList<Violation> violationslist;
    private Violation violation;
    private Violation violationdelete;

    public void initViolation() {
        violationslist = FXCollections.observableArrayList();
        ViolationBUS violationBUS = new ViolationBUS();
        ArrayList<Violation> Violations = violationBUS.getAll();
        violationslist.addAll(Violations);
        ObservableList<Violation> observableList = FXCollections.observableArrayList(violationslist);
        maphat.setCellValueFactory(new PropertyValueFactory<>("violationID"));
        vipham.setCellValueFactory(new PropertyValueFactory<>("name"));

        tienphat.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableViolation.setItems(observableList);
        handleViolation();

    }

    public void handleViolation() {
        tableViolation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Violation selectedRow = tableViolation.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    maPhatField.setText(selectedRow.getViolationID());
                    viphamField.setText(selectedRow.getName());
                    tienPhatField.setText(String.valueOf(selectedRow.getPrice()));
                    violationdelete = selectedRow;
                }
            }
        });
    }

    public void handleDeleteViolation() {
        if (violationdelete == null) {
            showAlert("Lỗi", "Không có vi phạm nào được chọn để xóa", AlertType.ERROR);
            return;
        }

        ViolationBUS violationBUS = new ViolationBUS();
        boolean deleteResult = violationBUS.delete(violationdelete);

        if (deleteResult) {
            showAlert("Thành Công", "Xóa vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();

            maPhatField.clear();
            viphamField.clear();
            tienPhatField.clear();
            violationdelete = null;
        } else {
            showAlert("Thất Bại", "Không thể xóa vi phạm", AlertType.ERROR);
        }
    }

    public void handleUpdateViolation() {
        if (violationdelete == null) {
            showAlert("Lỗi", "Không có vi phạm nào được chọn để cập nhật", AlertType.ERROR);
            return;
        }

        String newName = viphamField.getText();
        Double newPrice = Double.parseDouble(tienPhatField.getText());

        if (newName.isEmpty()) {
            showAlert("Lỗi", "Tên vi phạm không được để trống", AlertType.ERROR);
            return;
        }

        violationdelete.setName(newName);
        violationdelete.setPrice(newPrice);

        ViolationBUS violationBUS = new ViolationBUS();
        boolean updateResult = violationBUS.update(violationdelete);

        if (updateResult) {
            showAlert("Thành Công", "Cập nhật vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();
        } else {
            showAlert("Thất Bại", "Không thể cập nhật vi phạm", AlertType.ERROR);
        }
    }

    public void handleAddViolation() {

        String newViolationID = maPhatField.getText();
        String newName = viphamField.getText();
        Double newPrice = Double.parseDouble(tienPhatField.getText());

        if (newName.isEmpty()) {
            showAlert("Lỗi", "Tên vi phạm không được để trống", AlertType.ERROR);
            return;
        }

        Violation newViolation = new Violation(newViolationID, newName, newPrice);

        ViolationBUS violationBUS = new ViolationBUS();
        boolean insertResult = violationBUS.add(newViolation);

        if (insertResult) {
            showAlert("Thành Công", "Thêm vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();

            maPhatField.clear();
            viphamField.clear();
            tienPhatField.clear();
        } else {
            showAlert("Thất Bại", "Không thể thêm vi phạm", AlertType.ERROR);
        }
    }

    // Phiếu Phạt

    @FXML
    private TableView<ViolationTicket> tableviolationticket;
    @FXML
    private TableColumn<ViolationTicket, String> mxPP;
    @FXML
    private TableColumn<ViolationTicket, String> mxPThu;
    @FXML
    private TableColumn<ViolationTicket, Double> mxThanhTien;
    @FXML
    private TableColumn<ViolationTicket, LocalDate> mxNgayghi;
    @FXML
    private TableColumn<ViolationTicket, String> mxghichu;
    @FXML
    private TextField maPPField;
    @FXML
    private TextField maPThuField;

    @FXML
    private TextField thanhTienField;

    @FXML
    private DatePicker ngayGhiPPField;
    @FXML
    private TextArea ghiChuPPField;

    private ObservableList<ViolationTicket> violationsList;
    private ViolationTicket violationTicketdelete;

    public void initViolationTicket() {
        violationsList = FXCollections.observableArrayList();
        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        ArrayList<ViolationTicket> violationTickets = violationTicketBUS.getAll();
        violationsList.addAll(violationTickets);

        ObservableList<ViolationTicket> observableList = FXCollections.observableArrayList(violationsList);
        mxPP.setCellValueFactory(new PropertyValueFactory<>("violationID"));
        mxPThu.setCellValueFactory(new PropertyValueFactory<>("monthlyRentBillID"));
        mxThanhTien.setCellValueFactory(new PropertyValueFactory<>("price"));
        mxNgayghi.setCellValueFactory(new PropertyValueFactory<>("date"));
        mxghichu.setCellValueFactory(new PropertyValueFactory<>("note"));
        tableviolationticket.setItems(observableList);
        handleViolationTicket();
    }

    public void handleViolationTicket() {
        tableviolationticket.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ViolationTicket selectedRow = tableviolationticket.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    maPPField.setText(selectedRow.getViolationID());
                    maPThuField.setText(selectedRow.getMonthlyRentBillID());
                    thanhTienField.setText(String.valueOf(selectedRow.getPrice()));
                    ngayGhiPPField.setValue(selectedRow.getDate());
                    ghiChuPPField.setText(selectedRow.getNote());
                    violationTicketdelete = selectedRow;
                }
            }
        });
    }

    public void handleDeleteViolationTicket() {
        ViolationTicket selectedRow = tableviolationticket.getSelectionModel().getSelectedItem();
        if (selectedRow == null) {
            showAlert("Lỗi", "Không có phiếu vi phạm nào được chọn để xóa", Alert.AlertType.ERROR);
            return;
        }

        boolean deleteResult = ViolationTicketBUS.getInstance().delete(selectedRow);

        if (deleteResult) {
            showAlert("Thành Công", "Xóa phiếu vi phạm thành công", Alert.AlertType.CONFIRMATION);
            initViolationTicket();

        } else {
            showAlert("Thất Bại", "Không thể xóa phiếu vi phạm", Alert.AlertType.ERROR);
        }
    }

    public void handleAddViolationTicket() {
        String maPDV = maPhieuDVField.getText();
    String maPhieuThu = maPhieuThuField.getText();
    String maDichVu = maDichVuField.getText();
    LocalDate ngayGhi = ngayGhiPicker.getValue();
    String ghiChu = ghiChuArea.getText();

  
        // Kiểm tra và xử lý số lượng và thành tiền
        Double soLuong = Double.parseDouble(soLuongField.getText());
        Double thanhTien = Double.parseDouble(thanhTienField.getText());

        if (maPDV.isEmpty() || maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }
        ServiceTicket newServiceTicket = new ServiceTicket();
        newServiceTicket.setServiceTicketID(maPDV);
        newServiceTicket.setMonthlyRentBillID(maPhieuThu);
        newServiceTicket.setServiceID(maDichVu);
        newServiceTicket.setQuantity(soLuong);
        newServiceTicket.setTotalAmount(thanhTien);
        newServiceTicket.setDate(ngayGhi);
        newServiceTicket.setNote(ghiChu);

        // Thêm phiếu dịch vụ mới vào cơ sở dữ liệu
        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        boolean addResult = serviceTicketBUS.add(newServiceTicket);

        if (addResult) {
            showAlert("Thành Công", "Thêm vi phạm thành công", Alert.AlertType.CONFIRMATION);
            initViolationTicket();

        } else {
            showAlert("Thất Bại", "Không thể thêm vi phạm", Alert.AlertType.ERROR);
        }
    
    }

    public void handleUpdateViolationTicket() {
        String newViolationID = maPPField.getText();
        String newMonthlyRentBillID = maPThuField.getText();
        Double newPrice = Double.parseDouble(thanhTienField.getText());
        LocalDate newDate = ngayGhiPPField.getValue();
        String newNote = ghiChuPPField.getText();
        if (newViolationID.isEmpty() || newMonthlyRentBillID.isEmpty() || newPrice <= 0 || newDate == null
                || newNote.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin và đúng định dạng", Alert.AlertType.ERROR);
            return;
        }
        ViolationTicket newViolation = new ViolationTicket(newViolationID, newMonthlyRentBillID, newPrice,
                newDate, newNote);

        ViolationTicketBUS violationBUS = new ViolationTicketBUS();
        boolean insertResult = violationBUS.update(newViolation);

        if (insertResult) {
            showAlert("Thành Công", "Sửa vi phạm thành công", Alert.AlertType.CONFIRMATION);
            initViolationTicket();

        } else {
            showAlert("Thất Bại", "Không thể sửa vi phạm", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private TableColumn<Furniture, String> ColumnP5__1 = new TableColumn<>();

    @FXML
    private TableColumn<Furniture, String> ColumnP5__2 = new TableColumn<>();

    @FXML
    private TableColumn<Furniture, String> ColumnP5__3 = new TableColumn<>();

    @FXML
    private TableColumn<Furniture, String> ColumnP5__4 = new TableColumn<>();

    @FXML
    private TableColumn<Furniture, Double> ColumnP5__5 = new TableColumn<>();

    @FXML
    private TextField TxtField__P5__1 = new TextField();

    @FXML
    private TextField TxtField__P5__2 = new TextField();

    @FXML
    private TextField TxtField__P5__3 = new TextField();

    @FXML
    private TextField TxtField__P5__4 = new TextField();

    @FXML
    private TextField TxtField__P5__search = new TextField();

    @FXML
    private Button bnt__P5__add;

    @FXML
    private Button bnt__P5__delete;

    @FXML
    private Button bnt__P5__update;

    @FXML
    private ComboBox<String> comboBox__P5__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P5__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P5__3 = new ComboBox<>();

    @FXML
    private TableView<Furniture> table__P5__1 = new TableView<>();

    private ObservableList<Furniture> furnitureObservableList;

    public ObservableList<Furniture> getFurnitureObservableList(){
        ObservableList<Furniture> furnitureObservableList1 = FXCollections.observableArrayList();
        FurnitureBUS furnitureBUS = new FurnitureBUS();
        List<Furniture> furnitureList = furnitureBUS.getAll();
        furnitureObservableList1.addAll(furnitureList);
        return furnitureObservableList1;
    }

    public void initFurniture(){
        ColumnP5__1.setCellValueFactory(new PropertyValueFactory<>("furnitureID"));
        ColumnP5__2.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        ColumnP5__3.setCellValueFactory(new PropertyValueFactory<>("nameFurniture"));
        ColumnP5__4.setCellValueFactory(new PropertyValueFactory<>("conditionFurniture"));
        ColumnP5__5.setCellValueFactory(new PropertyValueFactory<>("price"));

        furnitureObservableList = getFurnitureObservableList();
        table__P5__1.setItems(furnitureObservableList);
    }

    public void refreshFormFurniture(){
        TxtField__P5__1.setText("");
        TxtField__P5__2.setText("");
        TxtField__P5__3.setText("");
        comboBox__P5__3.setValue(null);
        TxtField__P5__4.setText("");
    }

    @FXML
    void suaNoiThat(ActionEvent event) {

    }

    @FXML
    void themNoiThat(ActionEvent event) {
        try{
            Furniture furniture = new Furniture();
            furniture.setFurnitureID(TxtField__P5__1.getText());
            furniture.setApartmentID(TxtField__P5__2.getText());
            furniture.setNameFurniture(TxtField__P5__3.getText());
            furniture.setPrice(Double.parseDouble(TxtField__P5__4.getText()));
            furniture.setCondition(comboBox__P5__3.getSelectionModel().getSelectedItem());

            FurnitureBUS furnitureBUS = new FurnitureBUS();
            furnitureBUS.add(furniture);

            furnitureObservableList.add(furniture);
            refreshFormFurniture();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void xoaNoiThat(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboBox__P1__3.getItems().addAll("Cơ bản", "Tiện nghi", "Cao cấp");
            comboBox__P1__3.setPromptText("");
            initApartment();
            comboBox__P2__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2__3.setPromptText("");
            initTenant();
            initCohabitant();
            comboBox__P2_1__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2_1__3.setPromptText("");

            comboBox__P3__3.getItems().addAll("Đã trả", "Chưa trả", "Quá hạn");
            comboBox__P3__3.setPromptText("");
            initMonthlyRentBill();

            comboBox__P5__3.getItems().addAll("Mới", "Cũ");
            comboBox__P5__3.setPromptText("");
            initFurniture();
            //Chạy page 0
            totalOfBuldings();
            updatePieChart();
            drawBarChart();

            // Dich Vu
            initService();
            showcomboboxService();

            // Phieu Dich Vu
            initServiceTicket();

            // phat
            initViolation();

            // Phieu Phat

            initViolationTicket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}