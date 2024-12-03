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
import java.time.format.DateTimeParseException;
import java.util.*;
import java.sql.*;
import java.util.Date;

import static java.time.LocalTime.now;

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
            Furniture furniture = new Furniture();
            fb.importExcel(filePath, furniture);
            System.out.println(furniture);
            furnitureObservableList.add(furniture);
            table__P5__1.refresh();
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
    private Label Regex__P1__3 = new Label();

    @FXML
    private Label Regex__P1__4 = new Label();

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
        Regex__P1__4.setText("");
    }

    private void initApartment() {
        maCanHoTable.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        maToaNhaTable.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
        dienTichTable.setCellValueFactory(new PropertyValueFactory<>("area"));
        soPhongNguTable.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        soPhongTamTable.setCellValueFactory(new PropertyValueFactory<>("bathrooms"));
        noiThatTable.setCellValueFactory(new PropertyValueFactory<>("furniture"));
        trangthai.setCellValueFactory(new PropertyValueFactory<>("status"));
        apartmentObservableList = getApartmentList();
        table__P1__1.setItems(apartmentObservableList);
    }

    private Apartment lastSelectedApartment = null;

    @FXML
    void showApartment(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();

        // Nếu hàng được chọn không null
        if (selectedApartment != null) {
            // Nếu hàng được chọn giống với hàng trước đó, xóa các trường nhập liệu
            if (selectedApartment.equals(lastSelectedApartment)) {
                TxtField__P1__3.clear();
                TxtField__P1__4.clear();
                TxtField__P1__5.clear();
                comboBox__P1__3.setValue(null);

                // Xóa các thông báo lỗi
                Regex__P1__1.setText("");
                Regex__P1__2.setText("");
                Regex__P1__3.setText("");
                Regex__P1__4.setText("");
                bnt__P1__add.setDisable(false);

                // Bỏ chọn hàng trong bảng
                table__P1__1.getSelectionModel().clearSelection();

                // Reset biến lưu hàng được chọn
                lastSelectedApartment = null;
            } else {
                // Nếu khác hàng trước đó, hiển thị thông tin căn hộ
                TxtField__P1__3.setText(String.valueOf(selectedApartment.getArea()));
                TxtField__P1__4.setText(String.valueOf(selectedApartment.getBedrooms()));
                TxtField__P1__5.setText(String.valueOf(selectedApartment.getBathrooms()));
                comboBox__P1__3.setValue(selectedApartment.getFurniture());

                // Xóa các thông báo lỗi
                Regex__P1__1.setText("");
                Regex__P1__2.setText("");
                Regex__P1__3.setText("");
                Regex__P1__4.setText("");

                // Cập nhật lại căn hộ vừa được chọn
                lastSelectedApartment = selectedApartment;
                bnt__P1__add.setDisable(true);
            }
        }
    }

    @FXML
    void suaCanHo(MouseEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        if (selectedApartment != null) {
            boolean isValid = true;

            // Area validation
            if (!TxtField__P1__3.getText().isEmpty()) {
                try {
                    double area = Double.parseDouble(TxtField__P1__3.getText());
                    if (area <= 0) {
                        Regex__P1__3.setText("Phải là số dương");
                        isValid = false;
                    } else {
                        Regex__P1__3.setText("");
                        selectedApartment.setArea(area);  // Cập nhật nếu hợp lệ
                    }
                } catch (NumberFormatException e) {
                    Regex__P1__3.setText("Phải là số dương");
                    isValid = false;
                }
            }

            // Bedrooms validation
            if (!TxtField__P1__4.getText().isEmpty()) {
                try {
                    int bedrooms = Integer.parseInt(TxtField__P1__4.getText());
                    if (bedrooms <= 0) {
                        Regex__P1__4.setText("Phải là số nguyên dương");
                        isValid = false;
                    } else {
                        Regex__P1__4.setText("");
                        selectedApartment.setBedrooms(bedrooms);  // Cập nhật nếu hợp lệ
                    }
                } catch (NumberFormatException e) {
                    Regex__P1__4.setText("Phải là số nguyên dương");
                    isValid = false;
                }
            }

            // Bathrooms validation
            if (!TxtField__P1__5.getText().isEmpty()) {
                try {
                    int bathrooms = Integer.parseInt(TxtField__P1__5.getText());
                    if (bathrooms < 0) {
                        Regex__P1__1.setText("Phải là số nguyên dương");
                        isValid = false;
                    } else {
                        Regex__P1__1.setText("");
                        selectedApartment.setBathrooms(bathrooms);  // Cập nhật nếu hợp lệ
                    }
                } catch (NumberFormatException e) {
                    Regex__P1__1.setText("Phải là số nguyên dương");
                    isValid = false;
                }
            }

            // Furniture validation
            if (comboBox__P1__3.getValue() != null) {
                selectedApartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());  // Cập nhật nếu có giá trị
            }

            // If validation failed, return early
            if (!isValid) {
                return;
            }

            // Cập nhật BuildingManager và các thông tin khác
            BuildingManagerBUS bus = new BuildingManagerBUS();
            List<BuildingManager> buildingManagers = bus.getAll();
            for (BuildingManager buildingManager : buildingManagers) {
                if (ID.equals(buildingManager.getBuildingManagerId())) {
                    selectedApartment.setBuildingID(buildingManager.getBuildingId());
                    break;
                }
            }

            // Cập nhật dữ liệu vào cơ sở dữ liệu
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            boolean updateSuccess = apartmentBUS.update(selectedApartment);
            if (updateSuccess) {
                int selectedIndex = table__P1__1.getSelectionModel().getSelectedIndex();
                apartmentObservableList.set(selectedIndex, selectedApartment);
                table__P1__1.refresh();
                refreshFormApartment();
                showAlert("Thông báo", "Sửa thành công!", AlertType.INFORMATION);
            } else {
                System.err.println("Không thể cập nhật căn hộ trong cơ sở dữ liệu.");
            }
        }
    }


    @FXML
    void themCanHo(MouseEvent event) {
        try {
            boolean isValid = true;

            // Area validation
            if (TxtField__P1__3.getText().isEmpty()) {
                Regex__P1__3.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    double area = Double.parseDouble(TxtField__P1__3.getText());
                    if (area < 20 || area > 200) {
                        Regex__P1__3.setText("Diện tích phải nằm trong khoảng 20 đến 200 m²");
                        isValid = false;
                    } else {
                        Regex__P1__3.setText("");
                    }

                } catch (NumberFormatException e) {
                    Regex__P1__3.setText("Diện tích phải nằm trong khoảng 20 đến 200 m²");
                    isValid = false;
                }
            }

            // Bedrooms validation
            if (TxtField__P1__4.getText().isEmpty()) {
                Regex__P1__4.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    int bedrooms = Integer.parseInt(TxtField__P1__4.getText());
                    if (bedrooms < 1 || bedrooms > 5) {
                        Regex__P1__4.setText("Số phòng ngủ phải từ 1 đến 5");
                        isValid = false;
                    } else {
                        Regex__P1__4.setText("");
                    }

                } catch (NumberFormatException e) {
                    Regex__P1__4.setText("Số phòng ngủ phải từ 1 đến 5");
                    isValid = false;
                }
            }

            // Bathrooms validation
            if (TxtField__P1__5.getText().isEmpty()) {
                Regex__P1__1.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    int bathrooms = Integer.parseInt(TxtField__P1__5.getText());
                    if (bathrooms < 1 || bathrooms > 3) {
                        Regex__P1__1.setText("Số phòng tắm phải từ 1 đến 3");
                        isValid = false;
                    } else {
                        Regex__P1__1.setText("");
                    }

                } catch (NumberFormatException e) {
                    Regex__P1__1.setText("Số phòng tắm phải từ 1 đến 3");
                    isValid = false;
                }
            }

            if (!isValid) {
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText("Căn hộ "+selectedApartment.getApartmentID() +" đã bị xóa");
                alert.setContentText("Xóa thành công.");
                alert.showAndWait();
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
    private Label Regex__P2__3 = new Label();

    @FXML
    private Label Regex__P2__4 = new Label();

    @FXML
    private Label Regex__P2__5 = new Label();

    @FXML
    private Label Regex__P2__6 = new Label();

    @FXML
    private Label Regex__P2__2__1 = new Label();

    @FXML
    private Label Regex__P2__2__2 = new Label();

    @FXML
    private Label Regex__P2__2__3 = new Label();

    @FXML
    private Label Regex__P2__2__4 = new Label();

    @FXML
    private Label Regex__P2__2__5 = new Label();

    @FXML
    private Label Regex__P2__2__6 = new Label();

    @FXML
    private Label Regex__P2__2__7 = new Label();


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
        Regex__P2__2__1.setText("");
        Regex__P2__2__2.setText("");
        Regex__P2__2__3.setText("");
        Regex__P2__2__4.setText("");
        Regex__P2__2__5.setText("");
        Regex__P2__2__6.setText("");
        Regex__P2__2__7.setText("");

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

    private Tenant lastSelectedTenant = null;

    @FXML
    void showTenant(MouseEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();

        // Nếu tenant được chọn không null
        if (selectedTenant != null) {
            // Nếu tenant được chọn giống với tenant trước đó, xóa các trường nhập liệu
            if (selectedTenant.equals(lastSelectedTenant)) {
                TxtField__P2__1.clear();
                TxtField__P2__2.clear();
                TxtField__P2__3.clear();
                TxtField__P2__4.clear();
                TxtField__P2__5.setValue(null);
                comboBox__P2__3.setValue(null);
                TxtField__P2__51.clear();

                // Xóa các thông báo lỗi
                Regex__P2__1.setText("");
                Regex__P2__2.setText("");
                Regex__P2__3.setText("");
                Regex__P2__4.setText("");
                Regex__P2__5.setText("");
                Regex__P2__6.setText("");

                // Reset biến lưu tenant được chọn
                lastSelectedTenant = null;
                table__P2__1.getSelectionModel().clearSelection();
                bnt__P2__add.setDisable(false);
            } else {
                // Nếu khác tenant trước đó, hiển thị thông tin tenant
                TxtField__P2__1.setText(selectedTenant.getTenantID());
                TxtField__P2__2.setText(selectedTenant.getFirstName());
                TxtField__P2__3.setText(selectedTenant.getLastName());
                TxtField__P2__4.setText(selectedTenant.getPhoneNumber());
                TxtField__P2__5.setValue(selectedTenant.getDateOfBirthDay());
                comboBox__P2__3.setValue(selectedTenant.getGender());
                TxtField__P2__51.setText(selectedTenant.getCitizenIdentityCard());

                // Xóa các thông báo lỗi
                Regex__P2__1.setText("");
                Regex__P2__2.setText("");
                Regex__P2__3.setText("");
                Regex__P2__4.setText("");
                Regex__P2__5.setText("");
                Regex__P2__6.setText("");

                // Cập nhật lại tenant vừa được chọn
                lastSelectedTenant = selectedTenant;

                bnt__P2__add.setDisable(true);
            }
        }
    }

    private Cohabitant lastSelectedCohabitant = null;

    @FXML
    void showCohabitant(MouseEvent event) {
        Cohabitant selectedCohabitant = table__P2_1__1.getSelectionModel().getSelectedItem();

        // Nếu cohabitant được chọn không null
        if (selectedCohabitant != null) {
            // Nếu cohabitant được chọn giống với cohabitant trước đó, xóa các trường nhập liệu
            if (selectedCohabitant.equals(lastSelectedCohabitant)) {
                Combobox__P2_1__2.setValue(null);
                TxtField__P2_1__3.clear();
                TxtField__P2_1__4.clear();
                TxtField__P2_1__5.clear();
                comboBox__P2_1__3.setValue(null);
                TxtField__P2_1__41.setValue(null);
                TxtField__P2_1__51.clear();

                // Xóa các thông báo lỗi
                Regex__P2__2__1.setText("");
                Regex__P2__2__2.setText("");
                Regex__P2__2__3.setText("");
                Regex__P2__2__4.setText("");
                Regex__P2__2__5.setText("");
                Regex__P2__2__6.setText("");
                Regex__P2__2__7.setText("");

                // Reset biến lưu cohabitant được chọn và bỏ chọn dòng trong bảng
                lastSelectedCohabitant = null;
                table__P2_1__1.getSelectionModel().clearSelection();  // Bỏ chọn dòng trong bảng
                bnt__P2_1__add.setDisable(false);

            } else {
                // Nếu khác cohabitant trước đó, hiển thị thông tin cohabitant
                Combobox__P2_1__2.setValue(selectedCohabitant.getTenantID());
                TxtField__P2_1__3.setText(selectedCohabitant.getFirstName());
                TxtField__P2_1__4.setText(selectedCohabitant.getLastName());
                TxtField__P2_1__5.setText(selectedCohabitant.getPhoneNumber());
                comboBox__P2_1__3.setValue(selectedCohabitant.getGender());
                TxtField__P2_1__41.setValue(selectedCohabitant.getDateOfBirthDay());
                TxtField__P2_1__51.setText(selectedCohabitant.getCitizenIdentityCard());

                // Xóa các thông báo lỗi
                Regex__P2__2__1.setText("");
                Regex__P2__2__2.setText("");
                Regex__P2__2__3.setText("");
                Regex__P2__2__4.setText("");
                Regex__P2__2__5.setText("");
                Regex__P2__2__6.setText("");
                Regex__P2__2__7.setText("");

                // Cập nhật lại cohabitant vừa được chọn
                lastSelectedCohabitant = selectedCohabitant;
                bnt__P2_1__add.setDisable(true);
            }
        }
    }

    @FXML
    void suaCuDan(ActionEvent event) {
        Cohabitant selectedCohabitant = table__P2_1__1.getSelectionModel().getSelectedItem();
        if (selectedCohabitant != null) {
            boolean isValid = true;

            // Kiểm tra ComboBox
            if (Combobox__P2_1__2.getValue() == null) {
                Regex__P2__2__1.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P2__2__1.setText("");
                // Nếu ComboBox không rỗng thì cập nhật giá trị
                selectedCohabitant.setTenantID(Combobox__P2_1__2.getValue());
            }

            // Kiểm tra họ
            if (!TxtField__P2_1__3.getText().isEmpty()) {
                String input = TxtField__P2_1__3.getText();
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2__2.setText("Họ không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2__2.setText("");
                    selectedCohabitant.setLastName(input);
                }
            }

            // Kiểm tra tên
            if (!TxtField__P2_1__4.getText().isEmpty()) {
                String input = TxtField__P2_1__4.getText();
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2__3.setText("Tên không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2__3.setText("");
                    selectedCohabitant.setFirstName(input);
                }
            }

            // Kiểm tra số điện thoại
            if (!TxtField__P2_1__5.getText().isEmpty()) {
                String input = TxtField__P2_1__5.getText();
                if (!input.matches("\\d{10}")) {
                    Regex__P2__2__4.setText("10 số");
                    isValid = false;
                } else {
                    Regex__P2__2__4.setText("");
                    selectedCohabitant.setPhoneNumber(input);
                }
            }

            // Kiểm tra ngày sinh
            if (TxtField__P2_1__41.getValue() != null&&TxtField__P2_1__41.getValue().isBefore(LocalDate.now())) {
                selectedCohabitant.setDateOfBirthDay(TxtField__P2_1__41.getValue());
            }

            // Kiểm tra giới tính (ComboBox)
            if (comboBox__P2_1__3.getValue() != null) {
                selectedCohabitant.setGender(comboBox__P2_1__3.getSelectionModel().getSelectedItem());
            }

            // Kiểm tra căn cước công dân
            if (!TxtField__P2_1__51.getText().isEmpty()) {
                String input = TxtField__P2_1__51.getText();
                if (!input.matches("\\d{12}")) {
                    Regex__P2__2__7.setText("Chứa đúng 12 số");
                    isValid = false;
                } else {
                    Regex__P2__2__7.setText("");
                    selectedCohabitant.setCitizenIdentityCard(input);
                }
            }

            // Nếu không hợp lệ thì kết thúc
            if (!isValid) {
                return;
            }

            // Gọi BUS để cập nhật thông tin
            CohabitantBUS cohabitantBUS = new CohabitantBUS();
            boolean updateSuccess = cohabitantBUS.update(selectedCohabitant);
            if (updateSuccess) {
                int selectedIndex = table__P2_1__1.getSelectionModel().getSelectedIndex();
                cohabitantObservableList.set(selectedIndex, selectedCohabitant);
                table__P2_1__1.refresh();
                refreshFormCohabitant();
                showAlert("Thông báo", "Sửa thành công!", AlertType.INFORMATION);
            } else {
                System.err.println("Không thể cập nhật thông tin cư dân trong cơ sở dữ liệu.");
            }
        }
    }


    @FXML
    void suaKhachHang(ActionEvent event) {
        Tenant selectedTenant = table__P2__1.getSelectionModel().getSelectedItem();
        if (selectedTenant!=null) {
            boolean isValid = true;
            if (TxtField__P2__2.getText().isEmpty()) {
                Regex__P2__1.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__2.getText();
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__1.setText("Họ không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__1.setText("");
                    selectedTenant.setFirstName(input);  // Cập nhật họ nếu hợp lệ
                }
            }

            if (TxtField__P2__3.getText().isEmpty()) {
                Regex__P2__2.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__3.getText();
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2.setText("Tên không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2.setText("");
                    selectedTenant.setLastName(input);  // Cập nhật tên nếu hợp lệ
                }
            }

            if (!TxtField__P2__4.getText().isEmpty()) {
                String input = TxtField__P2__4.getText();
                if (!input.matches("\\d{10}")) {
                    Regex__P2__3.setText("Phải là 10 số");
                    isValid = false;
                } else {
                    Regex__P2__3.setText("");
                    selectedTenant.setPhoneNumber(input);  // Cập nhật số điện thoại nếu hợp lệ
                }
            }

            if (TxtField__P2__5.getValue() != null) {
                if (TxtField__P2__5.getValue().isAfter(LocalDate.now())) {
                    Regex__P2__4.setText("Ngày sinh phải nhỏ hơn ngày hiện tại");
                    isValid = false;
                } else {
                    Regex__P2__4.setText("");
                    selectedTenant.setDateOfBirthDay(TxtField__P2__5.getValue());  // Cập nhật ngày sinh nếu hợp lệ
                }
            }


            if (comboBox__P2__3.getValue() != null) {
                selectedTenant.setGender(comboBox__P2__3.getSelectionModel().getSelectedItem());  // Cập nhật giới tính nếu hợp lệ
            }

            if (!TxtField__P2__51.getText().isEmpty()) {
                String input = TxtField__P2__51.getText();
                if (!input.matches("\\d{12}")) {
                    Regex__P2__6.setText("Căn cước phải chứa đúng 12 số");
                    isValid = false;
                } else {
                    Regex__P2__6.setText("");
                    selectedTenant.setCitizenIdentityCard(input);  // Cập nhật CCCD nếu hợp lệ
                }
            }

            // If validation failed, return early
            if (!isValid) {
                return;
            }

            TenantBUS tenantBUS = new TenantBUS();
            boolean updateSuccess = tenantBUS.update(selectedTenant);
            if (updateSuccess) {
                int selectedIndex = table__P2__1.getSelectionModel().getSelectedIndex();
                tenantObservableList.set(selectedIndex, selectedTenant);
                table__P2__1.refresh();
                refreshFormTenant();
                showAlert("Thông báo", "Sửa thành công", AlertType.INFORMATION);
            } else {
                System.err.println("Không thể cập nhật khách hàng trong cơ sở dữ liệu.");
            }
        } else {
            showAlert("Thông báo", "Không có khách hàng nào được chọn", AlertType.ERROR);
        }
    }

    @FXML
    void themCuDan(ActionEvent event) {
        try {
            boolean isValid = true;

            if (Combobox__P2_1__2.getValue()==null) {
                Regex__P2__2__1.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P2__2__1.setText("");
            }


            if (TxtField__P2_1__3.getText().isEmpty()) {
                Regex__P2__2__2.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2_1__3.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2__2.setText("Họ không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2__2.setText("");
                }
            }

            if (TxtField__P2_1__4.getText().isEmpty()) {
                Regex__P2__2__3.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2_1__4.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2__3.setText("Tên không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2__3.setText("");
                }
            }

            if (TxtField__P2_1__5.getText().isEmpty()) {
                Regex__P2__2__4.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2_1__5.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("\\d{10}")) {
                    Regex__P2__2__4.setText("10 số");
                    isValid = false;
                } else {
                    Regex__P2__2__4.setText("");
                }
            }

            if (TxtField__P2_1__41.getValue()==null) {
                Regex__P2__2__6.setText("Không được để trống");
                isValid = false;
            } else if(TxtField__P2_1__41.getValue().isAfter(LocalDate.now())){
                Regex__P2__2__6.setText("Chọn ngày sinh hợp lệ");
            } else {
                Regex__P2__2__6.setText("");
            }

            if (comboBox__P2_1__3.getValue()==null) {
                Regex__P2__2__5.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P2__2__5.setText("");
            }

            if (TxtField__P2_1__51.getText().isEmpty()) {
                Regex__P2__2__7.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2_1__51.getText();
                // Kiểm tra căn cước công dân phải có đúng 12 số
                if (!input.matches("\\d{12}")) {
                    Regex__P2__2__7.setText("Chứa đúng 12 số");
                    isValid = false;
                } else {
                    Regex__P2__2__7.setText("");
                }
            }




            // If validation failed, return early
            if (!isValid) {
                return;
            }

            Cohabitant cohabitant = new Cohabitant();
            cohabitant.setTenantID(Combobox__P2_1__2.getSelectionModel().getSelectedItem());
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


            boolean isValid = true;

            if (TxtField__P2__2.getText().isEmpty()) {
                Regex__P2__1.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__2.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__1.setText("Họ không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__1.setText("");
                }
            }

            if (TxtField__P2__3.getText().isEmpty()) {
                Regex__P2__2.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__3.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("[a-zA-ZÀ-ỹ\\s]+")) {
                    Regex__P2__2.setText("Tên không được chứa số hoặc ký tự đặc biệt");
                    isValid = false;
                } else {
                    Regex__P2__2.setText("");
                }
            }

            if (TxtField__P2__4.getText().isEmpty()) {
                Regex__P2__3.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__4.getText();
                // Kiểm tra xem chuỗi có chứa số hoặc ký tự đặc biệt hay không
                if (!input.matches("\\d{10}")) {
                    Regex__P2__3.setText("10 số");
                    isValid = false;
                } else {
                    Regex__P2__3.setText("");
                }
            }

            if (TxtField__P2__5.getValue()==null) {
                Regex__P2__4.setText("Không được để trống");
                isValid = false;
            } else  if (TxtField__P2__5.getValue().isAfter(LocalDate.now())){
                Regex__P2__4.setText("Chọn ngày sinh hợp lệ");

            }else {
                Regex__P2__4.setText("");
            }

            if (comboBox__P2__3.getValue()==null) {
                Regex__P2__5.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P2__5.setText("");
            }

            if (TxtField__P2__51.getText().isEmpty()) {
                Regex__P2__6.setText("Không được để trống");
                isValid = false;
            } else {
                String input = TxtField__P2__51.getText();
                // Kiểm tra căn cước công dân phải có đúng 12 số
                if (!input.matches("\\d{12}")) {
                    Regex__P2__6.setText("Chứa đúng 12 số");
                    isValid = false;
                } else {
                    Regex__P2__6.setText("");
                }
            }




            // If validation failed, return early
            if (!isValid) {
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
                showAlert("Thông báo", "Xóa thành công", AlertType.CONFIRMATION);
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
    private Label Regex__P3__1 = new Label();

    @FXML
    private Label Regex__P3__2 = new Label();

    @FXML
    private ComboBox<String> comboBox_P3_status = new ComboBox<>();

    @FXML
    private DatePicker datePicker__P3 = new DatePicker();

    @FXML
    private DatePicker Date__P3__1 = new DatePicker();

    @FXML
    private DatePicker Date__P3__2 = new DatePicker();

    @FXML
    private Button bnt__P3__add = new Button();

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

    private MonthlyRentBill lastSelectedMonthlyRentBill = null;

    @FXML
    void showMonthlyRentBill(MouseEvent event) {
        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();

        // Nếu hóa đơn tiền thuê được chọn không null
        if (monthlyRentBill != null) {
            // Nếu hóa đơn được chọn giống với hóa đơn trước đó, xóa các trường nhập liệu
            if (monthlyRentBill.equals(lastSelectedMonthlyRentBill)) {
                Combobox__P3__4.setValue(null);
                TxtField__P3__5.clear();
                TxtField__P3__51.clear();
                comboBox_P3_status.setValue(null);

                // Xóa các thông báo lỗi
                Regex__P3__1.setText("");
                Regex__P3__2.setText("");

                // Reset biến lưu hóa đơn đã chọn và bỏ chọn dòng trong bảng
                lastSelectedMonthlyRentBill = null;
                table__P3__1.getSelectionModel().clearSelection();  // Bỏ chọn dòng trong bảng
                bnt__P3__add.setDisable(false);
                Combobox__P3__4.setDisable(false);
                TxtField__P3__5.setDisable(false);
            } else {
                // Nếu khác hóa đơn trước đó, hiển thị thông tin hóa đơn
                Combobox__P3__4.setValue(monthlyRentBill.getApartmentID());
                TxtField__P3__5.setText(String.valueOf(monthlyRentBill.getRepaymentPeriod()));
                TxtField__P3__51.setText(monthlyRentBill.getStatus());
                comboBox_P3_status.setValue(monthlyRentBill.getStatus());

                // Xóa các thông báo lỗi
                Regex__P3__1.setText("");
                Regex__P3__2.setText("");

                // Cập nhật lại hóa đơn vừa được chọn
                lastSelectedMonthlyRentBill = monthlyRentBill;
                bnt__P3__add.setDisable(true);
                Combobox__P3__4.setDisable(true);
                TxtField__P3__5.setDisable(true);
            }
        }
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
        if (monthlyRentBill == null) {
            System.err.println("Không có phiếu thu được chọn.");
            return;
        }

//        String repaymentText = TxtField__P3__5.getText();
//        if (!repaymentText.isEmpty()) {
//            try {
//                int repaymentPeriod = Integer.parseInt(repaymentText);
//                if (repaymentPeriod > 0) {
//                    monthlyRentBill.setRepaymentPeriod(repaymentPeriod);
//                    Regex__P3__2.setText("");  // Clear the error message if the input is valid
//                } else {
//                    Regex__P3__2.setText("Phải là số nguyên dương.");
//                    return;
//                }
//            } catch (NumberFormatException e) {
//                Regex__P3__2.setText("Phải là số nguyên dương.");
//                return;
//            }
//        } else {
//            Regex__P3__2.setText("Không nhập sẽ giữ nguyên.");
//        }

        monthlyRentBill.setStatus(comboBox_P3_status.getSelectionModel().getSelectedItem());

        MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
        boolean updateSuccess = monthlyRentBillBUS.update(monthlyRentBill);
        if (updateSuccess) {
            int selectedIndex = table__P3__1.getSelectionModel().getSelectedIndex();
            monthlyRentBillObservableList.set(selectedIndex, monthlyRentBill);
            table__P3__1.refresh();
            refreshFormMonthlyRentBill();
            showAlert("Thông báo", "Cập nhât thành công", AlertType.INFORMATION);
        } else {
            System.err.println("Không thể cập nhật phiếu thu trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themPhieuThu(ActionEvent event) {
        try {
            boolean isValid = true;
            MonthlyRentBill monthlyRentBill = new MonthlyRentBill();
            String apartmentID = Combobox__P3__4.getSelectionModel().getSelectedItem();

            if (apartmentID == null) {
                Regex__P3__1.setText("Chọn mã căn hộ");
                isValid = false;
            } else {
                Regex__P3__1.setText("");

            }

            if (TxtField__P3__5.getText().isEmpty()) {
                Regex__P3__2.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    int repaymentPeriod = Integer.parseInt(TxtField__P3__5.getText());
                    if (repaymentPeriod <= 0) {
                        Regex__P3__2.setText("Phải là số nguyên dương");
                        isValid = false;
                    } else {
                        Regex__P3__2.setText("");
                    }
                } catch (NumberFormatException e) {
                    Regex__P3__2.setText("Phải là số nguyên dương");
                    isValid = false;
                }
            }

            if (!isValid) {
                return;
            }

            boolean checkExist = MonthlyRentBillBUS.getInstance().checkExist(apartmentID);
            if (!checkExist) {
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
            } else {
                showAlert("Thông báo", "Đã có phiếu thu tháng này của căn hộ", AlertType.ERROR);
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

//    @FXML
//    void xoaPhieuThu(ActionEvent event) {
//        MonthlyRentBill monthlyRentBill = table__P3__1.getSelectionModel().getSelectedItem();
//        if (monthlyRentBill != null) {
//            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
//            boolean deleteSuccess = monthlyRentBillBUS.delete(monthlyRentBill);
//            if (deleteSuccess) {
//                monthlyRentBillObservableList.remove(monthlyRentBill);
//                table__P3__1.refresh();
//                refreshFormMonthlyRentBill();
//            } else {
//                System.err.println("Không thể xóa phiếu thu từ cơ sở dữ liệu.");
//            }
//        }
//    }

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

    @FXML
    private Button bnt__P4__add1 = new Button();

    @FXML
    private Label Regex__P4__3__1 = new Label();

    @FXML
    private Label Regex__P4__3__2 = new Label();

    @FXML
    private Label Regex__P4__3__3 = new Label();

    @FXML
    private Label Regex__P4__3__4 = new Label();

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

                // Kiểm tra nếu hàng được chọn là hàng đã chọn trước đó (lần thứ hai)
                if (selectedRow != null && selectedRow.equals(servicedelete)) {
                    // Làm trống các trường
                    TxtField__P4__11.setText("");
                    TxtField__P4__31.setText("");
                    TxtField__P4__51.setText("");
                    TxtField__P4__61.setText("");
                    fill_type.getSelectionModel().clearSelection();
                    table__P4__11.getSelectionModel().clearSelection();
                    servicedelete = null; // Xóa trạng thái hàng đã chọn để có thể chọn lại
                    bnt__P4__add1.setDisable(false); // Kích hoạt lại nút "Thêm"
                } else {
                    // Nếu chọn một hàng mới hoặc lần đầu tiên chọn
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

                        servicedelete = selectedRow; // Lưu trạng thái hàng đã chọn
                        bnt__P4__add1.setDisable(true); // Vô hiệu hóa nút "Thêm"
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
        boolean isValid = true;

        // Lấy giá trị từ các trường
        String name = TxtField__P4__31.getText();
        String pricePerUnitText = TxtField__P4__51.getText().replaceAll(",", ""); // Xóa dấu phẩy nếu có
        String unit = TxtField__P4__61.getText();
        String type = fill_type.getValue();

        // Validate tên không được để trống
        if (name == null || name.trim().isEmpty()) {
            Regex__P4__3__1.setText("Tên dịch vụ không được để trống");
            isValid = false;
        } else {
            Regex__P4__3__1.setText("");
        }

        // Validate giá dịch vụ phải là số và lớn hơn 0
        double pricePerUnit = 0;
        try {
            pricePerUnit = Double.parseDouble(pricePerUnitText);
            if (pricePerUnit <= 0) {
                Regex__P4__3__2.setText("Giá dịch vụ phải lớn hơn 0");
                isValid = false;
            } else {
                Regex__P4__3__2.setText("");
            }
        } catch (NumberFormatException e) {
            Regex__P4__3__2.setText("Giá dịch vụ phải là số hợp lệ");
            isValid = false;
        }

        // Validate đơn vị không được để trống
        if (unit == null || unit.trim().isEmpty()) {
            Regex__P4__3__3.setText("Đơn vị không được để trống");
            isValid = false;
        } else {
            Regex__P4__3__3.setText("");
        }

        // Validate loại dịch vụ không được để trống
        if (type == null || type.trim().isEmpty()) {
            Regex__P4__3__4.setText("Loại dịch vụ không được để trống.");

            isValid = false;
        } else {
            Regex__P4__3__4.setText("");
        }

        // Nếu có lỗi trong quá trình kiểm tra, dừng xử lý
        if (!isValid) {
            return;
        }

        // Xử lý thêm dịch vụ mới nếu tất cả đều hợp lệ
        Service newService = new Service();
        newService.setName(name);
        newService.setPricePerUnit(pricePerUnit);
        newService.setUnit(unit);
        newService.setType(type);

        ServiceBUS serviceBUS = new ServiceBUS();
        boolean isSuccess = serviceBUS.add(newService);

        if (isSuccess) {
            showAlert("Thành Công", "Thêm dịch vụ thành công.", AlertType.CONFIRMATION);
            initService();
            TxtField__P4__11.setText("");
            TxtField__P4__31.setText("");
            TxtField__P4__51.setText("");
            TxtField__P4__61.setText("");
            fill_type.getSelectionModel().clearSelection();

        } else {
            showAlert("Lỗi", "Không thể thêm dịch vụ.", AlertType.ERROR);
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
                TxtField__P4__11.setText("");
                TxtField__P4__31.setText("");
                TxtField__P4__51.setText("");
                TxtField__P4__61.setText("");
                fill_type.getSelectionModel().clearSelection();
            } else {
                showAlert("Thất Bại", "Không Thể Xóa", AlertType.ERROR);
            }
            initService();
        });
    }

    public void handleUpdateService() {
        if (servicedelete == null) {
            showAlert("Lỗi", "Không có dịch vụ nào chọn để sửa", AlertType.ERROR);
            return;
        }

        boolean isValid = true;

        // Lấy giá trị từ các trường
        String name = TxtField__P4__31.getText();
        String pricePerUnitText = TxtField__P4__51.getText().replaceAll(",", ""); // Xóa dấu phẩy nếu có
        String unit = TxtField__P4__61.getText();
        String type = fill_type.getValue();

        // Validate tên không được để trống
        if (name == null || name.trim().isEmpty()) {
            Regex__P4__3__1.setText("Tên dịch vụ không được để trống");
            isValid = false;
        } else {
            Regex__P4__3__1.setText("");
        }

        // Validate giá dịch vụ phải là số và lớn hơn 0
        double pricePerUnit = 0;
        try {
            pricePerUnit = Double.parseDouble(pricePerUnitText);
            if (pricePerUnit <= 0) {
                Regex__P4__3__2.setText("Giá dịch vụ phải lớn hơn 0");
                isValid = false;
            } else {
                Regex__P4__3__2.setText("");
            }
        } catch (NumberFormatException e) {
            Regex__P4__3__2.setText("Giá dịch vụ phải là số hợp lệ");
            isValid = false;
        }

        // Validate đơn vị không được để trống
        if (unit == null || unit.trim().isEmpty()) {
            Regex__P4__3__3.setText("Đơn vị không được để trống");
            isValid = false;
        } else {
            Regex__P4__3__3.setText("");
        }

        // Validate loại dịch vụ không được để trống
        if (type == null || type.trim().isEmpty()) {
            Regex__P4__3__4.setText("Loại dịch vụ không được để trống.");

            isValid = false;
        } else {
            Regex__P4__3__4.setText("");
        }

        // Nếu có lỗi trong quá trình kiểm tra, dừng xử lý
        if (!isValid) {
            return;
        }

        Service newService = new Service();
        newService.setServiceID(servicedelete.getServiceID());
        newService.setName(name);
        newService.setPricePerUnit(pricePerUnit);
        newService.setUnit(unit);
        newService.setType(type);
        ServiceBUS serviceBUS = new ServiceBUS();
        boolean isSuccess = serviceBUS.update(newService);

        if (isSuccess) {
            showAlert("Thành Công", "Sửa Thành Công.", AlertType.CONFIRMATION);
            initService();
            TxtField__P4__11.setText("");
            TxtField__P4__31.setText("");
            TxtField__P4__51.setText("");
            TxtField__P4__61.setText("");
            fill_type.getSelectionModel().clearSelection();
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
    private ComboBox<String> maPhatCombobox = new ComboBox<>();
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
    private Button bnt__P4_1__add = new Button();


    @FXML
    private Label Regex__P4__2__1 = new Label();

    @FXML
    private Label Regex__P4__2__2 = new Label();

    @FXML
    private Label Regex__P4__2__3 = new Label();

    @FXML
    private Label Regex__P4__2__4 = new Label();


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
    private Button bnt__P4__add = new Button();



    @FXML
    private Label Regex__P4__1__1 = new Label();

    @FXML
    private Label Regex__P4__1__2 = new Label();

    @FXML
    private Label Regex__P4__1__3 = new Label();

    @FXML
    private Label Regex__P4__1__4 = new Label();

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

    ServiceTicket lastSelectedRow = null; // Lưu hàng được chọn cuối cùng

    public void handleServiceTicket() {
        table__sericetiket.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ServiceTicket selectedRow = table__sericetiket.getSelectionModel().getSelectedItem();

                if (selectedRow != null) {
                    // Nếu hàng hiện tại được chọn giống với hàng đã chọn trước đó
                    if (selectedRow.equals(lastSelectedRow)) {
                        // Hủy chọn hàng
                        table__sericetiket.getSelectionModel().clearSelection();
                        maPhieuDVField.clear();
                        maPhieuThuCombobox.setValue(null);
                        maDichVuCombobox.setValue(null);
                        soLuongField.clear();
                        ngayGhiPicker.setValue(null);
                        ghiChuArea.clear();
                        serviceTicketdelete = null;

                        Regex__P4__1__4.setText("");
                        Regex__P4__1__3.setText("");
                        Regex__P4__1__2.setText("");
                        Regex__P4__1__1.setText("");

                        maPhieuThuCombobox.setDisable(false);
                        maDichVuCombobox.setDisable(false);
                        soLuongField.setDisable(false);
                        ngayGhiPicker.setDisable(false);

                        lastSelectedRow = null; // Đặt lại biến để đánh dấu rằng không có hàng nào được chọn
                        bnt__P4__add.setDisable(false);
                        table__sericetiket.getSelectionModel().clearSelection();
                    } else {
                        // Nếu hàng khác với hàng trước đó, chọn hàng mới
                        maPhieuDVField.setText(selectedRow.getServiceTicketID());
                        maPhieuThuCombobox.setValue(selectedRow.getMonthlyRentBillID());
                        maDichVuCombobox.setValue(selectedRow.getServiceID());
                        soLuongField.setText(String.valueOf(selectedRow.getQuantity()));
                        ngayGhiPicker.setValue(selectedRow.getDate());
                        ghiChuArea.setText(selectedRow.getNote());
                        serviceTicketdelete = selectedRow;

                        Regex__P4__1__4.setText("");
                        Regex__P4__1__3.setText("");
                        Regex__P4__1__2.setText("");
                        Regex__P4__1__1.setText("");

                        maPhieuThuCombobox.setDisable(true);
                        maDichVuCombobox.setDisable(true);
                        soLuongField.setDisable(true);
                        ngayGhiPicker.setDisable(true);

                        // Cập nhật hàng cuối cùng được chọn
                        lastSelectedRow = selectedRow;
                        bnt__P4__add.setDisable(true);

                    }
                }
            }
        });
    }


    public void handleAddServiceTicket() {
        LocalDate ngayGhi = ngayGhiPicker.getValue();
        boolean isValid = true;

        // Kiểm tra nếu ngày ghi trống
        if (ngayGhi == null) {
            Regex__P4__1__4.setText("Ngày ghi không được để trống");
            isValid = false;
        } else {
            Regex__P4__1__4.setText("");
        }

        String maPhieuThu = maDichVuCombobox.getSelectionModel().getSelectedItem();
        String maDichVu = maPhieuThuCombobox.getSelectionModel().getSelectedItem();

        // Kiểm tra nếu maDichVuCombobox trống
        if (maDichVu == null || maDichVu.isEmpty()) {
            Regex__P4__1__1.setText("Vui lòng chọn mã phiếu thu");
            isValid = false;
        } else {
            Regex__P4__1__1.setText("");
            // Lấy thông tin phiếu thu và dịch vụ
            MonthlyRentBill monthlyRentBill1 = MonthlyRentBillBUS.getInstance().getMonthlyRentBillWithMRB_BuildingManager(maDichVu);
            if (LocalDate.now().isAfter(monthlyRentBill1.getDate().plusMonths(1))){
                showAlert("Thông báo", "Phiếu thu này đã hết hạn", AlertType.ERROR);
                isValid = false;
            }



            // Kiểm tra ngày ghi không được trước ngày bắt đầu của phiếu thu
            if (ngayGhi != null && ngayGhi.isBefore(monthlyRentBill1.getDate())||ngayGhi != null && ngayGhi.isAfter(monthlyRentBill1.getDate().plusMonths(1))) {
                Regex__P4__1__4.setText("Ngày ghi phải trong hạn 1 tháng của phiếu thu.");
                isValid = false;
            }
        }

        Service serviceFixed = ServiceBUS.getInstance().getService(maPhieuThu);

        // Kiểm tra nếu maPhieuThuCombobox trống
        if (maPhieuThu == null || maPhieuThu.isEmpty()) {
            Regex__P4__1__2.setText("Vui lòng chọn mã dịch vụ");
            isValid = false;
        } else {
            Regex__P4__1__2.setText("");
        }

        // Kiểm tra số lượng phải hợp lệ (không trống và trong khoảng từ 1 đến 10)
        double soLuong = 0;
        if (soLuongField.getText().isEmpty()) {
            Regex__P4__1__3.setText("Số lượng không được để trống");
            isValid = false;
        } else {
            try {
                soLuong = Double.parseDouble(soLuongField.getText());
                if (soLuong < 1 || soLuong > 10) {
                    Regex__P4__1__3.setText("Số lượng phải nằm trong khoảng từ 1 đến 10");
                    isValid = false;
                } else {
                    Regex__P4__1__3.setText("");
                }
            } catch (NumberFormatException e) {
                Regex__P4__1__3.setText("Số lượng phải nằm trong khoảng từ 1 đến 10");
                isValid = false;
            }
        }

        // Nếu có lỗi trong phần kiểm tra, dừng xử lý
        if (!isValid) {
            return;
        }



        // Dịch vụ "fixed"
        if (serviceFixed.getType().equals("fixed")) {
            boolean exist = ServiceTicketBUS.getInstance().checkServiceTicketInList(maDichVu, maPhieuThu);
            if (!exist) {
                Double thanhTien = serviceFixed.getPricePerUnit() * soLuong;
                String ghiChu = ghiChuArea.getText();

                // Tạo phiếu dịch vụ mới
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
                    showAlert("Thành Công", "Thêm Phiếu Dịch Vụ Thành Công: " + service.getServiceTicketID(), AlertType.CONFIRMATION);
                    initServiceTicket();
                    maDichVuField.setText("");
                    soLuongField.setText("");
                    ngayGhiPicker.setValue(null);
                    ghiChuArea.setText("");
                    maPhieuThuCombobox.setValue(null);
                    maDichVuCombobox.setValue(null);
                } else {
                    showAlert("Thất Bại", "Không Thể Thêm Phiếu Dịch Vụ", AlertType.ERROR);
                }

                // Cập nhật tổng tiền phiếu thu
                MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
                List<MonthlyRentBill> monthlyRentBillList = monthlyRentBillBUS.getAll();
                for (int i = 0; i < monthlyRentBillList.size(); i++) {
                    MonthlyRentBill monthlyRentBill = monthlyRentBillList.get(i);
                    LocalDate sameDayNextMonth = monthlyRentBill.getDate().plusMonths(1);
                    if (monthlyRentBill.getMonthlyRentBillID().equals(service.getMonthlyRentBillID()) &&
                            (service.getDate().isBefore(sameDayNextMonth) || service.getDate().isEqual(sameDayNextMonth)) &&
                            (service.getDate().isAfter(monthlyRentBill.getDate()) || service.getDate().isEqual(monthlyRentBill.getDate()))) {

                        monthlyRentBill.setTotalPayment(monthlyRentBill.getTotalPayment() + service.getTotalAmount());

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
            } else {
                showAlert("Thông báo", "Khách hàng đã đăng ký dịch vụ này trong tháng", AlertType.ERROR);
            }
        } else {
            Double thanhTien = serviceFixed.getPricePerUnit() * soLuong;
            String ghiChu = ghiChuArea.getText();

            // Tạo phiếu dịch vụ mới
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
                showAlert("Thành Công", "Thêm Phiếu Dịch Vụ Thành Công: " + service.getServiceTicketID(), AlertType.CONFIRMATION);
                initServiceTicket();
                maDichVuField.setText("");
                soLuongField.setText("");
                ngayGhiPicker.setValue(null);
                ghiChuArea.setText("");
                maPhieuThuCombobox.setValue(null);
                maDichVuCombobox.setValue(null);
            } else {
                showAlert("Thất Bại", "Không Thể Thêm Phiếu Dịch Vụ", AlertType.ERROR);
            }

            // Cập nhật tổng tiền phiếu thu
            MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
            List<MonthlyRentBill> monthlyRentBillList = monthlyRentBillBUS.getAll();
            for (int i = 0; i < monthlyRentBillList.size(); i++) {
                MonthlyRentBill monthlyRentBill = monthlyRentBillList.get(i);
                LocalDate sameDayNextMonth = monthlyRentBill.getDate().plusMonths(1);
                if (monthlyRentBill.getMonthlyRentBillID().equals(service.getMonthlyRentBillID()) &&
                        (service.getDate().isBefore(sameDayNextMonth) || service.getDate().isEqual(sameDayNextMonth)) &&
                        (service.getDate().isAfter(monthlyRentBill.getDate()) || service.getDate().isEqual(monthlyRentBill.getDate()))) {

                    monthlyRentBill.setTotalPayment(monthlyRentBill.getTotalPayment() + service.getTotalAmount());

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

        String ghiChu = ghiChuArea.getText();



        String maPDVCu = serviceTicketdelete.getServiceTicketID();

        ServiceTicket service = table__sericetiket.getSelectionModel().getSelectedItem();

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

    @FXML
    private Button bnt__P4__add11 = new Button();

    @FXML
    private Label Regex__P4__4__1 = new Label();

    @FXML
    private Label Regex__P4__4__2 = new Label();

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

    private Violation lastSelectedRow_Violation = null; // Biến lưu hàng được chọn trước đó

    public void handleViolation() {
        tableViolation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Violation selectedRow = tableViolation.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    // Nếu hàng được chọn giống hàng trước đó, xóa nội dung các TextField
                    if (selectedRow.equals(lastSelectedRow_Violation)) {
                        viphamField.clear();
                        tienPhatField.clear();
                        violationdelete = null; // Reset biến vi phạm đã chọn
                        lastSelectedRow_Violation = null; // Reset hàng được chọn trước đó
                        bnt__P4__add11.setDisable(false);
                        Regex__P4__4__1.setText("");
                        Regex__P4__4__2.setText("");
                        tableViolation.getSelectionModel().clearSelection();
                    } else {
                        // Nếu không giống, hiển thị thông tin của hàng được chọn
                        viphamField.setText(selectedRow.getName());
                        tienPhatField.setText(String.valueOf(selectedRow.getPrice()));
                        violationdelete = selectedRow; // Lưu lại hàng đã chọn
                        lastSelectedRow_Violation = selectedRow; // Cập nhật hàng được chọn trước đó
                        bnt__P4__add11.setDisable(true);
                        Regex__P4__4__1.setText("");
                        Regex__P4__4__2.setText("");
                    }
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

        boolean isValid = true; // Biến kiểm tra tính hợp lệ

        String newName = viphamField.getText().trim();
        String priceText = tienPhatField.getText().trim();
        Double newPrice = null; // Khai báo nhưng chưa khởi tạo

        // Kiểm tra tên vi phạm không được để trống
        if (newName.isEmpty()) {
            Regex__P4__4__1.setText("Tên vi phạm không được để trống");
            isValid = false;
        } else if (newName.matches(".*\\d.*")) { // Kiểm tra xem tên có chứa số hay không
            Regex__P4__4__1.setText("Tên vi phạm không được chứa số");
            isValid = false;
        } else {
            Regex__P4__4__1.setText(""); // Xóa thông báo lỗi
        }

        // Kiểm tra số tiền
        try {
            newPrice = Double.parseDouble(priceText);
            if (newPrice <= 0) {
                Regex__P4__4__2.setText("Số tiền phải lớn hơn 0");
                isValid = false;
            } else if (newPrice >= 500000) {
                Regex__P4__4__2.setText("Số tiền phải nhỏ hơn 500000");
                isValid = false;
            } else {
                Regex__P4__4__2.setText(""); // Xóa thông báo lỗi
            }
        } catch (NumberFormatException e) {
            Regex__P4__4__2.setText("Số tiền phải là số hợp lệ");
            isValid = false;
        }

        // Nếu không hợp lệ, dừng xử lý
        if (!isValid) {
            return;
        }

        Violation newViolation = violationdelete;
        newViolation.setName(newName);
        newViolation.setPrice(newPrice);
        ViolationBUS violationBUS = new ViolationBUS();
        boolean insertResult = violationBUS.update(newViolation);
        if (insertResult) {
            showAlert("Thành Công", "Sửa vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();
            viphamField.clear();
            tienPhatField.clear();
        } else {
            showAlert("Thất Bại", "Không thể sửa vi phạm", AlertType.ERROR);
        }
    }

    public void handleAddViolation() {
        boolean isValid = true; // Biến kiểm tra tính hợp lệ

        String newName = viphamField.getText().trim();
        String priceText = tienPhatField.getText().trim();
        Double newPrice = null; // Khai báo nhưng chưa khởi tạo

        // Kiểm tra tên vi phạm không được để trống
        if (newName.isEmpty()) {
            Regex__P4__4__1.setText("Tên vi phạm không được để trống");
            isValid = false;
        } else if (newName.matches(".*\\d.*")) { // Kiểm tra xem tên có chứa số hay không
            Regex__P4__4__1.setText("Tên vi phạm không được chứa số");
            isValid = false;
        } else {
            Regex__P4__4__1.setText(""); // Xóa thông báo lỗi
        }

        // Kiểm tra số tiền
        try {
            newPrice = Double.parseDouble(priceText);
            if (newPrice <= 0) {
                Regex__P4__4__2.setText("Số tiền phải lớn hơn 0");
                isValid = false;
            } else if (newPrice >= 500000) {
                Regex__P4__4__2.setText("Số tiền phải nhỏ hơn 500000");
                isValid = false;
            } else {
                Regex__P4__4__2.setText(""); // Xóa thông báo lỗi
            }
        } catch (NumberFormatException e) {
            Regex__P4__4__2.setText("Số tiền phải là số hợp lệ");
            isValid = false;
        }

        // Nếu không hợp lệ, dừng xử lý
        if (!isValid) {
            return;
        }

        // Tạo đối tượng Violation và thêm vào cơ sở dữ liệu
        Violation newViolation = new Violation();
        newViolation.setName(newName);
        newViolation.setPrice(newPrice);
        ViolationBUS violationBUS = new ViolationBUS();
        boolean insertResult = violationBUS.add(newViolation);

        if (insertResult) {
            showAlert("Thành Công", "Thêm vi phạm thành công", AlertType.CONFIRMATION);
            initViolation();
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

    // Declare a variable to store the last selected row
    private ViolationTicket lastSelectedRow_ViolationTicket = null;

    public void handleViolationTicket() {
        tableviolationticket.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                ViolationTicket selectedRow = tableviolationticket.getSelectionModel().getSelectedItem();

                // Kiểm tra nếu hàng được chọn giống với hàng đã chọn trước đó
                if (selectedRow != null && selectedRow.equals(lastSelectedRow_ViolationTicket)) {
                    // Nếu nhấp lần thứ hai, xóa các trường và cho phép nhập liệu lại
                    maPPField.setText("");
                    maPhatCombobox.setValue(null);
                    maPThuCombobox.setValue(null);
                    maSluongField1.setText("");
                    ngayGhiPPField.setValue(null);
                    ghiChuPPField.setText("");

                    // Đảm bảo tất cả các trường có thể chỉnh sửa lại
                    maPPField.setDisable(false);
                    maPhatCombobox.setDisable(false);
                    maPThuCombobox.setDisable(false);
                    maSluongField1.setDisable(false);
                    ngayGhiPPField.setDisable(false);
                    ghiChuPPField.setDisable(false);

                    violationTicketdelete = null;
                    lastSelectedRow_ViolationTicket = null; // Đặt lại hàng được chọn cuối cùng

                    bnt__P4_1__add.setDisable(false);
                    tableviolationticket.getSelectionModel().clearSelection();
                } else {
                    // Nếu chọn một hàng mới, điền dữ liệu vào các trường
                    if (selectedRow != null) {
                        maPPField.setText(selectedRow.getViolationTicketID());
                        maPhatCombobox.setValue(selectedRow.getViolationID());
                        maPThuCombobox.setValue(selectedRow.getMonthlyRentBillID());
                        maSluongField1.setText(String.valueOf(selectedRow.getQuantity()));
                        ngayGhiPPField.setValue(selectedRow.getDate());
                        ghiChuPPField.setText(selectedRow.getNote());
                        violationTicketdelete = selectedRow;
                        lastSelectedRow_ViolationTicket = selectedRow;

                        // Đảm bảo các trường có thể chỉnh sửa
                        maPPField.setDisable(true);
                        maPhatCombobox.setDisable(true);
                        maPThuCombobox.setDisable(true);
                        maSluongField1.setDisable(true);
                        ngayGhiPPField.setDisable(true);
                        ghiChuPPField.setDisable(false);
                        bnt__P4_1__add.setDisable(true);
                        // Xóa các thông báo lỗi xác thực trước đó
                        Regex__P4__2__1.setText("");
                        Regex__P4__2__2.setText("");
                        Regex__P4__2__3.setText("");
                        Regex__P4__2__4.setText("");
                    }
                }
            }
        });
    }



    //xoa phieu phat

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
        boolean isValid = true;

        String maPhieuThu = maPhatCombobox.getSelectionModel().getSelectedItem();
        String maDichVu = maPThuCombobox.getSelectionModel().getSelectedItem();
        MonthlyRentBill monthlyRentBill1 = MonthlyRentBillBUS.getInstance().getMonthlyRentBillWithMRB_BuildingManager(maDichVu);
        if (maPhieuThu == null || maPhieuThu.isEmpty()) {
            Regex__P4__2__2.setText("Vui lòng chọn mã phiếu thu");
            isValid = false;
        } else {
            Regex__P4__2__2.setText("");
            if (LocalDate.now().isAfter(monthlyRentBill1.getDate().plusMonths(1))) {
                showAlert("Thông báo", "Phiếu thu này đã hết hạn", AlertType.ERROR);
                return;
            }
        }

        LocalDate ngayGhi = ngayGhiPPField.getValue();
        String ghiChu = ghiChuPPField.getText();
        Double thanhTien = 0.0;

        // Validation for maPhieuThu


        // Validation for maDichVu
        if (maDichVu == null || maDichVu.isEmpty()) {

            Regex__P4__2__1.setText("Vui lòng chọn mã phiếu phạt");
            isValid = false;
        } else {

            Regex__P4__2__1.setText("");
        }

        // Validation for ngayGhi
        if (ngayGhi == null) {
            Regex__P4__2__4.setText("Ngày ghi không được để trống");
            isValid = false;
        } else {
            Regex__P4__2__4.setText("");
        }

        if (ngayGhi != null && ngayGhi.isBefore(monthlyRentBill1.getDate())||ngayGhi != null && ngayGhi.isAfter(monthlyRentBill1.getDate().plusMonths(1))) {
            Regex__P4__2__4.setText("Ngày ghi phải trong hạn 1 tháng của phiếu thu.");
            isValid = false;
        }

        // Validation for soLuong
        int soLuong = 0;
        if (maSluongField1.getText().isEmpty()) {
            Regex__P4__2__3.setText("Số lượng không được để trống");
            isValid = false;
        } else {
            try {
                soLuong = Integer.parseInt(maSluongField1.getText());
                if (soLuong < 1 || soLuong > 10) {
                    Regex__P4__2__3.setText("Số lượng phải nằm trong khoảng từ 1 đến 10");
                    isValid = false;
                } else {
                    Regex__P4__2__3.setText("");
                }
            } catch (NumberFormatException e) {
                Regex__P4__2__3.setText("Số lượng phải là một số hợp lệ");
                isValid = false;
            }
        }

        // Stop execution if validation fails
        if (!isValid) {
            return;
        }

        // Calculate total price and proceed with adding ViolationTicket
        ViolationBUS violationBUS = new ViolationBUS();
        List<Violation> violationList = violationBUS.getAll();
        for (Violation violation1 : violationList) {
            if (violation1.getViolationID().equals(maPhieuThu)) {
                thanhTien = soLuong * violation1.getPrice();
                break;
            }
        }

        // Add the violation ticket logic (if all validations pass)
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
            showAlert("Thành Công", "Thêm phiếu vi phạm thành công: " + violationTickets.getViolationTicketID(), AlertType.CONFIRMATION);
            initViolationTicket();
            maPPField.setText(null);
            maPhatCombobox.setValue(null);
            maPThuCombobox.setValue(null);
            maSluongField1.setText("");
            ngayGhiPPField.setValue(null);
            ghiChuPPField.setText("");
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

                boolean updateSuccess = monthlyRentBillBUS.update(monthlyRentBill);
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

        ViolationTicket violationTicket = tableviolationticket.getSelectionModel().getSelectedItem();

        String ghiChu = ghiChuPPField.getText();






        violationTicket.setNote(ghiChu);

        ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
        boolean updateResult = violationTicketBUS.update(violationTicket);

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
    private Label Regex__P5__1 = new Label();

    @FXML
    private Label Regex__P5__2 = new Label();

    @FXML
    private Label Regex__P5__3 = new Label();

    @FXML
    private Label Regex__P5__4 = new Label();

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
    private ComboBox<String> comboBox__P5__4 = new ComboBox<>();

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
        comboBox__P5__4.setValue(null);
        TxtField__P5__3.setText("");
        comboBox__P5__3.setValue(null);
        TxtField__P5__4.setText("");
    }

    private Furniture lastSelectedFurniture = null;

    @FXML
    void showFurniture(MouseEvent event) {
        Furniture selectedFurniture = table__P5__1.getSelectionModel().getSelectedItem();

        // Nếu có nội thất được chọn
        if (selectedFurniture != null) {
            // Nếu nội thất được chọn giống với nội thất trước đó, xóa các trường nhập liệu
            if (selectedFurniture.equals(lastSelectedFurniture)) {
                comboBox__P5__4.setValue(null);
                TxtField__P5__3.clear();
                comboBox__P5__3.setValue(null);
                TxtField__P5__4.clear();

                // Reset biến lưu nội thất được chọn
                lastSelectedFurniture = null;
                table__P5__1.getSelectionModel().clearSelection();
                bnt__P5__add.setDisable(false);
                Regex__P5__1.setText("");
                Regex__P5__2.setText("");
                Regex__P5__3.setText("");
                Regex__P5__4.setText("");
                comboBox__P5__4.setDisable(false);

            } else {
                // Nếu khác nội thất trước đó, hiển thị thông tin nội thất
                comboBox__P5__4.setValue(selectedFurniture.getApartmentID());
                TxtField__P5__3.setText(selectedFurniture.getNameFurniture());
                comboBox__P5__3.setValue(selectedFurniture.getCondition());
                TxtField__P5__4.setText(String.valueOf(selectedFurniture.getPrice()));

                // Cập nhật lại nội thất vừa được chọn
                lastSelectedFurniture = selectedFurniture;
                bnt__P5__add.setDisable(true);
                Regex__P5__1.setText("");
                Regex__P5__2.setText("");
                Regex__P5__3.setText("");
                Regex__P5__4.setText("");
                comboBox__P5__4.setDisable(true);
            }
        }
    }

    @FXML
    void suaNoiThat(ActionEvent event) {
        Furniture furniture = table__P5__1.getSelectionModel().getSelectedItem();

        if (furniture == null) {
            showAlert("Thông báo", "Không có nội thất nào được chọn để cập nhật", AlertType.INFORMATION);
            return;
        }

        boolean isValid = true;  // Biến đánh dấu xem có hợp lệ hay không

        // Kiểm tra tên nội thất
        String nameFurniture = TxtField__P5__3.getText();
        if (nameFurniture.isEmpty()) {
            Regex__P5__2.setText("Không được để trống");
            isValid = false;
        } else if (!nameFurniture.matches("^[^0-9!@#$%^&*()_+=-]+$")) {
            Regex__P5__2.setText("Không được chứa số hoặc ký tự đặc biệt");
            isValid = false;
        } else {
            Regex__P5__2.setText("");  // Xóa thông báo lỗi
            furniture.setNameFurniture(nameFurniture);
        }

        // Kiểm tra giá
        String priceText = TxtField__P5__4.getText();
        if (priceText.isEmpty()) {
            Regex__P5__3.setText("Không được để trống");
            isValid = false;
        } else {
            try {
                double price = Double.parseDouble(priceText);
                if (price <= 0) {
                    Regex__P5__3.setText("Giá phải lớn hơn 0");
                    isValid = false;
                } else {
                    Regex__P5__3.setText("");  // Xóa thông báo lỗi
                    furniture.setPrice(price);
                }
            } catch (NumberFormatException e) {
                Regex__P5__3.setText("Giá không hợp lệ");
                isValid = false;
            }
        }

        // Kiểm tra điều kiện
        String condition = comboBox__P5__3.getSelectionModel().getSelectedItem();
        if (condition == null) {
            Regex__P5__4.setText("Không được để trống");
            isValid = false;
        } else {
            Regex__P5__4.setText("");  // Xóa thông báo lỗi
            furniture.setCondition(condition);
        }

        // Nếu tất cả các trường hợp hợp lệ
        if (isValid) {
            FurnitureBUS furnitureBUS = new FurnitureBUS();
            boolean updateSuccess = furnitureBUS.update(furniture);
            if (updateSuccess) {
                int selectedIndex = table__P5__1.getSelectionModel().getSelectedIndex();
                furnitureObservableList.set(selectedIndex, furniture);
                table__P5__1.refresh();
                refreshFormFurniture();
                showAlert("Thông báo", "Cập nhật thành công nội thất có mã nội thất: " + furniture.getFurnitureID(), AlertType.INFORMATION);
            } else {
                System.err.println("Không thể cập nhật nội thất trong cơ sở dữ liệu.");
            }
        }
    }


    @FXML
    void themNoiThat(ActionEvent event) {
        try {
            Furniture furniture = new Furniture();
            boolean isValid = true;  // Biến đánh dấu xem có hợp lệ hay không

            // Kiểm tra Apartment ID
            String apartmentID = comboBox__P5__4.getSelectionModel().getSelectedItem();
            if (apartmentID == null || apartmentID.isEmpty()) {
                Regex__P5__1.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P5__1.setText("");  // Xóa thông báo lỗi
                furniture.setApartmentID(apartmentID);
            }

            // Kiểm tra tên nội thất
            String nameFurniture = TxtField__P5__3.getText();
            if (nameFurniture.isEmpty()) {
                Regex__P5__2.setText("Không được để trống");
                isValid = false;
            }   else if (!nameFurniture.matches("^[^0-9!@#$%^&*()_+=-]+$")) {
                Regex__P5__1.setText("Không được chứa số hoặc ký tự đặc biệt");
                isValid = false;
            } else {
                Regex__P5__2.setText("");  // Xóa thông báo lỗi
                furniture.setNameFurniture(nameFurniture);
            }

            // Kiểm tra giá
            String priceText = TxtField__P5__4.getText();
            if (priceText.isEmpty()) {
                Regex__P5__3.setText("Không được để trống");
                isValid = false;
            } else {
                double price = Double.parseDouble(priceText);
                if (price <= 0) {
                    Regex__P5__3.setText("Giá phải lớn hơn 0");
                    isValid = false;
                } else {
                    Regex__P5__3.setText("");  // Xóa thông báo lỗi
                    furniture.setPrice(price);
                }
            }

            // Kiểm tra điều kiện
            String condition = comboBox__P5__3.getSelectionModel().getSelectedItem();
            if (condition == null) {
                Regex__P5__4.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P5__4.setText("");  // Xóa thông báo lỗi
                furniture.setCondition(condition);
            }

            // Nếu tất cả các trường hợp hợp lệ
            if (isValid) {
                FurnitureBUS furnitureBUS = new FurnitureBUS();
                furnitureBUS.add(furniture);

                furnitureObservableList.add(furniture);
                showAlert("Thông báo", "Thêm thành công nội thất có mã nội thất: "+ furniture.getFurnitureID(), AlertType.INFORMATION);
                refreshFormFurniture();
            }

        } catch (NumberFormatException e) {
            // Bắt lỗi nếu không thể chuyển đổi giá
            Regex__P5__3.setText("Giá không hợp lệ");
            e.printStackTrace();
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
                showAlert("Thông báo", "Xóa thành công", AlertType.CONFIRMATION);
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

    @FXML
    private Label Regex__P6__1 = new Label();

    @FXML
    private Label Regex__P6__2 = new Label();

    @FXML
    private Label Regex__P6__3 = new Label();

    @FXML
    private Label Regex__P6__4 = new Label();

    @FXML
    private Label Regex__P6__5 = new Label();

    @FXML
    private Label Regex__P6__6 = new Label();

    @FXML
    private Label Regex__P6__7 = new Label();

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

    private LeaseAgreement lastSelectedLeaseAgreement = null;

    @FXML
    void showLeaseAgreement(MouseEvent event) {
        LeaseAgreement leaseAgreement = table__P6__1.getSelectionModel().getSelectedItem();

        // Nếu hợp đồng thuê được chọn không null
        if (leaseAgreement != null) {
            // Nếu hợp đồng được chọn giống với hợp đồng trước đó, xóa các trường nhập liệu
            if (leaseAgreement.equals(lastSelectedLeaseAgreement)) {
                TxtField__P6__1.clear();
                TxtField__P6__2.clear();
                DP_P6_1.setValue(null);
                DP_P6_2.setValue(null);
                DP_P6_3.setValue(null);
                comboBox__P6__3.setValue(null);
                TxtField__P6__5.clear();
                TxtField__P6__6.clear();
                combobox_TxtField__P6__2.setValue(null);
                combobox_TxtField__P6__3.setValue(null);

                combobox_TxtField__P6__2.setDisable(false);
                combobox_TxtField__P6__3.setDisable(false);
                DP_P6_1.setDisable(false);
                DP_P6_2.setDisable(false);
                comboBox__P6__3.setDisable(false);
                TxtField__P6__6.setDisable(false);

                // Xóa các thông báo lỗi (nếu có)
                // Ví dụ:
                // Regex__P6__1.setText("");

                // Reset biến lưu hợp đồng đã chọn và bỏ chọn hàng trong bảng
                lastSelectedLeaseAgreement = null;
                table__P6__1.getSelectionModel().clearSelection();  // Bỏ chọn dòng trong bảng
                bnt__P6__add.setDisable(false);
            } else {
                // Nếu khác hợp đồng trước đó, hiển thị thông tin hợp đồng
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

                // Cập nhật lại hợp đồng vừa được chọn
                lastSelectedLeaseAgreement = leaseAgreement;
                bnt__P6__add.setDisable(true);
                combobox_TxtField__P6__2.setDisable(true);
                combobox_TxtField__P6__3.setDisable(true);
                DP_P6_1.setDisable(true);
                DP_P6_2.setDisable(true);
                comboBox__P6__3.setDisable(true);
                TxtField__P6__6.setDisable(true);
            }
        }
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

        leaseAgreement.setDeposit(Double.parseDouble(TxtField__P6__5.getText()));
        LeaseAgreementBUS leaseAgreementBUS = new LeaseAgreementBUS();
        boolean updateSuccess = leaseAgreementBUS.update(leaseAgreement);
        if (updateSuccess) {
            int selectedIndex = table__P6__1.getSelectionModel().getSelectedIndex();
            leaseAgreementObservableList.set(selectedIndex, leaseAgreement);
            table__P6__1.refresh();
            refreshFormLeaseAgreement();
            showAlert("Thông báo", "Cập nhật thành công", AlertType.CONFIRMATION);
        } else {
            System.err.println("Không thể cập nhật hợp đồng trong cơ sở dữ liệu.");
        }
    }

    @FXML
    void themHopDong(ActionEvent event) {
        LeaseAgreement selectedLeaseAgreement = table__P6__1.getSelectionModel().getSelectedItem();

        if (selectedLeaseAgreement != null) {
            // Kiểm tra xem giá trị Tenant ID và Apartment ID có thay đổi hay không
            String selectedTenantID = selectedLeaseAgreement.getTenantID();
            String selectedApartmentID = selectedLeaseAgreement.getApartmentID();
            String currentTenantID = combobox_TxtField__P6__2.getSelectionModel().getSelectedItem();
            String currentApartmentID = combobox_TxtField__P6__3.getSelectionModel().getSelectedItem();

            boolean isTenantChanged = !selectedTenantID.equals(currentTenantID);
            boolean isApartmentChanged = !selectedApartmentID.equals(currentApartmentID);

            // Nếu cả Tenant ID và Apartment ID không thay đổi, không cho phép thêm
            if (!isTenantChanged && !isApartmentChanged) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Hợp đồng giữa khách hàng '" + selectedTenantID + "' và căn hộ '" + selectedApartmentID + "' đã tồn tại.");
                alert.showAndWait();
                return; // Thoát khỏi phương thức và không thêm hợp đồng
            }
        }

        try {
            boolean isValid = true;

            // Kiểm tra Tenant ID
            if (combobox_TxtField__P6__2.getValue() == null) {
                Regex__P6__1.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P6__1.setText("");
            }

            // Kiểm tra Apartment ID
            if (combobox_TxtField__P6__3.getValue() == null) {
                Regex__P6__2.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P6__2.setText("");
            }

            // Kiểm tra ngày ký hợp đồng
                if (DP_P6_1.getValue() == null) {
                    Regex__P6__3.setText("Không được để trống");
                    isValid = false;
                } else {
                    Regex__P6__3.setText("");
                }


            // Kiểm tra ngày bắt đầu thuê
            if (DP_P6_2.getValue() == null) {
                Regex__P6__4.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P6__4.setText("");
            }

            // Kiểm tra thời hạn thuê
            if (comboBox__P6__3.getValue() == null) {
                Regex__P6__5.setText("Không được để trống");
                isValid = false;
            } else {
                Regex__P6__5.setText("");
            }

            // Kiểm tra tiền đặt cọc
            if (TxtField__P6__5.getText().isEmpty()) {
                Regex__P6__6.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    double deposit = Double.parseDouble(TxtField__P6__5.getText());
                    if (deposit < 0) {
                        Regex__P6__6.setText("Tiền đặt cọc phải lớn hơn hoặc bằng 0");
                        isValid = false;
                    } else {
                        Regex__P6__6.setText("");
                    }
                } catch (NumberFormatException e) {
                    Regex__P6__6.setText("Tiền đặt cọc phải là số hợp lệ");
                    isValid = false;
                }
            }

            // Kiểm tra tiền thuê hàng tháng
            if (TxtField__P6__6.getText().isEmpty()) {
                Regex__P6__7.setText("Không được để trống");
                isValid = false;
            } else {
                try {
                    double monthlyRent = Double.parseDouble(TxtField__P6__6.getText());
                    if (monthlyRent < 0) {
                        Regex__P6__7.setText("Tiền thuê phải lớn hơn hoặc bằng 0");
                        isValid = false;
                    } else {
                        Regex__P6__7.setText("");
                    }
                } catch (NumberFormatException e) {
                    Regex__P6__7.setText("Tiền thuê phải là số hợp lệ");
                    isValid = false;
                }
            }

            // Nếu có bất kỳ lỗi nào, thoát khỏi phương thức
            if (!isValid) {
                return;
            }

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
            List<Apartment> apartments = apartmentObservableList;
            List<String> apartmentIDList = new ArrayList<>();
            for (Apartment apartment : apartments) {
                apartmentIDList.add(apartment.getApartmentID());
            }
            comboBox__P5__4.getItems().addAll(apartmentIDList);

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

            //list service combobox
            ServiceBUS serviceBUS = new ServiceBUS();
            List<Service> serviceList = serviceBUS.getAll();
            List<String> serviceListID = new ArrayList<>();
            for (Service service : serviceList) {
                serviceListID.add(service.getServiceID());
            }
            maDichVuCombobox.getItems().addAll(serviceListID);

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
            maDichVuCombobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    String maPhieuThu_fixed = newValue.toString();
                    Service service = ServiceBUS.getInstance().getService(maPhieuThu_fixed);

                    if (service.getType().equals("fixed")) {
                        // Nếu dịch vụ là fixed, đặt số lượng là 1 và vô hiệu hóa trường số lượng
                        soLuongField.setText("1");
                        soLuongField.setDisable(true);  // Không cho phép chỉnh sửa
                    } else {
                        // Nếu không phải fixed, kích hoạt lại trường số lượng để người dùng có thể chỉnh sửa
                        soLuongField.setDisable(false);
                        soLuongField.clear();
                    }
                }
            });
            initServiceTicket();

            //list violation combobox
            List<Violation> violationList = ViolationBUS.getInstance().getAll();
            List<String> violationListID = new ArrayList<>();
            for (Violation violation1 : violationList) {
                violationListID.add(violation1.getViolationID());
            }
            maPhatCombobox.getItems().addAll(violationListID);

            // phat
            initViolation();

            // Phieu Phat
            keyenter();


            initViolationTicket();

            comboBox__P6__3.getItems().addAll(6.0, 12.0);
            comboBox__P6__3.setPromptText("");

            //list id tenant trong hop dong
            TenantBUS tenant_c = new TenantBUS();
            List<Tenant> listTenent_C = tenant_c.getTenantsNotInLeaseAgreement();
            List<String> differenceList = new ArrayList<>();

            for (Tenant tenant : listTenent_C) {
                    differenceList.add(tenant.getTenantID());
            }
            combobox_TxtField__P6__2.getItems().addAll(differenceList);

            //lay id tenant nam trong hop dong
            List<Tenant> listTenent_D = tenant_c.getTenantsInLeaseAgreement();
            List<String> differenceList1 = new ArrayList<>();

            for (Tenant tenant : listTenent_D) {
                differenceList1.add(tenant.getTenantID());
            }

            Combobox__P2_1__2.getItems().addAll(differenceList1);

            ApartmentBUS apartment_c = new ApartmentBUS();
            BuildingManagerBUS buildingManagerBUS = new BuildingManagerBUS();
            BuildingManager buildingManager = buildingManagerBUS.getBuildingManagerById(ID);

            List<Apartment> listApartment_C = apartment_c.getApartmentByBuildingID(buildingManager.getBuildingId());
            List<String> listApartmentID = new ArrayList<>();
            List<String> listApartmentID1 = new ArrayList<>();


            for (Apartment apartment: listApartment_C) {
                if (apartment.getStatus().equals("Còn trống")) {
                    listApartmentID.add(apartment.getApartmentID());
                } else {
                    listApartmentID1.add(apartment.getApartmentID());
                }

            }
            combobox_TxtField__P6__3.getItems().addAll(listApartmentID);

            Combobox__P3__4.getItems().addAll(listApartmentID1);

            initLeaseAgreement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logOut(MouseEvent event) {
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

//    public void logOut(ActionEvent actionEvent) {
//
//    }
}