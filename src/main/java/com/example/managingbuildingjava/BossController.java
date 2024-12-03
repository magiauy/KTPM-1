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
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private Button bnt__P1__add = new Button();

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
    private ComboBox<String> combobox__P2__1 = new ComboBox<>();

    @FXML
    private Button bnt__P2__add = new Button();


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

    @FXML
    private ComboBox<String> combobox__b = new ComboBox<>();

    //page 4
    @FXML
    private TableColumn<Table_User, String> Column_P7_3 = new TableColumn<>();

    @FXML
    private TableColumn<Table_User, String> Column_P7_4 = new TableColumn<>();

    @FXML
    private TableColumn<Table_User, String> Column_P7_1 = new TableColumn<>();

    @FXML
    private TableColumn<Table_User, LocalDate> Column_P7_2 = new TableColumn<>();


    @FXML
    private TextField TxtField__P7__search = new TextField();

    @FXML
    private Button bnt__P7__add = new Button();

    @FXML
    private Button bnt__P7__delete = new Button();

    @FXML
    private Button bnt__P7__update = new Button();

    @FXML
    private TableView<Table_User> table__P7__1 = new TableView<>();

    private ObservableList<Table_User> tableUserObservableList;

    public ObservableList<Table_User> getUserList() {
        ObservableList<Table_User> tableUserObservableList = FXCollections.observableArrayList();
        List<LeaseAgreement> leaseAgreements = LeaseAgreementBUS.getInstance().getAll();
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            Table_User tableUser = new Table_User();
            tableUser.setT(leaseAgreement.getTenantID());
            tableUser.setActive("Chưa có tài khoản");
            CustomersAccountBUS customersAccountBUS = new CustomersAccountBUS();
            ArrayList<CustomersAccount> customersAccounts = customersAccountBUS.getAll();
            for (CustomersAccount customersAccount : customersAccounts) {
                if (customersAccount.getId().equals(leaseAgreement.getTenantID())) {
                    if (leaseAgreement.getLeaseEndDate().isBefore(LocalDate.now())){
                        boolean deleteSuccess = customersAccountBUS.delete(customersAccount);
                        if (deleteSuccess){
                            tableUser.setActive("Vô hiệu");
                        }
                    } else {
                        tableUser.setActive("Đang hoạt động");
                    }
                }
            }
            tableUser.setLa(leaseAgreement.getLeaseAgreementID());
            tableUser.setDe(leaseAgreement.getLeaseEndDate());

            tableUserObservableList.add(tableUser);
        }
        List<Tenant> tenants = TenantBUS.getInstance().getTenantsNotInLeaseAgreement();
        for (Tenant tenant : tenants) {
            Table_User tableUser = new Table_User();
            tableUser.setT(tenant.getTenantID());
            tableUser.setLa(null);
            tableUser.setDe(null);
            tableUser.setActive("Chưa có tài khoản");
            tableUserObservableList.add(tableUser);
        }

        return tableUserObservableList;
    }

    private void initTableUser() {
        Column_P7_1.setCellValueFactory(new PropertyValueFactory<>("t"));
        Column_P7_2.setCellValueFactory(new PropertyValueFactory<>("la"));
        Column_P7_3.setCellValueFactory(new PropertyValueFactory<>("de"));
        Column_P7_4.setCellValueFactory(new PropertyValueFactory<>("active"));
        tableUserObservableList = getUserList();
        table__P7__1.setItems(tableUserObservableList);
    }

    @FXML
    void themTaiKhoan(MouseEvent event) {
        Table_User selected = table__P7__1.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CustomersAccountBUS customersAccountBUS = new CustomersAccountBUS();
            CustomersAccount customersAccount = new CustomersAccount("TK"+selected.getT(), "123", selected.getT());
            boolean Success = customersAccountBUS.add(customersAccount);
            if (Success) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Thêm thành công tài khoản của khách hàng "+selected.getT());
                alert.showAndWait();
                initTableUser();
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Đã có tài khoản "+selected.getT());
                alert.showAndWait();
            }
        }
    }

    @FXML
    void xoaTaiKhoan(MouseEvent event) {
        Table_User selectedTenant = table__P7__1.getSelectionModel().getSelectedItem();
        if (selectedTenant != null) {
            Tenant tenant = new Tenant();
            tenant.setTenantID(selectedTenant.getT());
            TenantBUS tenantBUS = new TenantBUS();
            boolean deleteSuccess = tenantBUS.delete(tenant);
            if (deleteSuccess) {
                tableUserObservableList.remove(selectedTenant);
                table__P7__1.refresh();
            } else {
                System.err.println("Không thể xóa khách hàng từ cơ sở dữ liệu.");
            }
        }
    }
    //

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
                if (Objects.equals(status, "Đã thanh toán")) {
                    paid++;
                } else if (Objects.equals(status, "Quá hạn")) {
                    pending++;
                } else {
                    unpaid++;
                }
            }
            // Tạo các đối tượng PieChart.Data với chú thích tương ứng
            PieChart.Data paidData = new PieChart.Data("Đã thanh toán (" + paid + ")", paid);
            PieChart.Data pendingData = new PieChart.Data("Quá hạn (" + pending + ")", pending);
            PieChart.Data unpaidData = new PieChart.Data("Chưa thanh toán (" + unpaid + ")", unpaid);

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
        TimeNow();
        drawLineChartOfMonthlyOpex();
        updateNumberOfStatus();
        updateMonthlyRevenueLabel();
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
//        box_page1();
//        keyenter();
        keyenter1();
