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
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.module.ModuleDescriptor.Exports;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

import BUS.BuildingBUS;
import BUS.BuildingManagerBUS;
import DTO.Building;
import DTO.BuildingManager;
import javafx.stage.Stage;

import javax.swing.*;

public class BossController implements Initializable {

    private static BossController instance;

    public static BossController getInstance() {
        if (instance == null) {
            instance = new BossController();
        }
        return instance;
    }

    private static String ID;

    public void setID(String ID) {
        BossController.ID = ID;
    }

    public String getID() {
        return ID;
    }

    private volatile boolean stop = false;
    private volatile Thread thread;
    private String buildingId;
    private ObservableList<Building> buildingsList;
    private Building selectedBuildingToDelete;
    private DTO.BuildingManager selectedBuildingToDelete1;
    private FinancialReport selecFinancialReport;
    private ObservableList<DTO.BuildingManager> buildingManagersList;
    private ObservableList<BuildingManager> buildingManagerList;
    private ObservableList<FinancialReport> financialReportsList;

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
    void loadHour(MouseEvent event) {
        TimeNow();
        drawLineChartOfMonthlyOpex();
        updateNumberOfStatus();
        updateMonthlyRevenueLabel();
    }

    @FXML
    private TableView<Building> table__view = new TableView<>();

    @FXML
    private TableView<DTO.BuildingManager> table__view2 = new TableView<>();

    @FXML
    private TableColumn<Building, String> maToaNhaColumn = new TableColumn<>();

    @FXML
    private TableColumn<Building, String> tenColumn = new TableColumn<>();

    @FXML
    private TableColumn<Building, String> thanhPhoColumn = new TableColumn<>();

    @FXML
    private TableColumn<Building, String> quanColumn = new TableColumn<>();

    @FXML
    private TableColumn<Building, String> diaChiColumn = new TableColumn<>();

    @FXML
    private TableColumn<Building, Integer> soLuongCanHoColumn = new TableColumn<>();
    @FXML
    private TextField TxtField__P1__1 = new TextField();

    @FXML
    private TextField TxtField__P1__search = new TextField();
    @FXML
    TextField seacrch_namepage1 = new TextField();

    @FXML
    private TextField TxtField__P1__2 = new TextField();
    @FXML
    private TextField TxtField__P1__3 = new TextField();
    @FXML
    private ComboBox<String> TxtField__P1__4 = new ComboBox<>();
    @FXML
    private TextField TxtField__P1__5 = new TextField();
    @FXML
    private TextField TxtField__P1__6 = new TextField();

    @FXML
    private TableColumn<DTO.BuildingManager, String> buildingManagerIdColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> buildingIdColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> lastNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> firstNameColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> phoneNumberColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, LocalDate> dobColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> genderColumn = new TableColumn<>();
    @FXML
    private TableColumn<DTO.BuildingManager, String> positionColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, String> citizenIdentityCardColumn = new TableColumn<>();

    @FXML
    private TableColumn<DTO.BuildingManager, Float> salaryColumn = new TableColumn<>();

    @FXML
    private TextField TxtField__b1 = new TextField();

    @FXML

    private TextField TxtField__b2 = new TextField();
    @FXML

    private TextField TxtField__b3 = new TextField();
    @FXML

    private TextField TxtField__b4 = new TextField();
    @FXML

    private TextField TxtField__b5 = new TextField();
    @FXML

    private TextField TxtField__b6 = new TextField();
    @FXML

    private TextField TxtField__b7 = new TextField();
    @FXML
    private DatePicker datePickerDOB = new DatePicker();

    @FXML
    private ComboBox<String> fruitCombo = new ComboBox<>();
    @FXML
    private ComboBox<String> box_dress = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P1__1 = new ComboBox<>();
    @FXML
    private ComboBox<String> comboBox__P1__2 = new ComboBox<>();
    @FXML
    private Label time, numberOfBuildings, monthlyRevenueLabel = new Label();
    @FXML
    private PieChart pieChart = new PieChart();

    @FXML
    private PieChart numberOfStatusLabel = new PieChart();
    @FXML
    private BarChart barChartOfMonthlyOpex;
    private BarChart<String, Number> barChart;

