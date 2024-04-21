package com.example.managingbuildingjava;

import BUS.FinancialReportBUS;
import BUS.MonthlyRentBillBUS;
import DTO.FinancialReport;
import DTO.MonthlyRentBill;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import BUS.BuildingBUS;
import BUS.BuildingManagerBUS;
import DTO.Building;

public class BossController implements Initializable {

    public static BossController getInstance() {
        return new BossController();
    }

    private String ID;

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    private volatile boolean stop = false;
    private volatile Thread thread;
    private String buildingId;
    private ObservableList<Building> buildingsList;
    private Building selectedBuildingToDelete;

    private ObservableList<DTO.BuildingManager> buildingManagersList;
    private BuildingManager selectedBuildingManagerToDelete;

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
    private Label time;

    @FXML
    private TableView<Building> table__view;

    @FXML
    private TableView<DTO.BuildingManager> table__view2;

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

    @FXML
    private TableColumn<DTO.BuildingManager, String> buildingManagerIdColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> buildingIdColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> lastNameColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> firstNameColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> phoneNumberColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, LocalDate> dobColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> genderColumn;
    @FXML
    private TableColumn<DTO.BuildingManager, String> positionColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, String> citizenIdentityCardColumn;

    @FXML
    private TableColumn<DTO.BuildingManager, Float> salaryColumn;

    @FXML
    private TextField TxtField__b1;

    @FXML

    private TextField TxtField__b2;
    @FXML

    private TextField TxtField__b3;
    @FXML

    private TextField TxtField__b4;
    @FXML

    private TextField TxtField__b5;
    @FXML

    private TextField TxtField__b6;
    @FXML

    private TextField TxtField__b7;
    @FXML
    private DatePicker datePickerDOB;

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

    @FXML
    private ComboBox<String> fruitCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildingsList = FXCollections.observableArrayList();
        buildingManagersList = FXCollections.observableArrayList();