//        box_page0();
        initTableUser();

        BuildingBUS bus = new BuildingBUS();

        //Khởi tạo page 2
        ArrayList<Building> buildingNotInBuildingManager = bus.getBuildingsWithoutManager();
        ArrayList<String> buildingIDNotInBuildingManager = new ArrayList<>();
        for (Building building : buildingNotInBuildingManager) {
            buildingIDNotInBuildingManager.add(building.getBuildingId());
        }
        combobox__P2__1.getItems().addAll(buildingIDNotInBuildingManager);


        //

        //Khởi tạo page 3


        ArrayList<Building> buildings = bus.getAll();
        ArrayList<String> buildingID = new ArrayList<>();
        for (Building building : buildings) {
            buildingID.add(building.getBuildingId());
        }
        combobox__b.getItems().addAll(buildingID);
        TxtField__r4.setDisable(true);
        //

    }

    private boolean processing = false; // Cờ để ngăn vòng lặp vô hạn

    @FXML
    void initCombobox__b(ActionEvent event) {
        // Bỏ qua nếu hàm được kích hoạt do thay đổi lập trình
        if (processing) {
            return;
        }

        // Kiểm tra nếu giá trị ComboBox là null
        String buildingid__getCombobox = combobox__b.getSelectionModel().getSelectedItem();
        if (buildingid__getCombobox == null) {
            return; // Không xử lý gì thêm nếu không có giá trị được chọn
        }

        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        ArrayList<BuildingManager> buildingManagers = buildingManagerBUS.getAll();
        boolean flag = false;

        for (BuildingManager buildingManager : buildingManagers) {
            if (buildingManager.getBuildingId().equals(buildingid__getCombobox)) {
                TxtField__r4.setText(buildingManager.getBuildingManagerId());
                flag = true;
                break;
            }
        }

        if (!flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Không tìm thấy quản lý tòa nhà");
            alert.setHeaderText(null);
            alert.setContentText("Tòa nhà này chưa có nhân viên.");
            alert.showAndWait();

            // Sử dụng Platform.runLater để tránh vòng lặp khi set giá trị ComboBox
            processing = true;
            Platform.runLater(() -> {
                combobox__b.setValue(null); // Xóa giá trị ComboBox
                TxtField__r4.setText("");
                processing = false;
            });
        }
    }

//    public void keyenter() {
//        Platform.runLater(() -> {
//            TxtField__P1__search.setOnKeyPressed(event -> {
//                if (event.getCode() == KeyCode.ENTER) {
//                    handleSearch();
//
//                }
//            });
//        });
//
//    }

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

//    public void box_page1() {
//        Platform.runLater(() -> {
//            if (box_dress != null) {
//                ObservableList<String> districts = FXCollections.observableArrayList(
//                        "Tất Cả",
//                        "Quận 1",
//                        "Quận 2",
//                        "Quận 3",
//                        "Quận 4",
//                        "Quận 5",
//                        "Quận 6",
//                        "Quận 7",
//                        "Quận 8",
//                        "Quận 9",
//                        "Quận 10",
//                        "Quận 11",
//                        "Quận 12",
//                        "Quận Bình Tân",
//                        "Quận Bình Thạnh",
//                        "Quận Gò Vấp",
//                        "Quận Phú Nhuận",
//                        "Quận Tân Bình",
//                        "Quận Tân Phú",
//                        "Quận Thủ Đức");
//                box_dress.setItems(districts);
//            } else {
//                System.err.println("box_dressmbo is null. Check FXML and controller connection.");
//            }
//        });
//    }

