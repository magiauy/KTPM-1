package com.example.managingbuildingjava;

import BUS.ApartmentBUS;
import BUS.TenantBUS;
import DTO.Apartment;
import DTO.Cohabitant;
import DTO.Tenant;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.time.LocalDate;
import java.util.*;

public class BuildingManagerController implements Initializable {

    private ObservableList<Apartment> apartmentsList = new ObservableList<Apartment>() {
        @Override
        public void addListener(ListChangeListener<? super Apartment> listChangeListener) {

        }

        @Override
        public void removeListener(ListChangeListener<? super Apartment> listChangeListener) {

        }

        @Override
        public boolean addAll(Apartment... apartments) {
            return false;
        }

        @Override
        public boolean setAll(Apartment... apartments) {
            return false;
        }

        @Override
        public boolean setAll(Collection<? extends Apartment> collection) {
            return false;
        }

        @Override
        public boolean removeAll(Apartment... apartments) {
            return false;
        }

        @Override
        public boolean retainAll(Apartment... apartments) {
            return false;
        }

        @Override
        public void remove(int i, int i1) {

        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<Apartment> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(Apartment apartment) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Apartment> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Apartment> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public Apartment get(int index) {
            return null;
        }

        @Override
        public Apartment set(int index, Apartment element) {
            return null;
        }

        @Override
        public void add(int index, Apartment element) {

        }

        @Override
        public Apartment remove(int index) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<Apartment> listIterator() {
            return null;
        }

        @Override
        public ListIterator<Apartment> listIterator(int index) {
            return null;
        }

        @Override
        public List<Apartment> subList(int fromIndex, int toIndex) {
            return List.of();
        }

        @Override
        public void addListener(InvalidationListener invalidationListener) {

        }

        @Override
        public void removeListener(InvalidationListener invalidationListener) {

        }
    };
    // thuộc tính căn hộ
    @FXML
    private TextField TxtField__P1__1 = new TextField();

    @FXML
    private TextField TxtField__P1__2 = new TextField();

    @FXML
    private TextField TxtField__P1__3 = new TextField();

    @FXML
    private TextField TxtField__P1__5 = new TextField();

    @FXML
    private TextField TxtField__P1__search;

    @FXML
    private TextField TxtField__P1__4 = new TextField();

    @FXML
    private Button bnt__P1__1;

    @FXML
    private Button bnt__P1__add;

    @FXML
    private Button bnt__P1__delete;

    @FXML
    private Button bnt__P1__update;

    @FXML
    private ComboBox<String> comboBox__P1__1;

    @FXML
    private ComboBox<String> comboBox__P1__2;

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
    ;

    @FXML
    private TableColumn<Apartment, Integer> soPhongNguTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, String> soPhongTable = new TableColumn<>();

    @FXML
    private TableColumn<Apartment, Integer> soPhongTamTable = new TableColumn<>();

    @FXML
    private TableView<Apartment> table__P1__1 = new TableView<>();

    private ObservableList<Apartment> apartmentObservableList;

    private ObservableList<Tenant> tenantObservableList;

//  thuộc tính khách thuê
    @FXML
    private TextField TxtField__P2_1__1 = new TextField();

    @FXML
    private TextField TxtField__P2_1__2 = new TextField();

    @FXML
    private TextField TxtField__P2_1__3 = new TextField();

    @FXML
    private TextField TxtField__P2_1__4 = new TextField();

    @FXML
    private TextField TxtField__P2_1__41 = new TextField();

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
    private TextField TxtField__P2__5 = new TextField();

    @FXML
    private TextField TxtField__P2__51 = new TextField();

    @FXML
    private TextField TxtField__P2__search = new TextField();

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
    private TableColumn<Cohabitant, LocalDate> ngaySinhCuDanTable = new TableColumn<>();

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
    private TableColumn<Cohabitant, String> tenKhachHangTable= new TableColumn<>();

    //



    private volatile boolean stop = false;

    private volatile Thread thread;

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
    private Label time;

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
                    if (time != null) time.setText(timenow);
                });
                dem++;
                System.out.println(dem);
            }
        });
        thread.start();
    }

    @FXML
    void Close_Clicked(MouseEvent event) {
        stop = true;
    }



    public void hienThiThongTin() {
        table__P1__1.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Apartment selectedRow = table__P1__1.getSelectionModel().getSelectedItem();
                if (selectedRow != null) {
                    TxtField__P1__1.setText(selectedRow.getApartmentID());
                    TxtField__P1__2.setText(selectedRow.getRoomNumber());
                    TxtField__P1__3.setText(String.valueOf(selectedRow.getArea()));
                    TxtField__P1__4.setText(String.valueOf(selectedRow.getBedrooms()));
                    TxtField__P1__5.setText(String.valueOf(selectedRow.getBathrooms()));
                    comboBox__P1__3.setValue(selectedRow.getFurniture());

                }

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //
            comboBox__P1__3.getItems().addAll("Đầy đủ", "Cơ bản", "Không");
            comboBox__P1__3.setPromptText("");
            maCanHoTable.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
            maToaNhaTable.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
            soPhongTable.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
            dienTichTable.setCellValueFactory(new PropertyValueFactory<>("area"));
            soPhongNguTable.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
            soPhongTamTable.setCellValueFactory(new PropertyValueFactory<>("bathrooms"));
            noiThatTable.setCellValueFactory(new PropertyValueFactory<>("furniture"));
            apartmentObservableList = getApartmentsList();
            table__P1__1.setItems(apartmentObservableList);
            //
            comboBox__P2__3.getItems().addAll("Nam", "Nữ");
            comboBox__P2__3.setPromptText("");
            maKhachHangTable_1.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
            hoKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tenKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            soDienThoaiKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            ngaySinhKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("dateOfBirthDay"));
            gioiTinhKhachHangTable.setCellValueFactory(new PropertyValueFactory<>("gender"));
            cCCDTable.setCellValueFactory(new PropertyValueFactory<>("citizenIdentityCard"));
            tenantObservableList = getTenantsList();
            table__P2__1.setItems(tenantObservableList);
            //

            hienThiThongTin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetText() {
        //
        TxtField__P1__1.setText("");
        TxtField__P1__3.setText("");
        TxtField__P1__2.setText("");
        TxtField__P1__4.setText("");
        TxtField__P1__5.setText("");
        comboBox__P1__3.getSelectionModel().select("Đầy đủ");
        //


    }

    public ObservableList<Apartment> getApartmentsList() {
        ObservableList<Apartment> apartmentObservableList = FXCollections.observableArrayList();
        ApartmentBUS apartmentBUS = new ApartmentBUS();
        List<Apartment> apartments = apartmentBUS.getAll();
        apartmentObservableList.addAll(apartments);
        return apartmentObservableList;
    }

    public ObservableList<Tenant> getTenantsList() {
        ObservableList<Tenant> tenantObservableList = FXCollections.observableArrayList();
        TenantBUS tenantBUS = new TenantBUS();
        List<Tenant> tenants = tenantBUS.getAll();
        tenantObservableList.addAll(tenants);
        return tenantObservableList;
    }

    @FXML
    void themCanHo(ActionEvent event) {
        try {
            Apartment apartment = new Apartment();
            apartment.setApartmentID(TxtField__P1__1.getText());
            apartment.setRoomNumber(TxtField__P1__2.getText());
            apartment.setArea(Double.parseDouble(TxtField__P1__3.getText()));
            apartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
            apartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
            apartment.setFurniture(comboBox__P1__3.getSelectionModel().getSelectedItem());
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            apartmentBUS.add(apartment);
            apartmentObservableList.add(apartment);
            resetText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void suaCanHo(ActionEvent event) {
        Apartment selectedApartment = table__P1__1.getSelectionModel().getSelectedItem();
        if (selectedApartment != null) {
            selectedApartment.setApartmentID(TxtField__P1__1.getText());
            selectedApartment.setRoomNumber(TxtField__P1__2.getText());
            selectedApartment.setArea(Double.parseDouble(TxtField__P1__3.getText()));
            selectedApartment.setBedrooms(Integer.parseInt(TxtField__P1__4.getText()));
            selectedApartment.setBathrooms(Integer.parseInt(TxtField__P1__5.getText()));
            selectedApartment.setFurniture(comboBox__P1__3.getValue());
            ApartmentBUS apartmentBUS = new ApartmentBUS();
            boolean updateSuccess = apartmentBUS.update(selectedApartment);
            if (updateSuccess) {
                int selectedIndex = table__P1__1.getSelectionModel().getSelectedIndex();
                apartmentObservableList.set(selectedIndex, selectedApartment);
                table__P1__1.refresh();
                resetText();
            } else {
                System.err.println("Không thể cập nhật căn hộ trong cơ sở dữ liệu.");
            }
        } else {
            System.out.println("Vui lòng chọn một căn hộ để chỉnh sửa.");
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
                resetText();
            } else{
                System.err.println("Không thể xóa căn hộ từ cơ sở dữ liệu.");
            }
        } else{
            System.out.println("Vui lòng chọn một căn hộ để xóa.");
        }
    }

    @FXML
    void themKhachHang(ActionEvent event) {

    }

    @FXML
    void xoaKhachHang(ActionEvent event) {

    }

    @FXML
    void suaKhachHang(ActionEvent event) {

    }
    @FXML
    void themCuDan(ActionEvent event) {

    }

    @FXML
    void suaCuDan(ActionEvent event) {

    }

    @FXML
    void xoaCuDan(ActionEvent event) {

    }


}