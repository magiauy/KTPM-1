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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.bouncycastle.operator.bc.BcSignerOutputStream;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class BuildingManagerController implements Initializable {
    private static BuildingManagerController instance;
    public ImageView exportPDF;


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

    public TextField TxtField__P1__search = new TextField();
    public Label txtField__P1__1;
    public TextField TxtField__P2__search = new TextField();
    public TextField TxtField__P4__search = new TextField();
    public Button bnt__P1__search;
    public TextField TxtField__P3__search = new TextField();

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
    private ImageView importButton = new ImageView();
    @FXML
    void importExcel(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Thiết lập tiêu đề của cửa sổ File Chooser
        fileChooser.setTitle("Chọn file Excel");

        // Thiết lập bộ lọc để chỉ chấp nhận các file Excel
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx, *.xls)", "*.xlsx", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        // Hiển thị File Chooser và lấy file được chọn
        File selectedFile = fileChooser.showOpenDialog(importButton.getScene().getWindow());

        if (selectedFile != null) {
            // Xử lý file đã chọn ở đây
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("File đã chọn: " + filePath);
            FurnitureBUS fb = new FurnitureBUS();
            fb.importExcel(filePath);
        } else {
            // Người dùng đã hủy bỏ việc chọn file
            System.out.println("Không có file nào được chọn.");
        }

    }

    @FXML
    public void setExportPDF(MouseEvent event){
        if (!maPhieuDVField.getText().isEmpty()){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Chọn thư mục lưu file");
            Stage primaryStage = new Stage();
            // Hiển thị cửa sổ thư mục và lấy thư mục được chọn
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                ServiceTicketBUS.getInstance().exportDPF(selectedDirectory.getAbsolutePath(), maPhieuDVField.getText());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Xuất phiếu thành công.");

                // Hiển thị cửa sổ thông báo và chờ người dùng đóng
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập mã phiếu.");

            // Hiển thị cửa sổ thông báo và chờ người dùng đóng
            alert.showAndWait();
        }
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
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            BuildingManager buildingManager = buildingManagerBUS.getBuildingManagerById(ID);
            List<Apartment> countApartment = apartmentBUS.getApartmentByBuildingID(buildingManager.getBuildingId());
            int total = 0;

            for(Apartment apartment : countApartment){
                total += 1;
            }
            numberOfBuildings.setText(String.valueOf(total));

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
            ArrayList<Building> buildings = buildingBUS.getAll();
            HashMap<String, Integer> cityCounts = new HashMap<>();
            for (Building building : buildings) {
                String city = building.getCity_Building();
                cityCounts.put(city, cityCounts.getOrDefault(city, 0) + 1);
            }
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (String city : cityCounts.keySet()) {
                int count = cityCounts.get(city);
                pieChartData.add(new PieChart.Data(city + " (" + count + ")", count));
            }
            pieChart.setData(pieChartData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawBarChart() {
        if (barChart == null) {
            return;
        }

        try {
            int[][] genderAgeCount = new int[100][2];

            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            ArrayList<BuildingManager> buildingManagers = buildingManagerBUS.getAll();

            LocalDate currentDate = LocalDate.now();

            // Duyệt qua danh sách người quản lý tòa nhà và tính độ tuổi của mỗi người quản
            // lý
            for (BuildingManager buildingManager : buildingManagers) {
                LocalDate managersDOB = buildingManager.getDob();
                Period calculate = Period.between(managersDOB, currentDate);
                int managersAge = calculate.getYears();

                // Xác định giới tính của người quản lý (0 là nam, 1 là nữ)
                int genderIndex = buildingManager.getGender().equals("Nam") ? 0 : 1;

                // Cập nhật mảng hai chiều
                genderAgeCount[managersAge][genderIndex]++;
            }

            // Xóa các dữ liệu cũ trong biểu đồ
            barChart.getData().clear();

            // Thêm dữ liệu mới vào biểu đồ
            XYChart.Series<String, Number> maleSeries = new XYChart.Series<>();
            maleSeries.setName("Nam");
            XYChart.Series<String, Number> femaleSeries = new XYChart.Series<>();
            femaleSeries.setName("Nữ");

            for (int i = genderAgeCount.length - 1; i >= 0; i--) {
                if (genderAgeCount[i][0] > 0) {
                    maleSeries.getData().add(new XYChart.Data<>(String.valueOf(currentDate.getYear() - i), i));
                }
                if (genderAgeCount[i][1] > 0) {
                    femaleSeries.getData().add(new XYChart.Data<>(String.valueOf(currentDate.getYear() - i), i));
                }
            }

            barChart.getData().addAll(maleSeries, femaleSeries);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Label Regex__P1__1 = new Label();

    @FXML
    private Label Regex__P1__2 = new Label();

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

    public boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public boolean isString(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return !str.matches(".*\\d.*");
    }


    @FXML
    private TableColumn<Apartment,String> trangthai = new TableColumn<>();

    @FXML
    private TableView<Apartment> table__P1__1 = new TableView<>();

    private ObservableList<Apartment> apartmentObservableList;

    public ObservableList<Apartment> getApartmentList() {
        ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();
        BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
        List<BuildingManager> buildingManagerList = buildingManagerBUS.getAll();
        for (BuildingManager buildingManager : buildingManagerList) {
            if (buildingManager.getBuildingManagerId().equals(ID)) {
                ApartmentBUS apartmentBUS = new ApartmentBUS();
                List<Apartment> apartments = apartmentBUS.getApartmentByBuildingID(buildingManager.getBuildingId());
                apartmentObservableList.addAll(apartments);
                break;
            }
        }

        return apartmentObservableList;
    }

    @FXML
    private void exportExcel(){
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
        if (!monthlyRentBill.getMonthlyRentBillID().isEmpty()){
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Chọn thư mục lưu file");
            Stage primaryStage = new Stage();
            // Hiển thị cửa sổ thư mục và lấy thư mục được chọn
            File selectedDirectory = directoryChooser.showDialog(primaryStage);

            if (selectedDirectory != null) {
                MonthlyRentBillBUS.getInstance().XuatExcelPhieuThang(monthlyRentBill.getMonthlyRentBillID(), selectedDirectory.getAbsolutePath());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Xuất phiếu thành công.");

                // Hiển thị cửa sổ thông báo và chờ người dùng đóng
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập mã phiếu thu.");

            // Hiển thị cửa sổ thông báo và chờ người dùng đóng
            alert.showAndWait();
        }

    }

    private void refreshFormApartment() {
        TxtField__P1__1.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__3.setText("");
        TxtField__P1__4.setText("");
        TxtField__P1__5.setText("");
        comboBox__P1__3.getSelectionModel().clearSelection();
        Regex__P1__1.setText("");
        Regex__P1__2.setText("");
    }

    private void initApartment() {
        maCanHoTable.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        maToaNhaTable.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
        soPhongTable.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        dienTichTable.setCellValueFactory(new PropertyValueFactory<>("area"));
        soPhongNguTable.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        soPhongTamTable.setCellValueFactory(new PropertyValueFactory<>("bathrooms"));
        noiThatTable.setCellValueFactory(new PropertyValueFactory<>("furniture"));
        trangthai.setCellValueFactory(new PropertyValueFactory<>("status"));
        apartmentObservableList = getApartmentList();
        table__P1__1.setItems(apartmentObservableList);
    }

    @FXML
    void showApartment(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        TxtField__P1__1.setText(selectedApartment.getApartmentID());
        TxtField__P1__2.setText(selectedApartment.getRoomNumber());
        TxtField__P1__3.setText(String.valueOf(selectedApartment.getArea()));
        TxtField__P1__4.setText(String.valueOf(selectedApartment.getBedrooms()));
        TxtField__P1__5.setText(String.valueOf(selectedApartment.getBathrooms()));
        comboBox__P1__3.setValue(selectedApartment.getFurniture());
        Regex__P1__1.setText("");
        Regex__P1__2.setText("");
    }

    @FXML
    void suaCanHo(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        if (!isInteger(TxtField__P1__4.getText())){
            Regex__P1__1.setText("Only number");
        }
        if (!isInteger(TxtField__P1__5.getText())){
            Regex__P1__2.setText("Only number");
        }
        if (!isInteger(TxtField__P1__4.getText())||!isInteger(TxtField__P1__5.getText())){
            return;
        }
        selectedApartment.setApartmentID(TxtField__P1__1.getText());

        BuildingManagerBUS bus = new BuildingManagerBUS();
        List<BuildingManager> buildingManagers = bus.getAll();

        for (BuildingManager buildingManager : buildingManagers) {
            if (ID.equals(buildingManager.getBuildingManagerId())) {
                selectedApartment.setBuildingID(buildingManager.getBuildingId());
                break;
            }
        }
        selectedApartment.setRoomNumber(TxtField__P1__2.getText());
        selectedApartment.setArea(Double.parseDouble(TxtField__P1__3.getText()));
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
    void themCanHo(MouseEvent event) {
        try {
            if (!isInteger(TxtField__P1__4.getText())){
                Regex__P1__1.setText("phải là số");
            }
            if (!isInteger(TxtField__P1__5.getText())){
                Regex__P1__2.setText("phải là số");
            }
            if (!isInteger(TxtField__P1__4.getText())||!isInteger(TxtField__P1__5.getText())){
                return;
            }
            Apartment newApartment = new Apartment();
            BuildingManagerBUS bus = new BuildingManagerBUS();
            List<BuildingManager> buildingManagers = bus.getAll();

            for (BuildingManager buildingManager : buildingManagers) {
                if (ID.equals(buildingManager.getBuildingManagerId())) {
                    newApartment.setBuildingID(buildingManager.getBuildingId());
                }
            }
            newApartment.setRoomNumber(TxtField__P1__2.getText());
            newApartment.setArea(Double.parseDouble(TxtField__P1__3.getText()));
            newApartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
            newApartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
            if (comboBox__P1__3.getSelectionModel().getSelectedItem()==null){
                newApartment.setFurniture("Cơ bản");
            } else {
                newApartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());
            }

            newApartment.setStatus("Còn trống");

            ApartmentBUS apartmentBUS = new ApartmentBUS();
            apartmentBUS.add(newApartment);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Đã tạo căn hộ với mã căn hộ: " + newApartment.getApartmentID());
            alert.showAndWait();

            apartmentObservableList.add(newApartment);
            refreshFormApartment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void xoaCanHo(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        if (selectedApartment != null && !selectedApartment.getStatus().equals("Đã được thuê")) {
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            boolean deleteSuccess = apartmentBUS.delete(selectedApartment);
            if (deleteSuccess) {
                apartmentObservableList.remove(selectedApartment);
                table__P1__1.refresh();
                refreshFormApartment();
            } else {
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText("Căn hộ "+selectedApartment.getApartmentID());
            alert.setContentText("Không thể xóa căn hộ đang được thuê.");
            alert.showAndWait();
        }
    }

    @FXML
    void timCanHo(KeyEvent event) {
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        ArrayList<Apartment> apartments = apartmentBUS.searchApartments(TxtField__P1__search.getText(), this.ID);
        ObservableList<Apartment> apartmentSearch = FXCollections.observableArrayList(apartments);
        apartmentObservableList.setAll(apartmentSearch);
    }

    //cu dan

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
    private TableColumn<Tenant, String> cCCDTable = new TableColumn<>();

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
    private ComboBox<String> Combobox__P2_1__2 = new ComboBox<>();

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

    @FXML
    private Label Regex__P2__1 = new Label();

    @FXML
    private Label Regex__P2__2 = new Label();

    @FXML
    private Label Regex__P3__1 = new Label();

    @FXML
    private Label Regex__P3__2 = new Label();

    @FXML
    private Label Regex__P3__3 = new Label();


    private ObservableList<Tenant> tenantObservableList;

    public ObservableList<Tenant> getTenantObservableList() {
        ObservableList<Tenant> tenantObservableLists = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        List<Tenant> tenants = tenantBUS.getTenantWithBuildingID(this.ID);
        tenantObservableLists.addAll(tenants);
        return tenantObservableLists;
    }



    private ObservableList<Cohabitant> cohabitantObservableList;;

    public ObservableList<Cohabitant> getCohabitantObservableList() {
        ObservableList<Cohabitant> cohabitantObservableLists = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        CohabitantBUS cohabitantBUS = new CohabitantBUS();
        List<Tenant> tenants = tenantBUS.getTenantWithBuildingID(this.ID);
        for (Tenant tenant : tenants) {
            List<Cohabitant> cohabitants = cohabitantBUS.getCohabitantsWithTenantId(tenant.getTenantID());
            cohabitantObservableLists.addAll(cohabitants);
        }

        return cohabitantObservableLists;
    }

    public void initTenant() {
        maKhachHangTable_1.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        hoKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tenKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
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
        Combobox__P2_1__2.getSelectionModel().clearSelection();
        Regex__P3__1.setText("");
        Regex__P3__2.setText("");
        Regex__P3__3.setText("");
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
        Regex__P2__1.setText("");
        Regex__P2__2.setText("");
    }

    @FXML
    void showCohabitant(MouseEvent event) {
        Cohabitant selectedCohabitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        Combobox__P2_1__2.setValue(selectedCohabitant.getTenantID());
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
        if (TxtField__P2__2.getText().equals("")){
            Regex__P2__1.setText("Họ không được bỏ trống");
        } else if (!isString(TxtField__P2__2.getText())) {
            Regex__P2__1.setText("Họ không được chứa số");
        }
        if (TxtField__P2__2.getText().equals("")){
            Regex__P2__2.setText("Tên không được bỏ trống");
        } else if (!isString(TxtField__P2__2.getText())){
            Regex__P2__2.setText("Tên không được chứa số");
        }

        if (TxtField__P2__2.getText().equals("")||TxtField__P2__2.getText().equals("")||!isString(TxtField__P2__2.getText())||!isString(TxtField__P2__2.getText())){
            return;
        }
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
            System.err.println("Không thể cập nhật khách hàng trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themCuDan(ActionEvent event) {
        try {
            if (Combobox__P2_1__2.getSelectionModel().getSelectedItem().equals("")){
                Regex__P3__1.setText("Không được bỏ trống");
            }
            if (TxtField__P2_1__3.getText().equals("")) {
                Regex__P3__2.setText("Họ không được bỏ trống");
            }
            if(TxtField__P2_1__4.getText().equals("")) {
                Regex__P3__3.setText("Tên không được bỏ trống");
            }

            if (TxtField__P2_1__2.getText().equals("")||TxtField__P2_1__3.getText().equals("")||TxtField__P2_1__4.getText().equals("")) {
                return;
            }
            Cohabitant cohabitant = new Cohabitant();
            cohabitant.setTenantID(TxtField__P2_1__2.getText());
            cohabitant.setFirstName(TxtField__P2_1__3.getText());
            cohabitant.setLastName(TxtField__P2_1__4.getText());
            cohabitant.setPhoneNumber(TxtField__P2_1__5.getText());
            cohabitant.setDateOfBirthDay(TxtField__P2_1__41.getValue());
            cohabitant.setGender(comboBox__P2_1__3.getSelectionModel().getSelectedItem());
            cohabitant.setCitizenIdentityCard(TxtField__P2_1__51.getText());

            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            cohabitantBUS.add(cohabitant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Đã thêm cư dân với mã " + cohabitant.getCohabitantID());
            alert.showAndWait();

            cohabitantObservableList.add(cohabitant);
            refreshFormCohabitant();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void themKhachHang(ActionEvent event) {
        try {
            if (TxtField__P2__2.getText().equals("")){
                Regex__P2__1.setText("Họ không được bỏ trống");
            } else if (!isString(TxtField__P2__2.getText())) {
                Regex__P2__1.setText("Họ không được chứa số");
            }
            if (TxtField__P2__2.getText().equals("")){
                Regex__P2__2.setText("Tên không được bỏ trống");
            } else if (!isString(TxtField__P2__2.getText())){
                Regex__P2__2.setText("Tên không được chứa số");
            }

            if (TxtField__P2__2.getText().equals("")||TxtField__P2__2.getText().equals("")||!isString(TxtField__P2__2.getText())||!isString(TxtField__P2__2.getText())){
                return;
            }
            Tenant tenant = new Tenant();
            tenant.setFirstName(TxtField__P2__2.getText());
            tenant.setLastName(TxtField__P2__3.getText());
            tenant.setPhoneNumber(TxtField__P2__4.getText());
            tenant.setDateOfBirthDay(TxtField__P2__5.getValue());
            tenant.setGender(comboBox__P2__3.getSelectionModel().getSelectedItem());
            tenant.setCitizenIdentityCard(TxtField__P2__51.getText());

            TenantBUS tenantBUS = new TenantBUS();
            tenantBUS.add(tenant);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Đã thêm khách hàng với mã: " + tenant.getTenantID());
            alert.showAndWait();

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
            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            boolean deleteSuccess = cohabitantBUS.delete(selectedCohatitant);
            if (deleteSuccess) {
                cohabitantObservableList.remove(selectedCohatitant);
                table__P2_1__1.refresh();
                refreshFormCohabitant();
            } else {
                System.err.println("Không thể xóa cư dân từ cơ sở dữ liệu.");
            }
        }
    }

//    @FXML
//    void xoaKhachHang(ActionEvent event) {
//        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
//        if (selectedTenant != null) {
//            TenantBUS tenantBUS = new TenantBUS();
//            boolean deleteSuccess = tenantBUS.delete(selectedTenant);
//            if (deleteSuccess) {
//                tenantObservableList.remove(selectedTenant);
//                table__P2__1.refresh();
//                refreshFormTenant();
//            } else {
//                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
//            }
//        }
//    }

    @FXML
    void timKhachHang(KeyEvent event) {
        TenantBUS tenantBUS = new TenantBUS();
        ArrayList<Tenant> tenants = tenantBUS.searchTenants(TxtField__P2__search.getText(), this.ID);
        ObservableList<Tenant> tenantSearch = FXCollections.observableArrayList(tenants);
        tenantObservableList.setAll(tenantSearch);
    }

    @FXML
    void timCuDan(KeyEvent event) {
        CohabitantBUS cohabitantBUS = new CohabitantBUS();
        ArrayList<Cohabitant> cohabitants = cohabitantBUS.searchCohabitants(TxtField__P2_1__search.getText(), this.ID);
        ObservableList<Cohabitant> cohabitantSearch = FXCollections.observableArrayList(cohabitants);
        cohabitantObservableList.setAll(cohabitantSearch);
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
    private ComboBox<String> Combobox__P3__4 = new ComboBox<>();

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
    private TextField TxtField__P3__51 = new TextField();

    @FXML
    private TextField TxtField__P3__6 = new TextField();

    @FXML
    private Button bnt__P3__add;

    @FXML
    private Button bnt__P3__delete;

    @FXML
    private Button bnt__P3__update;

    @FXML
    private Button btn_huyLoc;

    @FXML
    private Button btn_loc;

    @FXML
    private ComboBox<String> comboBox__P3__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P3__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P3__3 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox_P3_status = new ComboBox<>();

    @FXML
    private DatePicker datePicker__P3 = new DatePicker();

    @FXML
    private DatePicker Date__P3__1 = new DatePicker();

    @FXML
    private DatePicker Date__P3__2 = new DatePicker();

    @FXML
    private TableView<MonthlyRentBill> table__P3__1 = new TableView<>();

    private ObservableList<MonthlyRentBill> monthlyRentBillObservableList;

    public ObservableList<MonthlyRentBill> getMonthlyRentBillObservableList() {
        ObservableList<MonthlyRentBill> monthlyRentBillsObservableLists = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        List<Tenant> tenants = tenantBUS.getTenantWithBuildingID(this.ID);
        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        for (Tenant tenant : tenants) {
            List<MonthlyRentBill> monthlyRentBills = monthlyRentBillBUS
                    .getMonthlyRentBillsWithTenantId(tenant.getTenantID());
            monthlyRentBillsObservableLists.addAll(monthlyRentBills);
        }
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
        Combobox__P3__4.setValue(monthlyRentBill.getApartmentID());
        TxtField__P3__5.setText(String.valueOf(monthlyRentBill.getRepaymentPeriod()));
        TxtField__P3__51.setText(monthlyRentBill.getStatus());
        comboBox_P3_status.setValue(monthlyRentBill.getStatus());
    }

    public void refreshFormMonthlyRentBill() {
        TxtField__P3__1.setText("");
        TxtField__P3__2.setText("");
        TxtField__P3__3.setText("");
        datePicker__P3.setValue(null);
        TxtField__P3__5.setText("");
        TxtField__P3__6.setText("");
        TxtField__P3__51.setText("");
        comboBox_P3_status.getSelectionModel().clearSelection();
        Combobox__P3__4.setValue(null);
    }

    @FXML
    void suaPhieuThu(ActionEvent event) {
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
        monthlyRentBill.setApartmentID(Combobox__P3__4.getValue());
        monthlyRentBill.setRepaymentPeriod(Integer.parseInt(TxtField__P3__5.getText()));
        monthlyRentBill.setStatus(comboBox_P3_status.getSelectionModel().getSelectedItem());
        monthlyRentBill.setTotalPayment(Double.parseDouble(TxtField__P3__6.getText()));
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
            String apartmentID = Combobox__P3__4.getSelectionModel().getSelectedItem();
            LeaseAgreementBUS checkApartmentID = new LeaseAgreementBUS();
            List<LeaseAgreement> leaseAgreementList = checkApartmentID.getAll();
            for (LeaseAgreement list: leaseAgreementList) {
                if (list.getApartmentID().equals(apartmentID)){
                    monthlyRentBill.setApartmentID(apartmentID);
                    monthlyRentBill.setTenantID(list.getTenantID());
                    monthlyRentBill.setDate(LocalDate.now());

                    monthlyRentBill.setRepaymentPeriod(Integer.parseInt(TxtField__P3__5.getText()));

                    Double totalPayment = list.getMonthlyRent();
                    monthlyRentBill.setTotalPayment(totalPayment);
                    monthlyRentBill.setStatus("Chưa thanh toán");

                    MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
                    monthlyRentBillBUS.add(monthlyRentBill);

                    monthlyRentBillObservableList.add(monthlyRentBill);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã tạo phiếu thu với mã phiếu: " + monthlyRentBill.getMonthlyRentBillID());
                    alert.showAndWait();
                    table__P3__1.setItems(monthlyRentBillObservableList);
                    refreshFormMonthlyRentBill();

                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void timPhieuThu(KeyEvent event) {
        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        ArrayList<MonthlyRentBill> monthlyRentBills = monthlyRentBillBUS.searchMonthlyRentBills(TxtField__P3__search.getText(), this.ID);
        ObservableList<MonthlyRentBill> search = FXCollections.observableArrayList(monthlyRentBills);
        monthlyRentBillObservableList.setAll(search);
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

    @FXML
    void huyLocPhieuThu(MouseEvent event) {
            monthlyRentBillObservableList = getMonthlyRentBillObservableList();
            table__P3__1.setItems(monthlyRentBillObservableList);
    }

    @FXML
    void locPhieuThu(MouseEvent event) {
        if (Date__P3__1!=null&&Date__P3__2!=null) {
            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            ArrayList<MonthlyRentBill> monthlyRentBills = monthlyRentBillBUS.fill(this.ID, Date__P3__1.getValue(), Date__P3__2.getValue());
            ObservableList<MonthlyRentBill> fill = FXCollections.observableArrayList(monthlyRentBills);
            monthlyRentBillObservableList.setAll(fill);
        }
    }


    // Dich Vu

    @FXML
    private TableColumn<Service, String> madv = new TableColumn<>();

    @FXML
    private TableColumn<Service, String> tenDV = new TableColumn<>();

    @FXML
    private TableColumn<Service, Double> giaDV = new TableColumn<>();

    @FXML
    private TableColumn<Service, String> donVi = new TableColumn<>();

    @FXML
    private TableColumn<Service, String> loai = new TableColumn<>();

    @FXML
    private TableView<Service> table__P4__11 = new TableView<>();

    @FXML
    private ComboBox<String> fill_type = new ComboBox<>();

    @FXML
    private TextField TxtField__P4__search1 = new TextField();

    @FXML
    private ComboBox<String> comboBox__P4__11 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P4__21 = new ComboBox<>();

    @FXML
    private ComboBox<String> combox_loaidv = new ComboBox<>();

    @FXML
    private TextField TxtField__P4__11 = new TextField();

    @FXML
    private TextField TxtField__P4__31 = new TextField();

    @FXML
    private TextField TxtField__P4__51 = new TextField();

    @FXML
    private TextField TxtField__P4__61 = new TextField();

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

    public void showcomboboxService1() {
        Platform.runLater(() -> {
            if (combox_loaidv != null) {
                ObservableList<String> genders = FXCollections.observableArrayList(
                        "Tất Cả",
                        "mobile",
                        "fixed");
                combox_loaidv.setItems(genders);
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

    private boolean containsNumber(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidNumber(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                return false; // Nếu không phải số, dấu chấm hoặc dấu trừ, trả về false
            }
        }
        return true;
    }

    public void handleAddService() {
        String name = TxtField__P4__31.getText();
        Double pricePerUnitText = Double.parseDouble(TxtField__P4__51.getText());
        String unit = TxtField__P4__61.getText();
        String type = fill_type.getValue();

        String revenueInput = TxtField__P4__51.getText().replaceAll(",", "");
        if (name.isEmpty() || unit.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }
        if (containsNumber(name)) {
            showAlert("Lỗi", "Tên không được chứa số.", AlertType.ERROR);
            return;
        }
        if (!isValidNumber(revenueInput)) {
            showAlert("Lỗi", "Vui Lòng Nhập Số Giá Dịch Vụ.", AlertType.ERROR);
            return;
        }

        Service newService = new Service();
//        newService.setServiceID(serviceID);
        newService.setName(name);
        newService.setPricePerUnit(pricePerUnitText);
        newService.setUnit(unit);
        newService.setType(type);
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
            showAlert("Lỗi", "Không có dịch vụ nào chọn để sửa", AlertType.ERROR);
            return;
        }
        String serviceID = TxtField__P4__11.getText();
        String name = TxtField__P4__31.getText();
        Double pricePerUnitText = Double.parseDouble(TxtField__P4__51.getText());
        String unit = TxtField__P4__61.getText();
        String type = fill_type.getValue();

        String revenueInput = TxtField__P4__51.getText().replaceAll(",", "");
        if (serviceID.isEmpty() || name.isEmpty() || unit.isEmpty() || type == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }
        if (containsNumber(name)) {
            showAlert("Lỗi", "Tên không được chứa số.", AlertType.ERROR);
            return;
        }
        if (!isValidNumber(revenueInput)) {
            showAlert("Lỗi", "Vui Lòng Nhập Số Giá Dịch Vụ.", AlertType.ERROR);
            return;
        }

        Service newService = new Service();
        newService.setServiceID(serviceID);
        newService.setName(name);
        newService.setPricePerUnit(pricePerUnitText);
        newService.setUnit(unit);
        newService.setType(type);
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean isSuccess = serviceBUS.update(newService);

        if (isSuccess) {
            showAlert("Thành Công", "Sửa Thành Công.", AlertType.CONFIRMATION);
            initService();
        } else {
            showAlert("Lỗi", "Không Thể Sửa.", AlertType.ERROR);
        }
    }

    public void handleSelectType() {

        String type = combox_loaidv.getValue();
        if (type.equals("Tất Cả")) {
            ServiceBUS serviceBUS = new ServiceBUS();
            ArrayList<Service> services = serviceBUS.getAll();
            ServiceList.addAll(services);
            ObservableList<Service> observableBuildingList = FXCollections
                    .observableArrayList(services);
            table__P4__11.setItems(observableBuildingList);

        } else {
            ServiceBUS serviceBUS = new ServiceBUS();
            ArrayList<Service> services = serviceBUS.search(type, "Lọc Theo Loại");
            ObservableList<Service> observableBuildingList = FXCollections
                    .observableArrayList(services);
            table__P4__11.setItems(observableBuildingList);
        }

    }
    // Phiếu Dichj Vụ

    @FXML
    private TableColumn<ServiceTicket, String> maPDV = new TableColumn<>();

    @FXML
    private ComboBox<String> maPThuCombobox = new ComboBox<>();

    @FXML
    private TableColumn<ServiceTicket, String> maPhieuThu = new TableColumn<>();

    @FXML
    private TableColumn<ServiceTicket, String> maDichVu = new TableColumn<>();

    @FXML
    private TableColumn<ServiceTicket, Double> soLuong = new TableColumn<>();

    @FXML
    private TableColumn<ServiceTicket, Double> thanhTien = new TableColumn<>();

    @FXML
    private TableColumn<ServiceTicket, LocalDate> ngayTaoPhieu = new TableColumn<>();

    @FXML
    private TableColumn<ServiceTicket, String> ghiChu = new TableColumn<>();

    @FXML
    private TextField maPhieuDVField = new TextField();

    @FXML
    private TextField maPhieuThuField = new TextField();

    @FXML
    private TextField maDichVuField = new TextField();

    @FXML
    private TextField soLuongField = new TextField();

    @FXML
    private TextField thanhTien1Field = new TextField();

    @FXML
    private DatePicker ngayGhiPicker = new DatePicker();

    @FXML
    private TextArea ghiChuArea = new TextArea();

    @FXML
    private ComboBox<String> maPhieuThuCombobox = new ComboBox<>();

    @FXML
    private ComboBox<String> maDichVuCombobox = new ComboBox<>();

    @FXML
    private TextField search_phieudv = new TextField();

    @FXML
    private DatePicker dateStart = new DatePicker();

    @FXML
    private DatePicker dateEnd = new DatePicker();

    @FXML
    private Button searchPDV = new Button();

    @FXML
    private Button resetPDV = new Button();

    @FXML
    private TableView<ServiceTicket> table__sericetiket = new TableView<>();

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
                    maPhieuDVField.setText(selectedRow.getServiceTicketID());
                    maPhieuThuField.setText(selectedRow.getMonthlyRentBillID());
                    maDichVuField.setText(selectedRow.getServiceID());
                    soLuongField.setText(String.valueOf(selectedRow.getQuantity()));
                    ngayGhiPicker.setValue(selectedRow.getDate());
                    ghiChuArea.setText(selectedRow.getNote());
                    serviceTicketdelete = selectedRow;
                }
            }
        });
    }

    public void handleAddServiceTicket() {

        String maDichVu = maPhieuThuCombobox.getSelectionModel().getSelectedItem();
        String maPhieuThu = maDichVuField.getText();
        Double soLuong = Double.parseDouble(soLuongField.getText());
        Double thanhTien = 0.0;
        List<Service> serviceList = ServiceBUS.getInstance().getAll();
        for (Service service : serviceList) {
            if (service.getServiceID().equals(maPhieuThu)) {
                thanhTien = service.getPricePerUnit()*soLuong;
            }
        }
        LocalDate ngayGhi = ngayGhiPicker.getValue();
        String ghiChu = ghiChuArea.getText();

        if (maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        ServiceTicket service = new ServiceTicket();
        service.setMonthlyRentBillID(maDichVu);
        service.setServiceID(maPhieuThu);
        service.setQuantity(soLuong);
        service.setTotalAmount(thanhTien);
        service.setDate(ngayGhi);
        service.setNote(ghiChu);

        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        boolean addResult = serviceTicketBUS.add(service);

        if (addResult) {
            showAlert("Thành Công", "Thêm Phiếu Dịch Vụ Thành Công: "+ service.getServiceTicketID(), AlertType.CONFIRMATION);
            initServiceTicket();
            maDichVuField.setText("");
            soLuongField.setText("");
            ngayGhiPicker.setValue(null);
            ghiChuArea.setText("");
            maPhieuThuCombobox.setValue(null);
        } else {
            showAlert("Thất Bại", "Không Thể Thêm Phiếu Dịch Vụ", AlertType.ERROR);
        }

        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        List<MonthlyRentBill> monthlyRentBillList = monthlyRentBillBUS.getAll();


        for (int i = 0; i < monthlyRentBillList.size(); i++) {
            MonthlyRentBill monthlyRentBill = monthlyRentBillList.get(i);
            LocalDate sameDayNextMonth = monthlyRentBill.getDate().plusMonths(1);
            if (monthlyRentBill.getMonthlyRentBillID().equals(service.getMonthlyRentBillID()) &&
                    (service.getDate().isBefore(sameDayNextMonth) || service.getDate().isEqual(sameDayNextMonth)) &&
                    (service.getDate().isAfter(monthlyRentBill.getDate()) || service.getDate().isEqual(monthlyRentBill.getDate()))) {

                monthlyRentBill.setTotalPayment(monthlyRentBill.getTotalPayment() + service.getTotalAmount());


                // Cập nhật dữ liệu trong ObservableList
                MonthlyRentBillBUS monthlyRentBillBUS1 = new MonthlyRentBillBUS();
                boolean updateSuccess = monthlyRentBillBUS1.update(monthlyRentBill);
                if (updateSuccess) {
                    monthlyRentBillObservableList.set(i, monthlyRentBill);
                    table__P3__1.refresh();
                } else {
                    System.err.println("Không thể cập nhật phiếu thu trong cơ sở dữ liệu.");
                }
                break;
            }
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

        if (maPDV.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        String maPDVCu = serviceTicketdelete.getServiceTicketID();

        ServiceTicket service = new ServiceTicket();
        service.setServiceTicketID(maPDVCu); // Sử dụng ID của bản ghi cần cập nhật
        service.setMonthlyRentBillID(maDichVu);
        service.setServiceID(maPhieuThu);
        service.setQuantity(soLuong);
        service.setTotalAmount(thanhTien);
        service.setDate(ngayGhi);
        service.setNote(ghiChu);

        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        boolean updateResult = serviceTicketBUS.update(service);

        if (updateResult) {
            showAlert("Thành Công", "Cập Nhật Phiếu Dịch Vụ Thành Công", AlertType.CONFIRMATION);
            initServiceTicket();
        } else {
            showAlert("Thất Bại", "Không Thể Cập Nhật Phiếu Dịch Vụ", AlertType.ERROR);
        }
    }

    public void searchDayPPDV() {
        LocalDate day1 = dateStart.getValue();
        LocalDate day2 = dateEnd.getValue();
        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        ArrayList<ServiceTicket> serviceTickets = serviceTicketBUS.search(day1, day2, "Tìm Theo Ngày");
        ObservableList<ServiceTicket> observableBuildingList = FXCollections
                .observableArrayList(serviceTickets);
        table__sericetiket.setItems(observableBuildingList);
    }

    public void resetDay() {
        ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
        ArrayList<ServiceTicket> serviceTickets = serviceTicketBUS.getAll();
        serviceTicketslist.addAll(serviceTickets);
        ObservableList<ServiceTicket> observableBuildingList = FXCollections
                .observableArrayList(serviceTickets);
        table__sericetiket.setItems(observableBuildingList);
    }

    public void keyenter() {
        Platform.runLater(() -> {
            search_phieudv.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    handlesearchId();

                }
            });
        });

    }

    public void handlesearchId() {
        String id = search_phieudv.getText();
        if (id.equals("")) {
            ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
            ArrayList<ServiceTicket> serviceTickets = serviceTicketBUS.getAll();
            serviceTicketslist.addAll(serviceTickets);
            ObservableList<ServiceTicket> observableBuildingList = FXCollections
                    .observableArrayList(serviceTickets);
            table__sericetiket.setItems(observableBuildingList);
        } else {
            ServiceTicketBUS serviceTicketBUS = new ServiceTicketBUS();
            ArrayList<ServiceTicket> serviceTickets = serviceTicketBUS.searchID(id, "Tìm Theo Mã");
            ObservableList<ServiceTicket> observableBuildingList = FXCollections
                    .observableArrayList(serviceTickets);
            table__sericetiket.setItems(observableBuildingList);
        }
    }

    // Phạt
    @FXML
    private TableView<Violation> tableViolation = new TableView<>();

    @FXML
    private TableColumn<Violation, String> maphat = new TableColumn<>();

    @FXML
    private TableColumn<Violation, String> vipham = new TableColumn<>();

    @FXML
    private TableColumn<Violation, Double> tienphat = new TableColumn<>();
    @FXML
    private TextField maPhatField = new TextField();
    @FXML
    private TextField viphamField = new TextField();
    @FXML
    private TextField tienPhatField = new TextField();
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

        confirmAndDelete(violationdelete, "Bạn có chắc chắn muốn hình phạt này không?", () -> {
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
        });
    }

    public void handleUpdateViolation() {
        if (violationdelete == null) {
            showAlert("Lỗi", "Không có vi phạm nào được chọn để cập nhật", AlertType.ERROR);
            return;
        }

        String newViolationID = maPhatField.getText();
        String newName = viphamField.getText();
        Double newPrice = Double.parseDouble(tienPhatField.getText());
        if (newName.isEmpty() || newViolationID.isEmpty() || newPrice == null) {
            showAlert("Lỗi", "Tên vi phạm không được để trống", AlertType.ERROR);
            return;
        }

        if (containsNumber(newName)) {
            showAlert("Lỗi", "Tên Không Được Nhập Bằng Số", AlertType.ERROR);
            return;
        }
        String revenueInput1 = tienPhatField.getText().replaceAll(",", "");

        if (!isValidNumber(revenueInput1)) {
            showAlert("Lỗi", "Tiền Phạt Vui Lòng Nhập Bằng Số", AlertType.ERROR);
            return;
        }

        Violation newViolation = new Violation();
        newViolation.setViolationID((newViolationID));
        newViolation.setName(newName);
        newViolation.setPrice(newPrice);
        ViolationBUS violationBUS = new ViolationBUS();
        boolean insertResult = violationBUS.update(newViolation);
        if (insertResult) {
            showAlert("Thành Công", "Sửa vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();
            maPhatField.clear();
            viphamField.clear();
            tienPhatField.clear();
        } else {
            showAlert("Thất Bại", "Không thể thêm vi phạm", AlertType.ERROR);
        }
    }

    public void handleAddViolation() {
//
//        String newViolationID = maPhatField.getText();
        String newName = viphamField.getText();
        Double newPrice = Double.parseDouble(tienPhatField.getText());
        if (newName.isEmpty()) {
            showAlert("Lỗi", "Tên vi phạm không được để trống", AlertType.ERROR);
            return;
        }

        if (newName.isEmpty() || newPrice == null) {
            showAlert("Lỗi", "Tên vi phạm không được để trống", AlertType.ERROR);
            return;
        }

        if (containsNumber(newName)) {
            showAlert("Lỗi", "Tên Không Được Nhập Bằng Số", AlertType.ERROR);
            return;
        }
        String revenueInput1 = tienPhatField.getText().replaceAll(",", "");

        if (!isValidNumber(revenueInput1)) {
            showAlert("Lỗi", "Tiền Phạt Vui Lòng Nhập Bằng Số", AlertType.ERROR);
            return;
        }

        Violation newViolation = new Violation();
//        newViolation.setViolationID((newViolationID));
        newViolation.setName(newName);
        newViolation.setPrice(newPrice);
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
    private TableView<ViolationTicket> tableviolationticket = new TableView<>();
    @FXML
    private TableColumn<ViolationTicket, String> mxPP = new TableColumn<>();
    @FXML
    private TableColumn<ViolationTicket, String> mxP = new TableColumn<>();

    @FXML
    private TableColumn<ViolationTicket, String> mxPThu = new TableColumn<>();
    @FXML
    private TableColumn<ViolationTicket, Integer> mxSoLuong = new TableColumn<>();
    @FXML
    private TableColumn<ViolationTicket, Double> mxThanhTien = new TableColumn<>();
    @FXML
    private TableColumn<ViolationTicket, LocalDate> mxNgayghi = new TableColumn<>();
    @FXML
    private TableColumn<ViolationTicket, String> mxghichu = new TableColumn<>();
    @FXML
    private TextField maPPField = new TextField();
    @FXML
    private TextField maPThuField = new TextField();

    @FXML
    private TextField maphatfied = new TextField();

    @FXML
    private TextField maSluongField1 = new TextField();

    @FXML
    private DatePicker ngayGhiPPField = new DatePicker();
    @FXML
    private TextArea ghiChuPPField = new TextArea();

    @FXML
    private TextField price_start = new TextField();

    @FXML
    private TextField price_end = new TextField();

    private ObservableList<ViolationTicket> violationsList;
    private ViolationTicket violationTicketdelete;
    // Page 0
    @FXML
    private Text cccdnfor = new Text();
    @FXML
    private Text dobnfor = new Text();
    @FXML
    private Text gendernfor = new Text();
    @FXML
    private Text IDInfor = new Text();
    @FXML
    private Text nameInfor = new Text();
    @FXML
    private Text phonenfor = new Text();
    @FXML
    private Label datePage0 = new Label();

    public void loadPage0() {
        TimeNow();
        updateInfor();
        totalOfBuldings();
        updatePieChart();
        drawBarChart();
        datePage0.setText(LocalDate.now().toString());
    }

    public void updateInfor() {
        BuildingManagerBUS.getInstance().setInfor(IDInfor, nameInfor, phonenfor, dobnfor, gendernfor, cccdnfor,
                BuildingManagerController.getInstance().getID());
    }

    public void initViolationTicket() {
        violationsList = FXCollections.observableArrayList();
        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        ArrayList<ViolationTicket> violationTickets = violationTicketBUS.getAll();
        violationsList.addAll(violationTickets);
        ObservableList<ViolationTicket> observableList = FXCollections.observableArrayList(violationsList);
        mxPP.setCellValueFactory(new PropertyValueFactory<>("violationTicketID"));
        mxP.setCellValueFactory(new PropertyValueFactory<>("violationID"));
        mxPThu.setCellValueFactory(new PropertyValueFactory<>("monthlyRentBillID"));
        mxSoLuong.setCellValueFactory(new PropertyValueFactory<>("quantity"));
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
                    maPPField.setText(selectedRow.getViolationTicketID());
                    maphatfied.setText(selectedRow.getViolationID());
                    maPThuCombobox.setValue(selectedRow.getMonthlyRentBillID());
                    maSluongField1.setText(String.valueOf(selectedRow.getQuantity()));
                    ngayGhiPPField.setValue(selectedRow.getDate());
                    ghiChuPPField.setText(selectedRow.getNote());
                    violationTicketdelete = selectedRow;
                    System.out.println(selectedRow.getPrice());
                }
            }
        });
    }

    public void handleDeleteViolationTicket() {
        if (violationTicketdelete == null) {
            showAlert("Lỗi", "Không có phiếu vi phạm nào được chọn để xóa", AlertType.ERROR);
            return;
        }

        confirmAndDelete(violationTicketdelete, "Bạn có chắc xóa phiếu vi pham này không ", () -> {
            try {
                ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
                boolean deleteResult = violationTicketBUS
                        .delete(violationTicketdelete);

                if (deleteResult) {
                    showAlert("Thành Công", "Xóa phiếu vi phạm thành công", AlertType.CONFIRMATION);
                    initViolationTicket();
                } else {
                    showAlert("Thất Bại", "Không thể xóa phiếu vi phạm", AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("Lỗi", "Đã xảy ra lỗi khi xóa phiếu vi phạm: " + e.getMessage(), AlertType.ERROR);
                e.printStackTrace();
            }
        });
    }

    public void handleAddViolationTicket() {
        String maPhieuThu = maphatfied.getText();
        String maDichVu = maPThuCombobox.getSelectionModel().getSelectedItem();
        int soLuong = Integer.parseInt(maSluongField1.getText());
        LocalDate ngayGhi = ngayGhiPPField.getValue();
        String ghiChu = ghiChuPPField.getText();
        Double thanhTien = 0.0;
        ViolationBUS violationBUS = new ViolationBUS();
        List<Violation> violationList = violationBUS.getAll();
        for (Violation violation1: violationList) {
            if (violation1.getViolationID().equals(maPhieuThu)) {
                thanhTien = soLuong*violation1.getPrice();
                break;
            }
        }

        if (maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        ViolationTicket violationTickets = new ViolationTicket();
        violationTickets.setMonthlyRentBillID(maDichVu);
        violationTickets.setViolationID(maPhieuThu);
        violationTickets.setQuantity(soLuong);
        violationTickets.setDate(ngayGhi);
        violationTickets.setPrice(thanhTien);
        violationTickets.setNote(ghiChu);

        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        boolean updateResult = violationTicketBUS.add(violationTickets);

        if (updateResult) {
            showAlert("Thành Công", "Thêm phiếu vi phạm thành công: "+violationTickets.getViolationTicketID(), AlertType.CONFIRMATION);
            initViolationTicket();
            System.out.println(violationTickets);

        } else {
            showAlert("Thất Bại", "Không thể thêm phiếu vi phạm", AlertType.ERROR);
        }

        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        List<MonthlyRentBill> monthlyRentBillList = monthlyRentBillBUS.getAll();


        for (int i = 0; i < monthlyRentBillList.size(); i++) {
            MonthlyRentBill monthlyRentBill = monthlyRentBillList.get(i);
            LocalDate sameDayNextMonth = monthlyRentBill.getDate().plusMonths(1);
            if (monthlyRentBill.getMonthlyRentBillID().equals(violationTickets.getMonthlyRentBillID()) &&
                    (violationTickets.getDate().isBefore(sameDayNextMonth) || violationTickets.getDate().isEqual(sameDayNextMonth)) &&
                    (violationTickets.getDate().isAfter(monthlyRentBill.getDate()) || violationTickets.getDate().isEqual(monthlyRentBill.getDate()))) {


                monthlyRentBill.setTotalPayment(monthlyRentBill.getTotalPayment() + violationTickets.getPrice());


                // Cập nhật dữ liệu trong ObservableList
                MonthlyRentBillBUS monthlyRentBillBUS1 = new MonthlyRentBillBUS();
                boolean updateSuccess = monthlyRentBillBUS1.update(monthlyRentBill);
                if (updateSuccess) {
                    monthlyRentBillObservableList.set(i, monthlyRentBill);
                    table__P3__1.refresh();
                } else {
                    System.err.println("Không thể cập nhật phiếu thu trong cơ sở dữ liệu.");
                }
                break;
            }
        }

    }

    public void handleUpdateViolationTicket() {
        if (violationTicketdelete == null) {
            showAlert("Thất Bại", "Không có phiếu phạt nào chọn để sửa", AlertType.ERROR);
        }
        String maPPhat = maPPField.getText();
        String maPhieuThu = maphatfied.getText();
        String maDichVu = maPThuCombobox.getSelectionModel().getSelectedItem();
        int soLuong = Integer.parseInt(maSluongField1.getText());
        LocalDate ngayGhi = ngayGhiPPField.getValue();
        String ghiChu = ghiChuPPField.getText();
        Double thanhTien = 0.0;
        ViolationBUS violationBUS = new ViolationBUS();
        List<Violation> violationList = violationBUS.getAll();
        for (Violation violation1: violationList) {
            if (violation1.getViolationID().equals(maPhieuThu)) {
                thanhTien = soLuong*violation1.getPrice();
                break;
            }
        }

        if (maPPhat.isEmpty() || maPhieuThu.isEmpty() || maDichVu.isEmpty() || ngayGhi == null) {
            showAlert("Lỗi", "Vui lòng nhập đầy đủ thông tin.", AlertType.ERROR);
            return;
        }

        ViolationTicket violationTickets = new ViolationTicket();
        violationTickets.setViolationTicketID(maPPhat);
        violationTickets.setMonthlyRentBillID(maDichVu);
        violationTickets.setViolationID(maPhieuThu);
        violationTickets.setQuantity(soLuong);
        violationTickets.setDate(ngayGhi);
        violationTickets.setPrice(thanhTien);
        violationTickets.setNote(ghiChu);

        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        boolean updateResult = violationTicketBUS.update(violationTickets);

        if (updateResult) {
            showAlert("Thành Công", "Sửa vi phạm thành công", AlertType.CONFIRMATION);
            initViolationTicket();
        } else {
            showAlert("Thất Bại", "Không thể sửa vi phạm", AlertType.ERROR);
        }

    }

    public void search_price() {
        Double text1=Double.parseDouble(price_start.getText());
        Double text2 = Double.parseDouble(price_end.getText());
        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        ArrayList<ViolationTicket> violationTickets = violationTicketBUS.search(text1, text2,"Lọc Theo Giá");
        ObservableList<ViolationTicket> observableList = FXCollections.observableArrayList(violationTickets);
        tableviolationticket.setItems(observableList);
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

    public ObservableList<Furniture> getFurnitureObservableList() {
        ObservableList<Furniture> furnitureObservableList1 = FXCollections.observableArrayList();
        LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
        FurnitureBUS furnitureBUS = new FurnitureBUS();
        List<LeaseAgreement> leaseAgreements = leaseAgreementBUS.getLeaseAgreementsWithBuildingManagerID(this.ID);
        for (LeaseAgreement leaseAgreement : leaseAgreements) {
            List<Furniture> furnitureList = furnitureBUS.getFurnitureByApartmentID(leaseAgreement.getApartmentID());
            furnitureObservableList1.addAll(furnitureList);
        }

        return furnitureObservableList1;
    }

    public void initFurniture() {
        ColumnP5__1.setCellValueFactory(new PropertyValueFactory<>("furnitureID"));
        ColumnP5__2.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        ColumnP5__3.setCellValueFactory(new PropertyValueFactory<>("nameFurniture"));
        ColumnP5__4.setCellValueFactory(new PropertyValueFactory<>("conditionFurniture"));
        ColumnP5__5.setCellValueFactory(new PropertyValueFactory<>("price"));
        furnitureObservableList = getFurnitureObservableList();
        table__P5__1.setItems(furnitureObservableList);
    }

    public void refreshFormFurniture() {
        TxtField__P5__1.setText("");
        TxtField__P5__2.setText("");
        TxtField__P5__3.setText("");
        comboBox__P5__3.setValue(null);
        TxtField__P5__4.setText("");
    }

    @FXML
    void showFurniture(MouseEvent event) {
        Furniture furniture = table__P5__1.getSelectionModel().getSelectedItem();
        TxtField__P5__1.setText(furniture.getFurnitureID());
        TxtField__P5__2.setText(furniture.getApartmentID());
        TxtField__P5__3.setText(furniture.getName());
        comboBox__P5__3.setValue(furniture.getCondition());
        TxtField__P5__4.setText(String.valueOf(furniture.getPrice()));
    }

    @FXML
    void suaNoiThat(ActionEvent event) {
        Furniture furniture = table__P5__1.getSelectionModel().getSelectedItem();
        furniture.setFurnitureID(TxtField__P5__1.getText());
        furniture.setApartmentID(TxtField__P5__2.getText());
        furniture.setName(TxtField__P5__3.getText());
        furniture.setConditionFurniture(comboBox__P5__3.getSelectionModel().getSelectedItem());
        furniture.setPrice(Double.parseDouble(TxtField__P5__4.getText()));
        FurnitureBUS furnitureBUS = new FurnitureBUS();
        boolean updateSuccess = furnitureBUS.update(furniture);
        if (updateSuccess) {
            int selectedIndex = table__P5__1.getSelectionModel().getSelectedIndex();
            furnitureObservableList.set(selectedIndex, furniture);
            table__P5__1.refresh();
            refreshFormFurniture();
        } else {
            System.err.println("Không thể cập nhật nội thất trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themNoiThat(ActionEvent event) {
        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void timNoiThat(KeyEvent event) {
        FurnitureBUS furnitureBUS = new FurnitureBUS();
        ArrayList<Furniture> furnitures = furnitureBUS.searchApartments(TxtField__P5__search.getText(), this.ID);
        ObservableList<Furniture> furnitureSearch = FXCollections.observableArrayList(furnitures);
        furnitureObservableList.setAll(furnitureSearch);
    }

    @FXML
    void xoaNoiThat(ActionEvent event) {
        Furniture selectedApartment = table__P5__1.getSelectionModel().getSelectedItem();
        if (selectedApartment != null) {
            FurnitureBUS apartmentBUS = new FurnitureBUS();
            boolean deleteSuccess = apartmentBUS.delete(selectedApartment);
            if (deleteSuccess) {
                furnitureObservableList.remove(selectedApartment);
                table__P5__1.refresh();
                refreshFormFurniture();
            } else {
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        }
    }

    @FXML
    private TableColumn<LeaseAgreement, String> ColumnP6__1 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, String> ColumnP6__2 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, String> ColumnP6__3 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, String> ColumnP6__4 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, LocalDate> ColumnP6__5 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, LocalDate> ColumnP6__6 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, LocalDate> ColumnP6__7 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, Double> ColumnP6__8 = new TableColumn<>();

    @FXML
    private TableColumn<LeaseAgreement, Double> ColumnP6__9 = new TableColumn<>();

    @FXML
    private DatePicker DP_P6_1 = new DatePicker();

    @FXML
    private DatePicker DP_P6_2 = new DatePicker();

    @FXML
    private DatePicker DP_P6_3 = new DatePicker();

    @FXML
    private TextField TxtField__P6__1 = new TextField();

    @FXML
    private TextField TxtField__P6__2 = new TextField();

    @FXML
    private TextField TxtField__P6__3 = new TextField();

    @FXML
    private TextField TxtField__P6__4 = new TextField();

    @FXML
    private TextField TxtField__P6__5 = new TextField();

    @FXML
    private TextField TxtField__P6__6 = new TextField();

    @FXML
    private TextField TxtField__P6__search = new TextField();

    @FXML
    private Button bnt__P6__add;

    @FXML
    private Button bnt__P6__delete;

    @FXML
    private Button bnt__P6__update;

    @FXML
    private ComboBox<String> comboBox__P6__1 = new ComboBox<>();

    @FXML
    private ComboBox<String> comboBox__P6__2 = new ComboBox<>();

    @FXML
    private ComboBox<Double> comboBox__P6__3 = new ComboBox<>();

    @FXML
    private ComboBox<String> combobox_TxtField__P6__2 = new ComboBox<>();

    @FXML
    private ComboBox<String> combobox_TxtField__P6__3 = new ComboBox<>();

    @FXML
    private TableView<LeaseAgreement> table__P6__1 = new TableView<>();

    private ObservableList<LeaseAgreement> leaseAgreementObservableList;

    public ObservableList<LeaseAgreement> getLeaseAgreementObservableList() {
        ObservableList<LeaseAgreement> leaseAgreementObservableList1 = FXCollections.observableArrayList();
        LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
        List<LeaseAgreement> leaseAgreementList = leaseAgreementBUS.getLeaseAgreementsWithBuildingManagerID(this.ID);
        leaseAgreementObservableList1.addAll(leaseAgreementList);
        return leaseAgreementObservableList1;
    }

    public void initLeaseAgreement() {
        ColumnP6__1.setCellValueFactory(new PropertyValueFactory<>("leaseAgreementID"));
        ColumnP6__2.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        ColumnP6__3.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        ColumnP6__4.setCellValueFactory(new PropertyValueFactory<>("buildingManagerID"));
        ColumnP6__5.setCellValueFactory(new PropertyValueFactory<>("signingDate"));
        ColumnP6__6.setCellValueFactory(new PropertyValueFactory<>("leaseStartDate"));
        ColumnP6__7.setCellValueFactory(new PropertyValueFactory<>("leaseEndDate"));
        ColumnP6__8.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        ColumnP6__9.setCellValueFactory(new PropertyValueFactory<>("monthlyRent"));
        leaseAgreementObservableList = getLeaseAgreementObservableList();
        table__P6__1.setItems(leaseAgreementObservableList);
    }

    @FXML
    void showLeaseAgreement(MouseEvent event) {
        LeaseAgreement leaseAgreement = table__P6__1.getSelectionModel().getSelectedItem();
        TxtField__P6__1.setText(leaseAgreement.getLeaseAgreementID());
        TxtField__P6__2.setText(leaseAgreement.getTenantID());
        DP_P6_1.setValue(leaseAgreement.getSigningDate());
        DP_P6_2.setValue(leaseAgreement.getLeaseStartDate());
        DP_P6_3.setValue(leaseAgreement.getLeaseEndDate());
        comboBox__P6__3.setValue(leaseAgreement.getLeaseTerm());
        TxtField__P6__5.setText(String.valueOf(leaseAgreement.getDeposit()));
        TxtField__P6__6.setText(String.valueOf(leaseAgreement.getMonthlyRent()));
        combobox_TxtField__P6__2.setValue(leaseAgreement.getTenantID());
        combobox_TxtField__P6__3.setValue(leaseAgreement.getApartmentID());
    }

    private void refreshFormLeaseAgreement() {
        TxtField__P6__1.setText("");
        TxtField__P6__2.setText("");
        combobox_TxtField__P6__2.getSelectionModel().clearSelection();
        combobox_TxtField__P6__3.getSelectionModel().clearSelection();
        DP_P6_1.setValue(null);
        DP_P6_2.setValue(null);
        DP_P6_3.setValue(null);
        comboBox__P6__3.setValue(null);
        TxtField__P6__5.setText("");
        TxtField__P6__6.setText("");
    }

    @FXML
    void suaHopDong(ActionEvent event) {
        LeaseAgreement leaseAgreement = table__P6__1.getSelectionModel().getSelectedItem();
//        leaseAgreement.setTenantID(combobox_TxtField__P6__2.getSelectionModel().getSelectedItem());
//        leaseAgreement.setApartmentID(combobox_TxtField__P6__3.getSelectionModel().getSelectedItem());
//        leaseAgreement.setSigningDate(DP_P6_1.getValue());
//        leaseAgreement.setLeaseStartDate(DP_P6_2.getValue());
//        leaseAgreement.setLeaseEndDate(DP_P6_3.getValue());
//        leaseAgreement.setLeaseTerm(comboBox__P6__3.getValue());
//        leaseAgreement.setDeposit(Double.parseDouble(TxtField__P6__5.getText()));
        leaseAgreement.setMonthlyRent(Double.parseDouble(TxtField__P6__6.getText()));
        LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
        boolean updateSuccess = leaseAgreementBUS.update(leaseAgreement);
        if (updateSuccess) {
            int selectedIndex = table__P6__1.getSelectionModel().getSelectedIndex();
            leaseAgreementObservableList.set(selectedIndex, leaseAgreement);
            table__P6__1.refresh();
            refreshFormLeaseAgreement();
        } else {
            System.err.println("Không thể cập nhật hợp đồng trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themHopDong(ActionEvent event) {
        try {
            LeaseAgreement leaseAgreement = new LeaseAgreement();
            leaseAgreement.setTenantID(combobox_TxtField__P6__2.getSelectionModel().getSelectedItem());
            leaseAgreement.setApartmentID(combobox_TxtField__P6__3.getSelectionModel().getSelectedItem());
            leaseAgreement.setBuildingManagerID(ID);
            leaseAgreement.setSigningDate(DP_P6_1.getValue());
            leaseAgreement.setLeaseStartDate(DP_P6_2.getValue());
            leaseAgreement.setLeaseTerm(comboBox__P6__3.getValue());
            leaseAgreement.setLeaseEndDate(DP_P6_2.getValue().plusMonths(leaseAgreement.getLeaseTerm().longValue()));
            leaseAgreement.setDeposit(Double.parseDouble(TxtField__P6__5.getText()));
            leaseAgreement.setMonthlyRent(Double.parseDouble(TxtField__P6__6.getText()));
            LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
            leaseAgreementBUS.add(leaseAgreement);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Đã tạo hợp đồng với mã hợp đồng: " + leaseAgreement.getLeaseAgreementID());
            alert.showAndWait();

            leaseAgreementObservableList.add(leaseAgreement);
            refreshFormLeaseAgreement();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void timHopDong(KeyEvent event) {
        LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
        ArrayList<LeaseAgreement> leaseAgreements = leaseAgreementBUS.searchApartments(TxtField__P6__search.getText(), this.ID);
        ObservableList<LeaseAgreement> leaseAgreementSearch = FXCollections.observableArrayList(leaseAgreements);
        leaseAgreementObservableList.setAll(leaseAgreementSearch);
    }

    @FXML
    void xoaHopDong(ActionEvent event) {
        LeaseAgreement select = table__P6__1.getSelectionModel().getSelectedItem();

        if (select != null) {
            LocalDate leaseEndDate = select.getLeaseEndDate();
            LocalDate currentDate = LocalDate.now();

            // Kiểm tra nếu ngày kết thúc hợp đồng đã qua
            if (leaseEndDate.isBefore(currentDate)) {
                LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
                boolean deleteSuccess = leaseAgreementBUS.delete(select);

                if (deleteSuccess) {
                    // Xóa hợp đồng thành công
                    leaseAgreementObservableList.remove(select);
                    table__P6__1.refresh();
                    refreshFormLeaseAgreement();
                } else {
                    // Lỗi khi xóa hợp đồng
                    System.err.println("Không thể xóa hợp đồng từ cơ sở dữ liệu.");
                }
            } else {
                // Hiển thị thông báo hợp đồng còn hiệu lực
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Hợp đồng còn hiệu lực");

                // Tính toán khoảng thời gian còn lại
                Period period = Period.between(currentDate, leaseEndDate);
                String remainingTime = String.format("Hợp đồng còn hiệu lực trong %d năm, %d tháng, %d ngày",
                        period.getYears(), period.getMonths(), period.getDays());

                alert.setContentText(remainingTime);
                alert.showAndWait();
            }
        } else {
            // Không có hợp đồng nào được chọn
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng chọn một hợp đồng để xóa.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            comboBox__P1__3.getItems().addAll("Cơ bản", "Đầy đủ", "Không có");
            comboBox__P1__3.setPromptText("");
            initApartment();
            comboBox__P2__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2__3.setPromptText("");

            initTenant();
            initCohabitant();
            comboBox__P2_1__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2_1__3.setPromptText("");

            comboBox_P3_status.getItems().addAll("Đã thanh toán", "Chưa thanh toán");
            comboBox_P3_status.setPromptText("");
            initMonthlyRentBill();
            List<MonthlyRentBill> monthlyRentBillList = monthlyRentBillObservableList;
            List<String> monthlyRentBillListID = new ArrayList<>();
            for (MonthlyRentBill monthlyRentBill : monthlyRentBillList) {
                monthlyRentBillListID.add(monthlyRentBill.getMonthlyRentBillID());
            }
            maPhieuThuCombobox.getItems().addAll(monthlyRentBillListID);
            maPThuCombobox.getItems().addAll(monthlyRentBillListID);

//            List<Service> serviceList = ServiceList;
//            List<String> serviceListID = new ArrayList<>();
//            for (Service service : serviceList) {
//                serviceListID.add(service.getServiceID());
//            }
//            maDichVuCombobox.getItems().addAll(serviceListID);

            comboBox__P5__3.getItems().addAll("Mới", "Cũ");
            comboBox__P5__3.setPromptText("");
            initFurniture();

            comboBox__P1__2.getItems().addAll("Cơ bản", "Đầy đủ", "Không có");
            comboBox__P1__2.setPromptText("");
            // Chạy page 0
            loadPage0();

            // Dich Vu
            initService();
            showcomboboxService();
            showcomboboxService1();

            // Phieu Dich Vu
            initServiceTicket();

            // phat
            initViolation();

            // Phieu Phat
            keyenter();
            initViolationTicket();

            comboBox__P6__3.getItems().addAll(6.0, 12.0);
            comboBox__P6__3.setPromptText("");

            //list id tenant
            TenantBUS tenant_c = new TenantBUS();
            List<Tenant> listTenent_C = tenant_c.getTenantsNotInLeaseAgreement();
            List<String> differenceList = new ArrayList<>();

            for (Tenant tenant : listTenent_C) {
                    differenceList.add(tenant.getTenantID());
            }
            combobox_TxtField__P6__2.getItems().addAll(differenceList);

            Combobox__P2_1__2.getItems().addAll(differenceList);

            ApartmentBUS apartment_c = new ApartmentBUS();
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            BuildingManager buildingManager = buildingManagerBUS.getBuildingManagerById(ID);

            List<Apartment> listApartment_C = apartment_c.getApartmentByBuildingID(buildingManager.getBuildingId());
            List<String> listApartmentID = new ArrayList<>();


            for (Apartment apartment: listApartment_C) {
                if (apartment.getStatus().equals("Còn trống"))

                listApartmentID.add(apartment.getApartmentID());
            }
            combobox_TxtField__P6__3.getItems().addAll(listApartmentID);

            Combobox__P3__4.getItems().addAll(listApartmentID);


            initLeaseAgreement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent actionEvent) {
        Stage primaryStage = com.example.managingbuildingjava.BuildingManager.getPrimaryStage();
        if (primaryStage==null){
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