    // Page 3

    @FXML
    private TableView<FinancialReport> table__view3 = new TableView<>();
    @FXML
    private TableColumn<FinancialReport, String> maBaoCaoColumn =new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, String> maQuanLiColumn = new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, String> maToaNha1Column = new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, Date> ngayColumn = new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, Float> doanhThuColumn = new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, Float> monthlyOpexColumn = new TableColumn<>();
    @FXML
    private TableColumn<FinancialReport, Float> loiNhuanColumn = new TableColumn<>();

    @FXML
    private TextField TxtField__r1 = new TextField();
    @FXML
    private TextField TxtField__r2 = new TextField();
    @FXML
    private TextField TxtField__r3 = new TextField();
    @FXML
    private TextField TxtField__r4 = new TextField();
    @FXML
    private TextField TxtField__r5 = new TextField();
    @FXML
    private TextField TxtField__r6 = new TextField();
    @FXML
    private DatePicker Date_page3 = new DatePicker();
    @FXML
    private DatePicker date1 = new DatePicker();
    @FXML
    private DatePicker date2 = new DatePicker();

    private void loadPage(String page) throws IOException {
        stop = true;
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        bp.setCenter(root);
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
            ArrayList<Building> buildings = buildingBUS.getAll();

            int total = 0;

            for(Building building : buildings){
                total += building.getNumberOfApartment_Building();
            }
            numberOfBuildings.setText(String.valueOf(total));

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
        datePage0.setText(LocalDate.now().toString());
    }

