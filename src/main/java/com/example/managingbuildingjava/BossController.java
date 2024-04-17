package com.example.managingbuildingjava;

import BUS.FinancialReportBUS;
import BUS.MonthlyRentBillBUS;
import DTO.FinancialReport;
import DTO.MonthlyRentBill;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import BUS.BuildingBUS;
import DTO.Building;

public class BossController implements Initializable {

    private volatile boolean stop = false;
    private volatile Thread thread;
    private String buildingId;
    private ObservableList<Building> buildingsList;
    private Building selectedBuildingToDelete;

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    int dem = 0;
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
        loadPage("Boss-view-Page1");
    }

    @FXML
    private void page2(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page2");
    }

    @FXML
    private void page3(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page3");
    }

    @FXML
    private void page4(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page4");
    }

    @FXML
    private void page5(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page5");
    }

    @FXML
    private void page6(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page6");
    }

    @FXML
    private void page7(MouseEvent event) throws IOException {
        loadPage("Boss-view-Page7");
    }

    @FXML
    private Label time;
    @FXML
    private TableView<Building> table__view;

    @FXML
    private TableColumn<Building, String> maToaNhaColumn;

    @FXML
    private TableColumn<Building, String> tenColumn;

    @FXML
    private TableColumn<Building, String> thanhPhoColumn;

    @FXML
    private TableColumn<Building, String> quanColumn;

    @FXML
    private TableColumn<Building, String> diaChiColumn;

    @FXML
    private TableColumn<Building, Integer> soLuongCanHoColumn;
    @FXML
    private TextField TxtField__P1__1;
    @FXML
    private TextField TxtField__P1__2;
    @FXML
    private TextField TxtField__P1__3;
    @FXML
    private TextField TxtField__P1__4;
    @FXML
    private TextField TxtField__P1__5;
    @FXML
    private TextField TxtField__P1__6;

    private void loadPage(String page) throws IOException {
        stop = true;
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        bp.setCenter(root);
    }
    @FXML
    private Label monthlyRevenueLabel;
    @FXML
    private PieChart numberOfStatusLabel;
    @FXML
    private BarChart barChartOfMonthlyOpex;
    private ObservableList<FinancialReport> financialReportsList;
    private ObservableList<FinancialReport> monthlyRentBillsList;

    public void updateMonthlyRevenueLabel() {
        if (monthlyRevenueLabel == null) {
            return;
        }
        try {
            financialReportsList = FXCollections.observableArrayList();

            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            financialReportBUS.setMonthlyRevenueLabel(monthlyRevenueLabel);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updateNumberOfStatus() {
        if (numberOfStatusLabel == null) {
            return;
        }
        try {
            monthlyRentBillsList = FXCollections.observableArrayList();
            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            monthlyRentBillBUS.setMonthlyRentBillsLabel(numberOfStatusLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void drawLineChartOfMonthlyOpex() {
        if (barChartOfMonthlyOpex == null) {
            return;
        }

        try {
            financialReportsList = FXCollections.observableArrayList();
            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            financialReportBUS.setMonthlyOpexLabel(barChartOfMonthlyOpex);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildingsList = FXCollections.observableArrayList();
        try {
            BuildingBUS buildingBUS = new BuildingBUS();
            ArrayList<Building> buildings = buildingBUS.getAll();
            buildingsList.addAll(buildings);

            ObservableList<Building> observableBuildingList = FXCollections.observableArrayList(buildingsList);
            maToaNhaColumn.setCellValueFactory(new PropertyValueFactory<>("buildingId"));
            tenColumn.setCellValueFactory(new PropertyValueFactory<>("nameBuilding"));
            thanhPhoColumn.setCellValueFactory(new PropertyValueFactory<>("city_Building"));
            quanColumn.setCellValueFactory(new PropertyValueFactory<>("district_Building"));
            diaChiColumn.setCellValueFactory(new PropertyValueFactory<>("address_Building"));
            soLuongCanHoColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfApartment_Building"));
            table__view.setItems(observableBuildingList);
            handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handle() {
        table__view.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Building selectedRow = table__view.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    System.out.println("Dữ liệu của dòng được chọn:");
                    System.out.println("Mã Tòa Nhà: " + selectedRow.getBuildingId());
                    System.out.println("Tên: " + selectedRow.getNameBuilding());
                    System.out.println("Thành Phố: " + selectedRow.getCity_Building());
                    System.out.println("Quận: " + selectedRow.getDistrict_Building());
                    System.out.println("Địa Chỉ: " + selectedRow.getAddress_Building());
                    System.out.println("Số Lượng Căn Hộ: " + selectedRow.getNumberOfApartment_Building());
                    TxtField__P1__1.setText(selectedRow.getBuildingId());
                    TxtField__P1__2.setText(selectedRow.getNameBuilding());
                    TxtField__P1__3.setText(selectedRow.getCity_Building());
                    TxtField__P1__4.setText(selectedRow.getDistrict_Building());
                    TxtField__P1__5.setText(selectedRow.getAddress_Building());
                    int numberOfApartments = selectedRow.getNumberOfApartment_Building();
                    TxtField__P1__6.setText(String.valueOf(numberOfApartments));
                    selectedBuildingToDelete = selectedRow;
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

    private void updateBuildingList() {
        try {

            buildingsList.clear();
            BuildingBUS buildingBUS = new BuildingBUS();
            ArrayList<Building> buildings = buildingBUS.getAll();
            buildingsList.addAll(buildings);
            ObservableList<Building> observableBuildingList = FXCollections.observableArrayList(buildingsList);
            table__view.setItems(observableBuildingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleaddBuilding() {
        try {
            String buildingId = TxtField__P1__1.getText();
            String nameBuilding = TxtField__P1__2.getText();
            String city_Building = TxtField__P1__3.getText();
            String district_Building = TxtField__P1__4.getText();
            String address_Building = TxtField__P1__5.getText();
            int numberOfApartment_Building = Integer.parseInt(TxtField__P1__6.getText());

            if (buildingId.isEmpty() || nameBuilding.isEmpty() || city_Building.isEmpty() || district_Building.isEmpty()
                    || address_Building.isEmpty()) {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
                return;
            }

            if (numberOfApartment_Building <= 0) {
                showAlert("Lỗi", "Số lượng căn hộ phải là một số nguyên dương.", AlertType.ERROR);
                return;
            }
            Building newBuilding = new Building();
            newBuilding.setBuildingId(buildingId);
            newBuilding.setNameBuilding(nameBuilding);
            newBuilding.setCity_Building(city_Building);
            newBuilding.setDistrict_Building(district_Building);
            newBuilding.setAddress_Building(address_Building);
            newBuilding.setNumberOfApartment_Building(numberOfApartment_Building);

            BuildingBUS buildingBUS = new BuildingBUS();
            boolean insertResult = buildingBUS.insert(newBuilding);

            if (insertResult) {
                showAlert("Thành Công", "Đã Thêm Thành Công", AlertType.CONFIRMATION);
                updateBuildingList();

            } else {
                showAlert("Lỗi", "Mã đã tồn tại trong cơ sở dữ liệu.", AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Số lượng căn hộ phải là một số nguyên.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleDelete() {
        if (selectedBuildingToDelete == null) {
            System.out.println("Không có tòa nhà nào được chọn để xóa.");
            return;
        }
        BuildingBUS buildingBUS = new BuildingBUS();
        boolean deleteResult = buildingBUS.delete(selectedBuildingToDelete);

        if (deleteResult) {
            showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
        resetTextfield();
            updateBuildingList();
        } else {
            showAlert("Thật Bại", "Không Thế Xóa", AlertType.CONFIRMATION);

        }
    }

    public void resetTextfield() {
        TxtField__P1__1.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__3.setText("");
        TxtField__P1__4.setText("");
        TxtField__P1__5.setText("");
        TxtField__P1__6.setText("");
    }
       
    public void handleEdit() {
        // Lấy thông tin từ các TextField
        String buildingId = TxtField__P1__1.getText();
        String nameBuilding = TxtField__P1__2.getText();
        String city_Building = TxtField__P1__3.getText();
        String district_Building = TxtField__P1__4.getText();
        String address_Building = TxtField__P1__5.getText();
        int numberOfApartment_Building = 0;
        try {
            numberOfApartment_Building = Integer.parseInt(TxtField__P1__6.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số lượng căn hộ không hợp lệ.", AlertType.ERROR);
            return;
        }

        // Kiểm tra thông tin đã nhập đầy đủ chưa
        if (buildingId.isEmpty() || nameBuilding.isEmpty() || city_Building.isEmpty() ||
                district_Building.isEmpty() || address_Building.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin để cập nhật.", AlertType.ERROR);
            return;
        }

        // Tạo đối tượng Building mới
        Building editedBuilding = new Building();
        editedBuilding.setBuildingId(buildingId);
        editedBuilding.setNameBuilding(nameBuilding);
        editedBuilding.setCity_Building(city_Building);
        editedBuilding.setDistrict_Building(district_Building);
        editedBuilding.setAddress_Building(address_Building);
        editedBuilding.setNumberOfApartment_Building(numberOfApartment_Building);

        BuildingBUS buildingBUS = new BuildingBUS();
        boolean updateResult = buildingBUS.update(editedBuilding);

        if (updateResult) {
            showAlert("Thành Công", "Cập nhật thành công", AlertType.CONFIRMATION);
            resetTextfield(); 
            updateBuildingList(); 
        } else {
            showAlert("Thất Bại", "Không thể cập nhật", AlertType.ERROR);
        }
    }

}