//    public void box_page0() {
//        Platform.runLater(() -> {
//            if (TxtField__P1__4 != null) {
//                ObservableList<String> districts = FXCollections.observableArrayList(
//
//                        "Quận 1",
//                        "Quận 2",
//                        "Quận 3",
//                        "Quận 4",
//                        "Quận 5",
//                        "Quận 6",
//                        "Quận 7",
//                        "Quận 8",
//                        "Quận 9",
//                        "Quận 10",
//                        "Quận 11",
//                        "Quận 12",
//                        "Quận Bình Tân",
//                        "Quận Bình Thạnh",
//                        "Quận Gò Vấp",
//                        "Quận Phú Nhuận",
//                        "Quận Tân Bình",
//                        "Quận Tân Phú",
//                        "Quận Thủ Đức");
//                TxtField__P1__4.setItems(districts);
//            } else {
//                System.err.println("box_dressmbo is null. Check FXML and controller connection.");
//            }
//        });
//    }

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
//            quanColumn.setCellValueFactory(new PropertyValueFactory<>("district_Building"));
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

    private Building previouslySelectedRow = null; // Lưu trữ dòng đã chọn trước đó

    public void handleBuilding() {
        table__view.setOnMouseClicked(event -> {
            // Kiểm tra dòng hiện tại được chọn
            Building selectedRow = table__view.getSelectionModel().getSelectedItem();

            if (event.getClickCount() == 1) {
                // Nếu chọn lại đúng dòng đã được chọn trước đó, bỏ chọn
                if (selectedRow != null && selectedRow.equals(previouslySelectedRow)) {
                    // Bỏ chọn và xóa thông tin trong text field
                    table__view.getSelectionModel().clearSelection(); // Bỏ chọn
                    TxtField__P1__2.clear();
                    TxtField__P1__3.clear();
                    TxtField__P1__5.clear();
                    TxtField__P1__6.clear();
                    selectedBuildingToDelete = null;
                    previouslySelectedRow = null; // Đặt lại trạng thái chọn

                    // Enable nút bnt__P1__add
                    bnt__P1__add.setDisable(false);
                }
                // Nếu chọn dòng mới, điền thông tin và disable nút
                else if (selectedRow != null) {
                    TxtField__P1__2.setText(selectedRow.getNameBuilding());
                    TxtField__P1__3.setText(selectedRow.getCity_Building());
                    TxtField__P1__5.setText(selectedRow.getAddress_Building());
                    TxtField__P1__6.setText(String.valueOf(selectedRow.getNumberOfApartment_Building()));
                    selectedBuildingToDelete = selectedRow;
                    previouslySelectedRow = selectedRow; // Cập nhật dòng đã chọn

                    // Disable nút bnt__P1__add
                    bnt__P1__add.setDisable(true);
                }
                // Nếu không có dòng nào được chọn
                else {
                    TxtField__P1__2.clear();
                    TxtField__P1__3.clear();
                    TxtField__P1__5.clear();
                    TxtField__P1__6.clear();
                    selectedBuildingToDelete = null;
                    previouslySelectedRow = null;

                    // Enable nút bnt__P1__add
                    bnt__P1__add.setDisable(false);
                }
            }
        });
    }



    private DTO.BuildingManager previouslySelectedRow1 = null; // Lưu trữ dòng đã chọn trước đó

    public void handbuildingmanager() {
        table__view2.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Kiểm tra dòng hiện tại được chọn
                DTO.BuildingManager selectedRow = table__view2.getSelectionModel().getSelectedItem();

                // Nếu chọn lại đúng dòng đã được chọn trước đó, bỏ chọn
                if (selectedRow != null && selectedRow.equals(previouslySelectedRow1)) {
                    // Bỏ chọn và xóa thông tin trong các text field
                    table__view2.getSelectionModel().clearSelection(); // Bỏ chọn
                    combobox__P2__1.setValue(null);
                    TxtField__b3.clear();
                    TxtField__b4.clear();
                    TxtField__b5.clear();
                    TxtField__b6.clear();
                    TxtField__b7.clear();
                    datePickerDOB.setValue(null);
                    fruitCombo.getSelectionModel().clearSelection();
                    selectedBuildingToDelete1 = null;
                    previouslySelectedRow1 = null; // Đặt lại trạng thái chọn

                    // Enable nút bnt__P2__add
                    bnt__P2__add.setDisable(false);
                }
                // Nếu chọn dòng mới, điền thông tin và disable nút
                else if (selectedRow != null) {
                    combobox__P2__1.setValue(selectedRow.getBuildingId());
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
                    previouslySelectedRow1 = selectedRow; // Cập nhật dòng đã chọn

                    // Disable nút bnt__P2__add
                    bnt__P2__add.setDisable(true);
                }
                // Nếu không có dòng nào được chọn
                else {
                    combobox__P2__1.setValue(null);
                    TxtField__b3.clear();
                    TxtField__b4.clear();
                    TxtField__b5.clear();
                    TxtField__b6.clear();
                    TxtField__b7.clear();
                    datePickerDOB.setValue(null);
                    fruitCombo.getSelectionModel().clearSelection();
                    selectedBuildingToDelete1 = null;
                    previouslySelectedRow1 = null;

                    // Enable nút bnt__P2__add
                    bnt__P2__add.setDisable(false);
                }
            }
        });
    }


    public void handleFianceRerport() {
        table__view3.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                FinancialReport selectedRow = table__view3.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {

                    TxtField__r4.setText(selectedRow.getBuildingManagerID());
                    combobox__b.setValue(selectedRow.getBuildingID());

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
            // Fetch input values
            String nameBuilding = TxtField__P1__2.getText().trim();
            String cityBuilding = TxtField__P1__3.getText().trim();
            String addressBuilding = TxtField__P1__5.getText().trim();
            String apartmentCountText = TxtField__P1__6.getText().trim();

            // Input validation
            if (nameBuilding.isEmpty() || cityBuilding.isEmpty() || addressBuilding.isEmpty()||apartmentCountText.isEmpty()) {
                showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
                return;
            }

            if (containsNumber(nameBuilding)) {
                showAlert("Lỗi", "Tên tòa nhà không được chứa số.", AlertType.ERROR);
                return;
            }

            if (!cityBuilding.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+")) {
                showAlert("Lỗi", "Tên thành phố không được chứa số và ký tự đặc biệt.", AlertType.ERROR);
                return;
            }

            int numberOfApartmentBuilding;
            try {
                numberOfApartmentBuilding = Integer.parseInt(apartmentCountText);
                if (numberOfApartmentBuilding <= 0) {
                    showAlert("Lỗi", "Số lượng căn hộ phải là một số nguyên dương.", AlertType.ERROR);
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Số lượng căn hộ phải là một số nguyên hợp lệ.", AlertType.ERROR);
                return;
            }

            // Create a new building object
            Building newBuilding = new Building();
            newBuilding.setBuildingId(buildingId); // Assuming `buildingId` is pre-defined
            newBuilding.setNameBuilding(nameBuilding);
            newBuilding.setCity_Building(cityBuilding);
            newBuilding.setAddress_Building(addressBuilding);
            newBuilding.setNumberOfApartment_Building(numberOfApartmentBuilding);

            // Insert into the database
            BuildingBUS buildingBUS = new BuildingBUS();
            boolean insertResult = buildingBUS.insert(newBuilding);

            if (insertResult) {
                showAlert("Thành Công", "Đã thêm thành công.", AlertType.CONFIRMATION);
                updateBuildingList(); // Refresh the building list
            } else {
                showAlert("Lỗi", "Mã đã tồn tại trong cơ sở dữ liệu.", AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Lỗi", "Đã xảy ra lỗi không mong muốn. Vui lòng thử lại.", AlertType.ERROR);
            e.printStackTrace();
        }
    }

//    public void handleDelete() {
//        if (selectedBuildingToDelete == null) {
//
//            showAlert("Lỗi", "Không có tòa nhà nào được chọn để xóa.", AlertType.ERROR);
//            return;
//        }
//
//        confirmAndDelete(selectedBuildingToDelete, "Bạn có chắc chắn muốn xóa tòa nhà này không?", () -> {
//            BuildingBUS buildingBUS = new BuildingBUS();
//            boolean deleteResult = buildingBUS.delete(selectedBuildingToDelete);
//
//            if (deleteResult) {
//                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
//                resetTextfield();
//                updateBuildingList();
//            } else {
//                showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
//            }
//        });
//    }

    public void resetTextfield() {
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
        String nameBuilding = TxtField__P1__2.getText().trim();
        String cityBuilding = TxtField__P1__3.getText().trim();
        String addressBuilding = TxtField__P1__5.getText().trim();
        String apartmentCountText = TxtField__P1__6.getText().trim();
        // Input validation
        if (nameBuilding.isEmpty() || cityBuilding.isEmpty() || addressBuilding.isEmpty()||apartmentCountText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        if (containsNumber(nameBuilding)) {
            showAlert("Lỗi", "Tên tòa nhà không được chứa số.", AlertType.ERROR);
            return;
        }

        if (!cityBuilding.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+")) {
            showAlert("Lỗi", "Tên thành phố không được chứa số và ký tự đặc biệt.", AlertType.ERROR);
            return;
        }

        int numberOfApartmentBuilding;
        try {
            numberOfApartmentBuilding = Integer.parseInt(apartmentCountText);
            if (numberOfApartmentBuilding <= 0) {
                showAlert("Lỗi", "Số lượng căn hộ phải là một số nguyên dương.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số lượng căn hộ phải là một số nguyên hợp lệ.", AlertType.ERROR);
            return;
        }

        selectedBuildingToDelete.setNameBuilding(nameBuilding);
        selectedBuildingToDelete.setCity_Building(cityBuilding);
        selectedBuildingToDelete.setAddress_Building(addressBuilding);
        selectedBuildingToDelete.setNumberOfApartment_Building(Integer.parseInt(apartmentCountText));

        BuildingBUS buildingBUS = new BuildingBUS();

        boolean updateResult = buildingBUS.update(selectedBuildingToDelete);

        if (updateResult) {
            showAlert("Thành Công", "Cập nhật thành công", AlertType.CONFIRMATION);
            table__view.refresh();
            resetTextfield();
            updateBuildingList();
        } else {
            showAlert("Thất Bại", "Không thể cập nhật", AlertType.ERROR);
        }
    }

//    public void selectDresss() {
//        String dress = box_dress.getValue();
//        if(dress.equals("Tất Cả")){
//            BuildingBUS buildingBUS = new BuildingBUS();
//            ArrayList<Building> searchResult = buildingBUS.getAll();
//            buildingsList.addAll(searchResult);
//            ObservableList<Building> observableBuildingList = FXCollections
//                    .observableArrayList(searchResult);
//            table__view.setItems(observableBuildingList);
//        }else {
//            BuildingBUS buildingBUS = new BuildingBUS();
//            ArrayList<Building> searchResult = buildingBUS.search(dress, "quận");
//            ObservableList<Building> observableBuildingList = FXCollections
//                    .observableArrayList(searchResult);
//            table__view.setItems(observableBuildingList);
//        }
//
//
//
//    }

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
        String buildingId = combobox__P2__1.getSelectionModel().getSelectedItem();
        String lastName = TxtField__b3.getText().trim();
        String firstName = TxtField__b4.getText().trim();
        String phoneNumber = TxtField__b5.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = fruitCombo.getValue();
        String citizenIdentityCard = TxtField__b6.getText();
        String salaryText = TxtField__b7.getText();

        if (buildingId == null || lastName.isEmpty() || firstName.isEmpty()
                || phoneNumber.isEmpty() || dob == null || gender == null || citizenIdentityCard.isEmpty() || salaryText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        if (lastName.length() > 40 || firstName.length() > 40) {
            showAlert("Lỗi", "Họ và tên không được quá 40 ký tự.", AlertType.ERROR);
            return;
        }

        if (containsNumber(lastName)) {
            showAlert("Lỗi", "Tên không được chứa số.", AlertType.ERROR);
            return;
        }
        if (containsNumber(firstName)) {
            showAlert("Lỗi", "Họ không được chứa số.", AlertType.ERROR);
            return;
        }

        if (!lastName.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+") || !firstName.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+")) {
            showAlert("Lỗi", "Họ và tên không được chứa ký tự đặc biệt.", AlertType.ERROR);
            return;
        }

        // Kiểm tra định dạng số điện thoại (giả sử là 10 chữ số)
        if (!phoneNumber.matches("\\d{10}")) {
            showAlert("Lỗi", "Số điện thoại phải gồm 10 chữ số.", AlertType.ERROR);
            return;
        }

        // Kiểm tra định dạng CMND/CCCD (giả sử là 9 hoặc 12 chữ số)
        if (!citizenIdentityCard.matches("\\d{12}")) {
            showAlert("Lỗi", "CMND/CCCD phải 12 chữ số.", AlertType.ERROR);
            return;
        }

        // Kiểm tra số lương có phải là số hợp lệ
        Float salary = 0f;
        try {
            salary = Float.parseFloat(salaryText.replaceAll(",", ""));
            if (salary <= 0) {
                showAlert("Lỗi", "Lương phải là số dương.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Lương không hợp lệ.", AlertType.ERROR);
            return;
        }


        DTO.BuildingManager newBuildingManager = new DTO.BuildingManager();
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
            combobox__P2__1.getItems().remove(buildingId);

        } else {
            showAlert("Lỗi", "Mã đã tồn tại trong cơ sở dữ liệu.", AlertType.ERROR);
        }
    }

    private void clearInputFields() {
        TxtField__b2.clear();
        TxtField__b3.clear();
        combobox__P2__1.getSelectionModel().clearSelection();
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

//    public void handleDeletepage2() {
//
//        if (selectedBuildingToDelete1 == null) {
//
//            showAlert("Lỗi", "Không có Quản Lí nào được chọn để xóa.", AlertType.ERROR);
//            return;
//        }
//
//        confirmAndDelete(selectedBuildingToDelete1, "Bạn có chắc chắn muốn xóa quản lí này không?", () -> {
//            if (selectedBuildingToDelete1.getFirstName().equals("")&&selectedBuildingToDelete1.getLastName().equals("TxtField__P1__search")) {
//                BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
//                boolean deleteResult = buildingManagerBUS.delete(selectedBuildingToDelete1);
//                if (deleteResult) {
//                    showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
//                    clearInputFields();
//                    updateBuildingManeger();
//                } else {
//                    showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
//                }
//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Thông báo");
//                alert.setHeaderText(null);
//                alert.setContentText("Nhân viên " + selectedBuildingToDelete1.getFirstName()+" "+selectedBuildingToDelete1.getLastName()+" đang quản lý toàn nhà "+selectedBuildingToDelete1.getBuildingId());
//                alert.showAndWait();
//            }
//        });
//    }

    public void handleEditBuildingManeger() {
        if (selectedBuildingToDelete1 == null) {

            showAlert("Lỗi", "Không có Quản Lí nào được chọn để sửa.", AlertType.ERROR);
            return;
        }

        String buildingId = combobox__P2__1.getSelectionModel().getSelectedItem();
        String lastName = TxtField__b3.getText().trim();
        String firstName = TxtField__b4.getText().trim();
        String phoneNumber = TxtField__b5.getText();
        LocalDate dob = datePickerDOB.getValue();
        String gender = fruitCombo.getValue();
        String citizenIdentityCard = TxtField__b6.getText();
        String salaryText = TxtField__b7.getText();


        if (buildingId == null || lastName.isEmpty() || firstName.isEmpty()
                || phoneNumber.isEmpty() || dob == null || gender == null || citizenIdentityCard.isEmpty() || salaryText.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        if (lastName.length() > 40 || firstName.length() > 40) {
            showAlert("Lỗi", "Họ và tên không được quá 40 ký tự.", AlertType.ERROR);
            return;
        }

        if (containsNumber(lastName)) {
            showAlert("Lỗi", "Tên không được chứa số.", AlertType.ERROR);
            return;
        }
        if (containsNumber(firstName)) {
            showAlert("Lỗi", "Họ không được chứa số.", AlertType.ERROR);
            return;
        }

        if (!lastName.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+") || !firstName.matches("[a-zA-Zàáảãạâấầẩẫậăắằẳẵặêếềểễệiíìỉĩịôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵ\\s]+")) {
            showAlert("Lỗi", "Họ và tên không được chứa ký tự đặc biệt.", AlertType.ERROR);
            return;
        }

        // Kiểm tra định dạng số điện thoại (giả sử là 10 chữ số)
        if (!phoneNumber.matches("\\d{10}")) {
            showAlert("Lỗi", "Số điện thoại phải gồm 10 chữ số.", AlertType.ERROR);
            return;
        }

        // Kiểm tra định dạng CMND/CCCD (giả sử là 9 hoặc 12 chữ số)
        if (!citizenIdentityCard.matches("\\d{12}")) {
            showAlert("Lỗi", "CMND/CCCD phải 12 chữ số.", AlertType.ERROR);
            return;
        }

        // Kiểm tra số lương có phải là số hợp lệ
        Float salary = 0f;
        try {
            salary = Float.parseFloat(salaryText.replaceAll(",", ""));
            if (salary <= 0) {
                showAlert("Lỗi", "Lương phải là số dương.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Lương không hợp lệ.", AlertType.ERROR);
            return;
        }

        selectedBuildingToDelete1.setBuildingId(buildingId);
        selectedBuildingToDelete1.setLastName(lastName);
        selectedBuildingToDelete1.setFirstName(firstName);
        selectedBuildingToDelete1.setPhoneNumber(phoneNumber);
        selectedBuildingToDelete1.setDob(dob);
        selectedBuildingToDelete1.setGender(gender);
        selectedBuildingToDelete1.setCitizenIdentityCard(citizenIdentityCard);
        selectedBuildingToDelete1.setSalary(salary);
        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        boolean check = buildingManagerBUS.update(selectedBuildingToDelete1);
        if (check) {
            showAlert("Thành Công", "Cập nhật thành công", AlertType.CONFIRMATION);
            table__view2.refresh();
            updateBuildingManeger();
            clearInputFields();
        } else {
                showAlert("Thất Bại", "Không thể cập nhật", AlertType.ERROR);
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

//    public void handleSearch() {
//        String name = TxtField__P1__search.getText();
//      BuildingManagerBUS buildingManager = new BuildingManagerBUS();
//      ArrayList<BuildingManager> buildingManagers = buildingManager.search(name,"Tìm Theo Ten");
//        ObservableList<BuildingManager> observableBuildingList = FXCollections
//                .observableArrayList(buildingManagers);
//        table__view2.setItems(observableBuildingList);
//    }

    /// ReanialReport///////==========

    public void handleAddReport() {

        // Lấy dữ liệu từ giao diện
        String buildingID = combobox__b.getSelectionModel().getSelectedItem();
        String buildingManagerID = TxtField__r4.getText().trim();
        String monthlyOpexStr = TxtField__r3.getText().trim();
        LocalDate date = Date_page3.getValue();

        // Kiểm tra xem các trường có bị bỏ trống không
        if (buildingID == null || buildingManagerID.isEmpty() || monthlyOpexStr.isEmpty() || date == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        // Kiểm tra xem số tiền chi phí vận hành (monthlyOpex) có hợp lệ không
        Float monthlyOpex = 0f;
        try {
            monthlyOpex = Float.parseFloat(monthlyOpexStr.replaceAll(",", "")); // Loại bỏ dấu phẩy nếu có
            if (monthlyOpex < 0) {
                showAlert("Lỗi", "Chi phí vận hành không thể là số âm.", AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Chi phí vận hành không hợp lệ.", AlertType.ERROR);
            return;
        }

        // Tính toán doanh thu và lợi nhuận
        FinancialReportBUS reportBUS = new FinancialReportBUS();
        Month month = date.getMonth();
        int year = date.getYear();
        Float revenue = reportBUS.calculateMonthlyRevenueForBuilding(buildingID, month, year);

        // Kiểm tra doanh thu có hợp lệ không
        if (revenue == null || revenue < 0) {
            showAlert("Lỗi", "Doanh thu không hợp lệ.", AlertType.ERROR);
            return;
        }

        // Loại bỏ dấu phẩy trong doanh thu
        String revenueStr = String.valueOf(revenue);
        String revenueWithoutComma = revenueStr.replaceAll(",", "");
        Float doanhthu = Float.parseFloat(revenueWithoutComma);

        // Tính toán lợi nhuận
        Float LoiNhuan = reportBUS.LoiNhuan(buildingID, month, year, monthlyOpex);
        String LoiNhuanStr = String.valueOf(LoiNhuan);
        String LoiNhuanWithoutComma = LoiNhuanStr.replaceAll(",", "");
        Float LoiNhuan3 = Float.parseFloat(LoiNhuanWithoutComma);

        // Tạo báo cáo tài chính
        FinancialReport newFinancialReport = new FinancialReport();
        newFinancialReport.setBuildingID(buildingID);
        newFinancialReport.setBuildingManagerID(buildingManagerID);
        newFinancialReport.setDate(date);
        newFinancialReport.setMonthlyRevenue(doanhthu);
        newFinancialReport.setMonthlyOpex(monthlyOpex);
        newFinancialReport.setMonthlyProfit(LoiNhuan3);

        // Thêm báo cáo tài chính vào cơ sở dữ liệu
        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
        boolean check = financialReportBUS.add(newFinancialReport);

        // Thông báo kết quả
        if (check) {
            showAlert("Thành Công", "Đã Thêm Thành Công", AlertType.CONFIRMATION);
            updateFianReport();
            clearFildReport();
        } else {
            showAlert("Thất Bại", "Thêm Thất Bại", AlertType.ERROR);
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

//    public void handleDeleReport() {
//        if (selecFinancialReport == null) {
//
//            showAlert("Lỗi", "Không có báo cáo nào được chọn để xóa.", AlertType.ERROR);
//            return;
//        }
//
//        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
//        confirmAndDelete(selecFinancialReport, "Bạn có chắc chắn muốn xóa báo cáo này không?", () -> {
//            boolean check = financialReportBUS.delete(selecFinancialReport);
//            if (check) {
//                showAlert("Thành Công", "Xóa Thành Công", AlertType.CONFIRMATION);
//                updateFianReport();
//                clearFildReport();
//            } else {
//                showAlert("Thất Bại", "Xóa Thất Bại", AlertType.ERROR);
//            }
//        });
//    }

    private boolean isValidNumber(String input) {

        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                return false;
            }
        }
        return true;
    }

    public void handEditReport() {
//        if (selecFinancialReport == null) {
//
//            showAlert("Lỗi", "Không có báo cáo nào được chọn để sửa.", AlertType.ERROR);
//            return;
//        }
//
//        String buildingManagerID = TxtField__r4.getText();
//        LocalDate date = Date_page3.getValue();
//        FinancialReportBUS reportBUS = new FinancialReportBUS();
//        Month month = date.getMonth();
//        int year = date.getYear();
//        Float revenue = (reportBUS.calculateMonthlyRevenueForBuilding(buildingID, month, year));
//        String revenueStr = String.valueOf(revenue);
//        String revenueWithoutComma = revenueStr.replaceAll(",", "");
//        Float doanhthu = Float.parseFloat(revenueWithoutComma);
//        System.out.println(doanhthu);
//
//        Float monthlyOpex = Float.parseFloat(TxtField__r3.getText().replaceAll(",", ""));
//
//        Float LoiNhuan = reportBUS.LoiNhuan(buildingID, month, year, monthlyOpex);
//        String LoiNhuan1 = String.valueOf(LoiNhuan);
//        String LoiNhuan2 = LoiNhuan1.replaceAll(",", "");
//        Float LoiNhuan3 = Float.parseFloat(LoiNhuan2);
//
//        System.out.println(LoiNhuan3);
//
//        FinancialReport newFinancialReport = new FinancialReport();
//        newFinancialReport.setFinancialReportID(financialReportID);
//        newFinancialReport.setBuildingID(buildingID);
//        newFinancialReport.setBuildingManagerID(buildingManagerID);
//        newFinancialReport.setDate(date);
//        newFinancialReport.setMonthlyRevenue(doanhthu);
//        newFinancialReport.setMonthlyOpex(monthlyOpex);
//        newFinancialReport.setMonthlyProfit(LoiNhuan3);
//
//        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
//        boolean check = financialReportBUS.update(newFinancialReport);
//        if (check) {
//            showAlert("Thành Công", "Đã sửa Thành Công", AlertType.CONFIRMATION);
//            updateFianReport();
//            clearFildReport();
//        } else {
//            showAlert("Thất Bai", "sửa Thất Bại", AlertType.ERROR);
//        }

    }

    private void clearFildReport() {
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
        if (dateFirst == null || dateLast == null) {
            showAlert("Lỗi", "Vui lòng chọn cả ngày bắt đầu và ngày kết thúc.", AlertType.ERROR);
            return;
        }

        // Kiểm tra xem ngày bắt đầu có nhỏ hơn hoặc bằng ngày kết thúc không
        if (dateFirst.isAfter(dateLast)) {
            showAlert("Lỗi", "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc.", AlertType.ERROR);
            return;
        }
        FinancialReportBUS financialReportBUS= new FinancialReportBUS();
        ArrayList<FinancialReport> financialReports = financialReportBUS.search(dateFirst, dateLast, "Tìm Theo Ngày");
        ObservableList<FinancialReport> observableBuildingList = FXCollections
                .observableArrayList(financialReports);
        table__view3.setItems(observableBuildingList);
    }

    public void resetDay() {
        date1.setValue(null);
        date2.setValue(null);

        FinancialReportBUS financialReportBUS = new FinancialReportBUS();
        financialReportsList.clear();
        ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();
        financialReportsList.addAll(financialReports);
        ObservableList<FinancialReport> observableList = FXCollections
                .observableArrayList(financialReportsList);
        table__view3.setItems(observableList);
    }


    @FXML
    void logOut(MouseEvent event) {
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