    public void updateMonthlyRevenueLabel() {
        if (monthlyRevenueLabel == null) {
            return;
        }
        try {
            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();

            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Lọc danh sách financialReports để chỉ chọn ra các báo cáo tài chính có ngày
            // tương ứng với tháng hiện tại
            ArrayList<FinancialReport> currentMonthReports = new ArrayList<>();
            for (FinancialReport report : financialReports) {
                LocalDate reportDate = LocalDate.parse(report.getDate().toString());
                YearMonth reportYearMonth = YearMonth.from(reportDate);
                YearMonth currentYearMonth = YearMonth.from(currentDate);
                if (reportYearMonth.equals(currentYearMonth)) {
                    currentMonthReports.add(report);
                }
            }

            // Kiểm tra xem có báo cáo nào cho tháng hiện tại không
            if (!currentMonthReports.isEmpty()) {
                // Lấy giá trị monthlyRevenue của báo cáo đầu tiên trong danh sách và gán vào
                // monthlyRevenueLabel
                double monthlyRevenue = currentMonthReports.get(0).getMonthlyRevenue();
                monthlyRevenueLabel.setText(String.valueOf(monthlyRevenue));
            } else {
                monthlyRevenueLabel.setText("N/A");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateNumberOfStatus() {
        if (numberOfStatusLabel == null) {
            return;
        }
        try {
            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            ArrayList<MonthlyRentBill> monthlyRentBills = monthlyRentBillBUS.getAll();

            int paid = 0, pending = 0, unpaid = 0;
            for (MonthlyRentBill monthlyRentBill : monthlyRentBills) {
                String status = monthlyRentBill.getStatus();
                if (Objects.equals(status, "Paid")) {
                    paid++;
                } else if (Objects.equals(status, "Pending")) {
                    pending++;
                } else {
                    unpaid++;
                }
            }
            // Tạo các đối tượng PieChart.Data với chú thích tương ứng
            PieChart.Data paidData = new PieChart.Data("Paid (" + paid + ")", paid);
            PieChart.Data pendingData = new PieChart.Data("Pending (" + pending + ")", pending);
            PieChart.Data unpaidData = new PieChart.Data("Unpaid (" + unpaid + ")", unpaid);

            // Thêm các đối tượng PieChart.Data vào danh sách pieChartData
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    paidData, pendingData, unpaidData
            );

            // Cập nhật dữ liệu cho numberOfStatusLabel
            numberOfStatusLabel.setData(pieChartData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawLineChartOfMonthlyOpex() {
        if (barChartOfMonthlyOpex == null) {
            return;
        }

        try {
            ArrayList<FinancialReport> financialReports = FinancialReportBUS.getInstance().getAll();

            // Tạo danh sách chứa dữ liệu cho Bar Chart
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Phí Vận Hành Hàng Tháng");

            // Lấy 12 báo cáo tài chính gần nhất
            int startIndex = Math.max(financialReports.size() - 12, 0);
            for (int i = startIndex; i < financialReports.size(); i++) {
                FinancialReport report = financialReports.get(i);
                // Lấy tháng từ ngày báo cáo
                String month = report.getDate().toString().substring(5, 7);
                // Thêm dữ liệu cho series
                series.getData().add(new XYChart.Data<>(month, report.getMonthlyOpex()));
            }

            // Sắp xếp lại dữ liệu theo tháng
            series.getData().sort(Comparator.comparing(data -> Integer.parseInt(data.getXValue())));

            // Xóa dữ liệu cũ trước khi cập nhật
            barChartOfMonthlyOpex.getData().clear();
            // Thêm series vào Bar Chart
            barChartOfMonthlyOpex.getData().add(series);

            // Xóa chú thích của Bar Chart
            barChartOfMonthlyOpex.setLegendVisible(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Label datePage0 = new Label();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildingsList = FXCollections.observableArrayList();
        buildingManagersList = FXCollections.observableArrayList();
        financialReportsList = FXCollections.observableArrayList();
        // Loading Page 0 From Nam
        updateMonthlyRevenueLabel();
        updateNumberOfStatus();
        drawLineChartOfMonthlyOpex();

        loadBuildingManagers();
        loadBuilding();
        loadDinancialReport();

        showcomboboxBuildingManager();
        showcombobox();
        box_page1();
        keyenter();
        keyenter1();
        box_page0();

    }

    public void keyenter() {
        Platform.runLater(() -> {
            TxtField__P1__search.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handleSearch();
                   
                }
            });
        });

    }

    public void keyenter1() {
        Platform.runLater(() -> {
            seacrch_namepage1.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {

                    handleseacrhName();
                }
            });
        });

    }

    public void showcomboboxBuildingManager() {
        Platform.runLater(() -> {
            if (fruitCombo != null) {
                ObservableList<String> genders = FXCollections.observableArrayList(
                        "Nam",
                        "Nữ");
                fruitCombo.setItems(genders);
            } else {
                System.err.println("fruitCombo is null. Check FXML and controller connection.");
            }
        });
    }

    public void box_page1() {
        Platform.runLater(() -> {
            if (box_dress != null) {
                ObservableList<String> districts = FXCollections.observableArrayList(
                        "Tất Cả",
                        "Quận 1",
                        "Quận 2",
                        "Quận 3",
                        "Quận 4",
                        "Quận 5",
                        "Quận 6",
                        "Quận 7",
                        "Quận 8",
                        "Quận 9",
                        "Quận 10",
                        "Quận 11",
                        "Quận 12",
                        "Quận Bình Tân",
                        "Quận Bình Thạnh",
                        "Quận Gò Vấp",
                        "Quận Phú Nhuận",
                        "Quận Tân Bình",
                        "Quận Tân Phú",
                        "Quận Thủ Đức");
                box_dress.setItems(districts);
            } else {
                System.err.println("box_dressmbo is null. Check FXML and controller connection.");
            }
        });
    }

    public void box_page0() {
        Platform.runLater(() -> {
            if (TxtField__P1__4 != null) {
                ObservableList<String> districts = FXCollections.observableArrayList(
                     
                        "Quận 1",
                        "Quận 2",
                        "Quận 3",
                        "Quận 4",
                        "Quận 5",
                        "Quận 6",
                        "Quận 7",
                        "Quận 8",
                        "Quận 9",
                        "Quận 10",
                        "Quận 11",
                        "Quận 12",
                        "Quận Bình Tân",
                        "Quận Bình Thạnh",
                        "Quận Gò Vấp",
                        "Quận Phú Nhuận",
                        "Quận Tân Bình",
                        "Quận Tân Phú",
                        "Quận Thủ Đức");
                TxtField__P1__4.setItems(districts);
            } else {
                System.err.println("box_dressmbo is null. Check FXML and controller connection.");
            }
        });
    }

    public void showcombobox() {
        Platform.runLater(() -> {
            if (comboBox__P1__1 != null) {
                ObservableList<String> genders = FXCollections.observableArrayList(
                        "Tất Cả",
                        "Nam",
                        "Nữ");
                comboBox__P1__1.setItems(genders);
            } else {
                System.err.println("comboBox__P1__1 is null. Check FXML and controller connection.");
            }
        });
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
            // Thiết lập các cột cho TableView
            buildingManagerIdColumn.setCellValueFactory(new PropertyValueFactory<>("buildingManagerId"));
            buildingIdColumn.setCellValueFactory(new PropertyValueFactory<>("buildingId"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            citizenIdentityCardColumn.setCellValueFactory(new PropertyValueFactory<>("citizenIdentityCard"));

            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
            table__view2.setItems(building);
            handbuildingmanager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadDinancialReport() {
        try {
            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            ArrayList<FinancialReport> FinancialReport = financialReportBUS.getAll();
            financialReportsList.addAll(FinancialReport);
            ObservableList<FinancialReport> financia = FXCollections.observableArrayList(financialReportsList);
            maBaoCaoColumn.setCellValueFactory(new PropertyValueFactory<>("financialReportID"));
            maQuanLiColumn.setCellValueFactory(new PropertyValueFactory<>("buildingManagerID"));
            maToaNha1Column.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
            ngayColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            doanhThuColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyRevenue"));
            monthlyOpexColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyOpex"));
            loiNhuanColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyProfit"));
            table__view3.setItems(financia);
            handleFianceRerport();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void handleBuilding() {
        table__view.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Building selectedRow = table__view.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    TxtField__P1__1.setText(selectedRow.getBuildingId());
                    TxtField__P1__2.setText(selectedRow.getNameBuilding());
                    TxtField__P1__3.setText(selectedRow.getCity_Building());
                    // Sử dụng setValue để chọn giá trị trong ComboBox
                    TxtField__P1__4.setValue(selectedRow.getDistrict_Building()); // districtComboBox là tên của
                                                                                   // ComboBox của bạn
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
                    double salary = selectedRow.getSalary();
                    DecimalFormat df = new DecimalFormat("#,##0.00"); // Định dạng số với 2 chữ số sau dấu thập phân
                    String formattedSalary = df.format(salary);
                    TxtField__b7.setText(formattedSalary);
                    datePickerDOB.setValue(selectedRow.getDob());
                    String genderValue = selectedRow.getGender();
                    if (genderValue != null && !genderValue.isEmpty()) {
                        fruitCombo.setValue(genderValue);
                    } else {
                        fruitCombo.getSelectionModel().clearSelection();
                    }
                    selectedBuildingToDelete1 = selectedRow;

                }
            }
        });
    }

    public void handleFianceRerport() {
        table__view3.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                FinancialReport selectedRow = table__view3.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {

                    TxtField__r1.setText(selectedRow.getFinancialReportID());
                    TxtField__r2.setText(selectedRow.getBuildingID());
                    TxtField__r4.setText(selectedRow.getBuildingManagerID());

                    // Xử lý monthlyOpex dạng Float
                    Float opex = selectedRow.getMonthlyOpex();
                    if (opex != null) {
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        TxtField__r3.setText(df.format(opex));
                    } else {
                        TxtField__r3.setText("N/A");
                    }

                    Date_page3.setValue(selectedRow.getDate());
                    selecFinancialReport = selectedRow;
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
private boolean containsNumber(String s) {
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            return true;
        }
    }
    return false;
}
    // =======Building======
    public void handleaddBuilding() {
        try {
            String buildingId = TxtField__P1__1.getText();
            String nameBuilding = TxtField__P1__2.getText();
            String city_Building = TxtField__P1__3.getText();
            String district_Building = TxtField__P1__4.getValue();
            if (district_Building == null || district_Building.isEmpty()) {
                showAlert("Lỗi", "Vui lòng chọn quận.", AlertType.ERROR);
                return;
            }
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
            if (containsNumber(nameBuilding)) {
                showAlert("Lỗi", "Tên tòa nhà không được chứa số.", AlertType.ERROR);
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
    
            showAlert("Lỗi", "Không có tòa nhà nào được chọn để xóa.", AlertType.ERROR);
            return;
        }

        confirmAndDelete(selectedBuildingToDelete, "Bạn có chắc chắn muốn xóa tòa nhà này không?", () -> {
            BuildingBUS buildingBUS = new BuildingBUS();
            boolean deleteResult = buildingBUS.delete(selectedBuildingToDelete);

            if (deleteResult) {
                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
                resetTextfield();
                updateBuildingList();
            } else {
                showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
            }
        });
    }

    public void resetTextfield() {
        TxtField__P1__1.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__3.setText("");
  
       
        TxtField__P1__5.setText("");
        TxtField__P1__6.setText("");
    }

    public void handleEdit() {
        if (selectedBuildingToDelete == null) {

            showAlert("Lỗi", "Không có tòa nhà nào được chọn để Sửa.", AlertType.ERROR);
            return;
        }
        String buildingId = TxtField__P1__1.getText().trim();
        String nameBuilding = TxtField__P1__2.getText();
        String city_Building = TxtField__P1__3.getText();
        String district_Building = TxtField__P1__4.getValue();
        if (district_Building == null || district_Building.isEmpty()) {
            showAlert("Lỗi", "Vui lòng chọn quận.", AlertType.ERROR);
            return;
        }
        String address_Building = TxtField__P1__5.getText();
        int numberOfApartment_Building = 0;

        try {
            numberOfApartment_Building = Integer.parseInt(TxtField__P1__6.getText());
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số lượng căn hộ không hợp lệ.", AlertType.ERROR);
            return;
        }
        if (containsNumber(nameBuilding)) {
            showAlert("Lỗi", "Tên tòa nhà không được chứa số.", AlertType.ERROR);
            return;
        }
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

    public void selectDresss() {
        String dress = box_dress.getValue();
        if(dress.equals("Tất Cả")){
            BuildingBUS buildingBUS = new BuildingBUS();
            ArrayList<Building> searchResult = buildingBUS.getAll();
            buildingsList.addAll(searchResult);
            ObservableList<Building> observableBuildingList = FXCollections
                    .observableArrayList(searchResult);
            table__view.setItems(observableBuildingList);
        }else {
            BuildingBUS buildingBUS = new BuildingBUS();
            ArrayList<Building> searchResult = buildingBUS.search(dress, "quận");
            ObservableList<Building> observableBuildingList = FXCollections
                    .observableArrayList(searchResult);
            table__view.setItems(observableBuildingList);
        }


       
    }

    public void handleseacrhName() {
       String name = seacrch_namepage1.getText();
       if(name.equals("")){
           BuildingBUS buildingBUS = new BuildingBUS();
           ArrayList<Building> searchResult = buildingBUS.getAll();
           buildingsList.addAll(searchResult);
           ObservableList<Building> observableBuildingList = FXCollections
                   .observableArrayList(searchResult);
           table__view.setItems(observableBuildingList);
       }else {
           BuildingBUS buildingBUS = new BuildingBUS();
           ArrayList<Building> searchResult = buildingBUS.search(name, "Tên Tòa Nhà");
           ObservableList<Building> observableBuildingList = FXCollections.observableArrayList(searchResult);
           table__view.setItems(observableBuildingList);
       }
       
    }

    /////// BuildingManeger///====================
    public void handleAddpage2() {
        String buildingManagerId = TxtField__b1.getText();
        String buildingId = TxtField__b2.getText();
        String lastName = TxtField__b3.getText();
        String firstName = TxtField__b4.getText();
        String phoneNumber = TxtField__b5.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = fruitCombo.getValue();
        String citizenIdentityCard = TxtField__b6.getText();
        Float salary = Float.parseFloat(TxtField__b7.getText().replaceAll(",", ""));

        if (buildingManagerId.isEmpty() || buildingId.isEmpty() || lastName.isEmpty() || firstName.isEmpty()
                || phoneNumber.isEmpty() || dob == null || gender == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        if (lastName.length() > 40 || firstName.length() > 40) {
            showAlert("Lỗi", "Họ và tên không được quá 255 ký tự.", AlertType.ERROR);
            return;
        }

        if (containsNumber(lastName)) {
            showAlert("Lỗi", "Tên  không được chứa số.", AlertType.ERROR);
            return;
        }
        if (containsNumber(firstName)) {
            showAlert("Lỗi", "Tên  không được chứa số.", AlertType.ERROR);
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
            updateBuildingManeger();
            clearInputFields();

        } else {
            showAlert("Lỗi", "Mã đã tồn tại trong cơ sở dữ liệu.", AlertType.ERROR);
        }
    }

    private void clearInputFields() {
        TxtField__b1.clear();
        TxtField__b2.clear();
        TxtField__b3.clear();
        TxtField__b4.clear();
        TxtField__b5.clear();
        TxtField__b6.clear();
        TxtField__b7.clear();
        datePickerDOB.setValue(null);
        fruitCombo.getSelectionModel().clearSelection();
    }

    public void updateBuildingManeger() {
        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        buildingManagersList.clear();
        ArrayList<DTO.BuildingManager> buildings = buildingManagerBUS.getAll();
        buildingManagersList.addAll(buildings);
        ObservableList<DTO.BuildingManager> observableBuildingList = FXCollections.observableArrayList(
                buildingManagersList);
        table__view2.setItems(observableBuildingList);
    }

    public void handleDeletepage2() {

        if (selectedBuildingToDelete1 == null) {

            showAlert("Lỗi", "Không có Quản Lí nào được chọn để xóa.", AlertType.ERROR);
            return;
        }
        if (selectedBuildingToDelete1 == null) {
            System.out.println("Không có tòa nhà nào được chọn để xóa.");
            return;
        }
        confirmAndDelete(selectedBuildingToDelete1, "Bạn có chắc chắn muốn xóa quản lí này không?", () -> {
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            boolean deleteResult = buildingManagerBUS.delete(selectedBuildingToDelete1);
            if (deleteResult) {
                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
                clearInputFields();
                updateBuildingManeger();
            } else {
                showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
            }
        });
    }

    public void handleEditBuildingManeger() {
        if (selectedBuildingToDelete1 == null) {

            showAlert("Lỗi", "Không có Quản Lí nào được chọn để sửa.", AlertType.ERROR);
            return;
        }
        
        String buildingManagerId = TxtField__b1.getText();
        String buildingId = TxtField__b2.getText();
        String lastName = TxtField__b3.getText();
        String firstName = TxtField__b4.getText();
        String phoneNumber = TxtField__b5.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = fruitCombo.getValue();

        if (buildingManagerId.isEmpty() || buildingId.isEmpty() || lastName.isEmpty() || firstName.isEmpty()
                || phoneNumber.isEmpty() || dob == null || gender == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        if (lastName.length() > 40 || firstName.length() > 40) {
            showAlert("Lỗi", "Họ và tên không được quá 255 ký tự.", AlertType.ERROR);
            return;
        }

        if (containsNumber(lastName) ) {
            showAlert("Lỗi", "Tên  không được chứa số.", AlertType.ERROR);
            return;
        }
        if (containsNumber(firstName)) {
            showAlert("Lỗi", "Tên  không được chứa số.", AlertType.ERROR);
            return;
        }

        String citizenIdentityCard = TxtField__b6.getText();
        Float salary = Float.parseFloat(TxtField__b7.getText().replaceAll(",", ""));
        BuildingManager buildingManager = new BuildingManager();
        buildingManager.setBuildingManagerId(buildingManagerId);
        buildingManager.setBuildingId(buildingId);
        buildingManager.setLastName(lastName);
        buildingManager.setFirstName(firstName);
        buildingManager.setPhoneNumber(phoneNumber);
        buildingManager.setDob(dob);
        buildingManager.setGender(gender);
        buildingManager.setCitizenIdentityCard(citizenIdentityCard);
        buildingManager.setSalary(salary);
        System.out.println(salary);
        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        boolean check = buildingManagerBUS.update(buildingManager);
        if (check) {
            if (check) {
                showAlert("Thành Công", "Cập nhật thành công", AlertType.CONFIRMATION);
                updateBuildingManeger();
                clearInputFields();
            } else {
                showAlert("Thất Bại", "Không thể cập nhật", AlertType.ERROR);
            }
        }
    }

    public void SelectGender() {
        String gender = comboBox__P1__1.getValue();
        if (gender.equals("Tất Cả")) {
            BuildingManagerBUS buildingBUS = new BuildingManagerBUS();
            ArrayList<BuildingManager> searchResult = buildingBUS.getAll();
            buildingManagersList.addAll(searchResult);
            ObservableList<BuildingManager> observableBuildingList = FXCollections
                    .observableArrayList(searchResult);
            table__view2.setItems(observableBuildingList);
        } else {
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            ArrayList<BuildingManager> buildingManagers = buildingManagerBUS.search(gender, "Giới Tính");
            ObservableList<BuildingManager> observableBuildingList = FXCollections
                    .observableArrayList(buildingManagers);
            table__view2.setItems(observableBuildingList);
        }
  
    }

    public void handleSearch() {
        String name = TxtField__P1__search.getText();
      BuildingManagerBUS buildingManager = new BuildingManagerBUS();
      ArrayList<BuildingManager> buildingManagers = buildingManager.search(name,"Tìm Theo Ten");
        ObservableList<BuildingManager> observableBuildingList = FXCollections
                .observableArrayList(buildingManagers);
        table__view2.setItems(observableBuildingList);
    }

    /// ReanialReport///////==========

   public void handleAddReport() {

       String financialReportID = TxtField__r1.getText();
       String buildingID = TxtField__r2.getText();
       String buildingManagerID = TxtField__r4.getText();
       LocalDate date = Date_page3.getValue();
       FinancialReportBUS reportBUS = new FinancialReportBUS();
       Month month = date.getMonth();
       int year = date.getYear();
       Float revenue = (reportBUS.calculateMonthlyRevenueForBuilding(buildingID, month, year));
       String revenueStr = String.valueOf(revenue);
       String revenueWithoutComma = revenueStr.replaceAll(",", "");
       Float doanhthu = Float.parseFloat(revenueWithoutComma);
       System.out.println(doanhthu);
     
       Float monthlyOpex = Float.parseFloat(TxtField__r3.getText().replaceAll(",", ""));
       

       Float LoiNhuan = reportBUS.LoiNhuan(buildingID, month, year, monthlyOpex);
       String LoiNhuan1 = String.valueOf(LoiNhuan);
       String LoiNhuan2 = LoiNhuan1.replaceAll(",", "");
       Float LoiNhuan3 = Float.parseFloat(LoiNhuan2);

       System.out.println(LoiNhuan3);


       FinancialReport newFinancialReport = new FinancialReport();
       newFinancialReport.setFinancialReportID(financialReportID);
       newFinancialReport.setBuildingID(buildingID);
       newFinancialReport.setBuildingManagerID(buildingManagerID);
       newFinancialReport.setDate(date);
       newFinancialReport.setMonthlyRevenue(doanhthu);
       newFinancialReport.setMonthlyOpex(monthlyOpex);
       newFinancialReport.setMonthlyProfit(LoiNhuan3);

       FinancialReportBUS financialReportBUS = new FinancialReportBUS();
       boolean check = financialReportBUS.add(newFinancialReport);
       if (check) {
           showAlert("Thành Công", "Đã Thêm Thành Công", AlertType.CONFIRMATION);
           updateFianReport();
           clearFildReport();
       } else {
           showAlert("Thất Bai", "Thêm Thất Bại", AlertType.ERROR);
       }

    }

    public void updateFianReport() {
        FinancialReportBUS financialReportBUS = new FinancialReportBUS();

        financialReportsList.clear();
        ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();
        financialReportsList.addAll(financialReports);
        ObservableList<FinancialReport> financialReports2 = FXCollections.observableArrayList(
                financialReportsList);
        table__view3.setItems(financialReports2);
    }

    public void handleDeleReport() {
        if (selecFinancialReport == null) {

            showAlert("Lỗi", "Không có báo cáo nào được chọn để xóa.", AlertType.ERROR);
            return;
        }

        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
        confirmAndDelete(selecFinancialReport, "Bạn có chắc chắn muốn xóa báo cáo này không?", () -> {
            boolean check = financialReportBUS.delete(selecFinancialReport);
            if (check) {
                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
                updateFianReport();
                clearFildReport();
            } else {
                showAlert("Thất Bại", "Xóa Thất Bại", AlertType.ERROR);
            }
        });
    }
    
    private boolean isValidNumber(String input) {

        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                return false; 
            }
        }
        return true; 
    }

    public void handEditReport() {
        if (selecFinancialReport == null) {

            showAlert("Lỗi", "Không có báo cáo nào được chọn để sửa.", AlertType.ERROR);
            return;
        }

        String financialReportID = TxtField__r1.getText();
        String buildingID = TxtField__r2.getText();
        String buildingManagerID = TxtField__r4.getText();
        LocalDate date = Date_page3.getValue();
        FinancialReportBUS reportBUS = new FinancialReportBUS();
        Month month = date.getMonth();
        int year = date.getYear();
        Float revenue = (reportBUS.calculateMonthlyRevenueForBuilding(buildingID, month, year));
        String revenueStr = String.valueOf(revenue);
        String revenueWithoutComma = revenueStr.replaceAll(",", "");
        Float doanhthu = Float.parseFloat(revenueWithoutComma);
        System.out.println(doanhthu);

        Float monthlyOpex = Float.parseFloat(TxtField__r3.getText().replaceAll(",", ""));

        Float LoiNhuan = reportBUS.LoiNhuan(buildingID, month, year, monthlyOpex);
        String LoiNhuan1 = String.valueOf(LoiNhuan);
        String LoiNhuan2 = LoiNhuan1.replaceAll(",", "");
        Float LoiNhuan3 = Float.parseFloat(LoiNhuan2);

        System.out.println(LoiNhuan3);

        FinancialReport newFinancialReport = new FinancialReport();
        newFinancialReport.setFinancialReportID(financialReportID);
        newFinancialReport.setBuildingID(buildingID);
        newFinancialReport.setBuildingManagerID(buildingManagerID);
        newFinancialReport.setDate(date);
        newFinancialReport.setMonthlyRevenue(doanhthu);
        newFinancialReport.setMonthlyOpex(monthlyOpex);
        newFinancialReport.setMonthlyProfit(LoiNhuan3);

        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
        boolean check = financialReportBUS.update(newFinancialReport);
        if (check) {
            showAlert("Thành Công", "Đã sửa Thành Công", AlertType.CONFIRMATION);
            updateFianReport();
            clearFildReport();
        } else {
            showAlert("Thất Bai", "sửa Thất Bại", AlertType.ERROR);
        }

    }

    private void clearFildReport() {
        TxtField__r1.clear();
        TxtField__r2.clear();
        TxtField__r3.clear();
        TxtField__r4.clear();
        TxtField__r5.clear();
        TxtField__r6.clear();
        TxtField__b7.clear();
        Date_page3.setValue(null);

    }

    public void searchDaypage3() {
        LocalDate dateFirst = date1.getValue();
        LocalDate dateLast = date2.getValue();
       FinancialReportBUS financialReportBUS= new FinancialReportBUS();
        ArrayList<FinancialReport> financialReports = financialReportBUS.search(dateFirst, dateLast, "Tìm Theo Ngày");
        ObservableList<FinancialReport> observableBuildingList = FXCollections
                .observableArrayList(financialReports);
        table__view3.setItems(observableBuildingList);
    }

    public void resetDay() {

        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
        financialReportsList.clear();
        ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();
        financialReportsList.addAll(financialReports);
        ObservableList<FinancialReport> observableList = FXCollections
                .observableArrayList(financialReportsList);
        table__view3.setItems(observableList);
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = Boss.getBossStage();
        if (primaryStage == null){
            primaryStage = main.getInstance().getPrimaryStage();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