        loadBuildingManagers();
        loadBuilding();
        Platform.runLater(() -> {
            if (fruitCombo != null) {
                ObservableList<String> fruits = FXCollections.observableArrayList(
                        "Nam",
                        "Nữ");
                fruitCombo.setItems(fruits);
            } else {
                System.err.println("fruitCombo is null. Check FXML and controller connection.");
            }
        });

    }

    public void loadBuilding() {
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
            handleBuilding();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadBuildingManagers() {
        try {
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            ArrayList<DTO.BuildingManager> buildingManagers = buildingManagerBUS.getAll();
            buildingManagersList.addAll(buildingManagers);
            ObservableList<DTO.BuildingManager> building = FXCollections.observableArrayList(buildingManagersList);
            buildingManagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("buildingManagerId"));
            buildingIdColumn.setCellValueFactory(new PropertyValueFactory<>("buildingId"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            citizenIdentityCardColumn.setCellValueFactory(new PropertyValueFactory<>("citizenIdentityCard"));
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
            positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
            table__view2.setItems(building);
            handbuildingmanager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBuilding() {
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

    public void handbuildingmanager() {
        table__view2.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                DTO.BuildingManager selectedRow = table__view2.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {

                    TxtField__b1.setText(selectedRow.getBuildingManagerId());
                    TxtField__b2.setText(selectedRow.getBuildingId());
                    TxtField__b3.setText(selectedRow.getFirstName());
                    TxtField__b4.setText(selectedRow.getLastName());
                    TxtField__b5.setText(selectedRow.getPhoneNumber());
                    TxtField__b6.setText(selectedRow.getCitizenIdentityCard());
                    TxtField__b7.setText(String.valueOf(selectedRow.getSalary()));
                    // Chuyển đổi sang String nếu cần
                    datePickerDOB.setValue(selectedRow.getDob());

                    // Xác định giá trị cho ComboBox dựa trên giới tính
                    String genderValue = selectedRow.getGender();
                    if (genderValue != null && !genderValue.isEmpty()) {
                        fruitCombo.setValue(genderValue);
                    } else {
                        fruitCombo.getSelectionModel().clearSelection();
                    }

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
        String buildingId = TxtField__P1__1.getText().trim();
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
        Building editedBuilding = new Building();
        editedBuilding.setBuildingId(buildingId);
        editedBuilding.setNameBuilding(nameBuilding);
        editedBuilding.setCity_Building(city_Building);
        editedBuilding.setDistrict_Building(district_Building);
        editedBuilding.setAddress_Building(address_Building);
        editedBuilding.setNumberOfApartment_Building(numberOfApartment_Building);

        // Gọi phương thức cập nhật từ BuildingBUS hoặc BuildingDAO
        BuildingBUS buildingBUS = new BuildingBUS(); // Đây là một ví dụ, bạn cần sửa lại theo cấu trúc thực tế của ứng
                                                     // dụng
        boolean updateResult = buildingBUS.update(editedBuilding);

        if (updateResult) {
            showAlert("Thành Công", "Cập nhật thành công", AlertType.CONFIRMATION);
            resetTextfield();
            updateBuildingList();
        } else {
            showAlert("Thất Bại", "Không thể cập nhật", AlertType.ERROR);
        }
    }

    public void handleAddpage2() {
        String buildingManagerId = TxtField__b1.getText();
        String buildingId = TxtField__b2.getText();
        String lastName = TxtField__b3.getText();
        String firstName = TxtField__b4.getText();
        String phoneNumber = TxtField__b5.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = fruitCombo.getValue();
        String citizenIdentityCard = TxtField__b6.getText();
        Double salary = Double.parseDouble(TxtField__b7.getText());

        if (buildingManagerId.isEmpty() || buildingId.isEmpty() || lastName.isEmpty() || firstName.isEmpty() ||
                phoneNumber.isEmpty() || dob == null || gender.isEmpty() || citizenIdentityCard.isEmpty()
                || salary.isNaN()) {

            System.out.println("Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        DTO.BuildingManager newBuildingManager = new DTO.BuildingManager();
        newBuildingManager.setBuildingManagerId(buildingManagerId);
        newBuildingManager.setBuildingId(buildingId);
        newBuildingManager.setLastName(lastName);
        newBuildingManager.setFirstName(firstName);
        newBuildingManager.setPhoneNumber(phoneNumber);
        newBuildingManager.setDob(dob);
        newBuildingManager.setGender(gender);
        newBuildingManager.setCitizenIdentityCard(citizenIdentityCard);
        newBuildingManager.setSalary(salary);
        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        boolean insertResult = buildingManagerBUS.insert(newBuildingManager);

        if (insertResult) {
            showAlert("Thành Công", "Đã Thêm Thành Công", AlertType.CONFIRMATION);
            buildingManagersList.clear();
            ArrayList<DTO.BuildingManager> buildings = buildingManagerBUS.getAll();
            buildingManagersList.addAll(buildings);
            ObservableList<DTO.BuildingManager> observableBuildingList = FXCollections.observableArrayList(
                    buildingManagersList);
            table__view2.setItems(observableBuildingList);
            clearInputFields();

        } else {
            showAlert("Lỗi", "Mã đã tồn tại trong cơ sở dữ liệu.", AlertType.ERROR);
        }

        // Xóa nội dung của các trường nhập liệu sau khi thêm
    }

    private void clearInputFields() {
        TxtField__b1.clear();
        TxtField__b2.clear();
        TxtField__b3.clear();
        TxtField__b4.clear();
        TxtField__b5.clear();
        TxtField__b6.clear();
        TxtField__b7.clear();
        datePickerDOB.setValue(null); // Đặt DatePicker về giá trị mặc định
        fruitCombo.getSelectionModel().clearSelection(); // Xóa lựa chọn trong ComboBox
    }

}
