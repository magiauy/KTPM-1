package com.example.managingbuildingjava;

import BUS.*;
import DAO.ServiceTicketDAO;
import DTO.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.swing.text.TabableView;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.Scene;


public class CustomerController implements Initializable {
    private static CustomerController instance;
    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    private static String ID;

    public void setID (String ID){
        CustomerController.ID = ID;
    }

    public String getID() {
        return ID;
    }
    @FXML
    public TableView<MonthlyRentBill> table__P3__1 = new TableView<>();
    @FXML
    TableColumn<MonthlyRentBill, String> monthlyRentBillIdColumn = new TableColumn<MonthlyRentBill, String >();
    @FXML
    TableColumn<MonthlyRentBill, LocalDate> dateColumn = new TableColumn<MonthlyRentBill, LocalDate>();
    @FXML
    TableColumn<MonthlyRentBill, Integer> repaymentPeriodColumn  = new TableColumn<MonthlyRentBill, Integer>();
    @FXML
    TableColumn<MonthlyRentBill, Double> totalPaymentColumn = new TableColumn<MonthlyRentBill, Double>();
    @FXML
    TableColumn<MonthlyRentBill, String> statusColumn = new TableColumn<>();
    private volatile boolean stop = false;
    private volatile Thread thread;
    int dem =0;
    @FXML
    private BorderPane bp = new BorderPane();
    @FXML
    private Pane mp = new Pane();
    @FXML
    private void page0 (MouseEvent event) throws IOException{
        stop = false;
        TimeNow();
        bp.setCenter(mp);
        loadPage0();
    }
    @FXML
    private void page1 (MouseEvent event) throws IOException {
        loadPage("Customer-view-Page1");
    }
    @FXML
    private void page2 (MouseEvent event) throws IOException {
        loadPage("Customer-view-Page2");
    }
    @FXML
    private Label time;
    private void loadPage(String page) throws IOException {
//        stop = true;
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
//                System.out.println(dem);
            }
        });
        thread.start();
    }
    @FXML
    void Close_Clicked(MouseEvent event){
        stop = true;
    }
    @FXML
    private Label monthlyBillLabel = new Label();
    @FXML
    private Label statusOfMonthlyBills = new Label();
    @FXML
    private PieChart pieChart = new PieChart();
    @FXML
    private BarChart barChart;
    @FXML
    private Label fullname = new Label();
    @FXML
    private Label dob = new Label();
    @FXML
    private Label phone = new Label();
    @FXML
    private Label gender = new Label();
    @FXML
    private Label CCCD = new Label();
    //Table Page 0
    @FXML
    Label termLabel = new Label();
    @FXML
    private Label deposiLabel = new Label();
    @FXML
    private Label rentLabel = new Label();
    @FXML
    public TableView<Cohabitant> cohabitantTable = new TableView<>();
    @FXML
    TableColumn<Cohabitant, String>  nameCol = new TableColumn<Cohabitant, String>("Họ & Tên");
    @FXML
    TableColumn<Cohabitant, String>  phoneCol = new TableColumn<Cohabitant, String>();
    @FXML
    TableColumn<Cohabitant, LocalDate>  dobCol = new TableColumn<Cohabitant, LocalDate>();
    @FXML
    TableColumn<Cohabitant, String>  genderCol = new TableColumn<Cohabitant, String>();
    //Page 1
    @FXML
    private CheckBox parkingRegis = new CheckBox();
    @FXML
    private CheckBox playGroundRegis = new CheckBox();
    @FXML
    private CheckBox poolRegis = new CheckBox();
    @FXML
    private CheckBox gymRegis = new CheckBox();
    @FXML
    private CheckBox internetRegis = new CheckBox();
    @FXML
    private TextField noteGyms = new TextField();
    @FXML
    private TextField noteInternets = new TextField();
    @FXML
    private TextField noteParkings = new TextField();
    @FXML
    private TextField notePlayGrounds = new TextField();
    @FXML
    private TextField notePools = new TextField();
    @FXML
    TableColumn<ServiceUsuage, String> nameSerCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String> noteSersCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String> priceSerCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String> regisSerCol = new TableColumn<>();

    @FXML
    private TextField noteFixedRegis = new TextField();
    @FXML
    private ComboBox<String> comboBox__P1__21 = new ComboBox<>();
    @FXML
    private DatePicker selectSersDate = new DatePicker();
    @FXML
    TableColumn<ServiceUsuage, Double>quantitySersOldCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String>nameSerOldCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String> priceSerOldCol = new TableColumn<>();
    @FXML
    TableColumn<ServiceUsuage, String> regisSerOldCol = new TableColumn<>();
    @FXML
    TableView<ServiceUsuage> registeredSerTable = new TableView<>();
    @FXML
    TableView<ServiceUsuage> registeredSerOldTable = new TableView<>();

    //Page 2
    @FXML
    TableColumn<ViolatioUsage, String> violationTicketIDCol = new TableColumn<>();
    @FXML
    TableColumn<ViolatioUsage, String> nameVioCol = new TableColumn<>();
    @FXML
    TableColumn<ViolatioUsage, Double> priceVioCol = new TableColumn<>();
    @FXML
    TableColumn<ViolatioUsage, LocalDate> dateVioCol = new TableColumn<>();
    @FXML
    TableColumn<ViolatioUsage, String> noteVioCol = new TableColumn<>();
    @FXML
    TableView<ViolatioUsage> table__P3__2 = new TableView<>();

    @FXML
    private TextField TxtField__P3__search = new TextField();
    @FXML
    private ImageView search = new ImageView();

    public void loadPage0(){
        TimeNow();
        setMonthlyBillLabel();
        updateInfor();
        updatePieChart();
        setTabelLeaseAgreement();
        setTableCohabitant();

    }
    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private Button logout;
    @FXML
    void logout(MouseEvent event) throws IOException {
        Platform.exit();
    }

    @FXML
    void searchBill(MouseEvent event) {
        String searchText = TxtField__P3__search.getText();
        if(Objects.equals(searchText, "")){
            showAlert("Lỗi", "Vui lòng nhập mã phiếu thu.", Alert.AlertType.ERROR);
        } else {
            String mrID = ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID()).getFirst();

            monthlyRentBillIdColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, String>("monthlyRentBillID"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, LocalDate>("date"));
            repaymentPeriodColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, Integer>("repaymentPeriod"));
            totalPaymentColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, Double>("totalPayment"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, String>("status"));

            ObservableList<MonthlyRentBill> data = FXCollections.observableArrayList(MonthlyRentBillBUS.getInstance().getMonthlyRentBillWithMRB(searchText));
            if (data.isEmpty() || data.getFirst() == null){
                showAlert("Lỗi", "Mã phiếu không tồn tại.", Alert.AlertType.ERROR);
            }else {
                table__P3__1.setItems(data);
            }
        }
    }

    void updateTableVio(){
        violationTicketIDCol.setCellValueFactory(new PropertyValueFactory<ViolatioUsage, String>("id"));
        nameVioCol.setCellValueFactory(new PropertyValueFactory<ViolatioUsage, String>("name"));
        priceVioCol.setCellValueFactory(new PropertyValueFactory<ViolatioUsage, Double>("price"));
        dateVioCol.setCellValueFactory(new PropertyValueFactory<ViolatioUsage, LocalDate>("date"));
        noteVioCol.setCellValueFactory(new PropertyValueFactory<ViolatioUsage, String>("note"));

        ViolationTicketBUS.getInstance().setTable(table__P3__2);

    }
    @FXML
    void regisMobile(){
        LocalDate dateRegis = selectSersDate.getValue();
        String note = noteFixedRegis.getText();
        String serName = comboBox__P1__21.getValue();

        if (dateRegis == null || serName == null){
            showAlert("Lỗi", "Vui lòng thử lại.", Alert.AlertType.ERROR);
        }
        else{
            ServiceTicketBUS.getInstance().repairInforRegis(serName, dateRegis, note);
            updateTableNewRegisServ();
            comboBox__P1__21.setValue(null);
            selectSersDate.setValue(null);
        }
    }

    void updateTableOldRegisServ(){
        ServiceBUS.getInstance().setCombox(comboBox__P1__21);

        nameSerOldCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("name"));
        priceSerOldCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("totalAmount"));
        quantitySersOldCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, Double>("quantity"));
        regisSerOldCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("date"));

        ServiceTicketBUS.getInstance().setTableOldRegisServ(registeredSerOldTable);
    }

    void updateTableNewRegisServ(){

        nameSerCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("name"));
        priceSerCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("totalAmount"));
        regisSerCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("date"));
        noteSersCol.setCellValueFactory(new PropertyValueFactory<ServiceUsuage, String>("note"));

        ServiceTicketBUS.getInstance().setTableRegisServ(registeredSerTable);
    }
    @FXML
    void regisFixed() {
        if (!parkingRegis.isSelected() && !playGroundRegis.isSelected() && !poolRegis.isSelected() && !gymRegis.isSelected() && !internetRegis.isSelected()){
            showAlert("Lỗi", "Vui lòng tích vào ô đăng ký.", Alert.AlertType.ERROR);
        }
        else{
            LocalDate currentDate = LocalDate.now();
            if(parkingRegis.isSelected()){
                String note = "";
                if (noteParkings.getText() != null){
                    note = noteParkings.getText();
                }
                ServiceTicketBUS.getInstance().regisServ("SERV3",note, currentDate);
                updateTableNewRegisServ();
            }
            if(playGroundRegis.isSelected()){
                String note = "";
                if (noteGyms.getText() != null){
                    note = noteGyms.getText();
                }
                ServiceTicketBUS.getInstance().regisServ("SERV11",note, currentDate);
                updateTableNewRegisServ();

            }
            if(poolRegis.isSelected()){
                String note = "";
                if (notePools.getText() != null){
                    note = notePools.getText();
                }
                ServiceTicketBUS.getInstance().regisServ("SERV9",note, currentDate);
                updateTableNewRegisServ();

            }
            if(gymRegis.isSelected()){
                String note = "";
                if (noteInternets.getText() != null){
                    note = noteInternets.getText();
                }
                ServiceTicketBUS.getInstance().regisServ("SERV5",note, currentDate);
                updateTableNewRegisServ();

            }
            if(internetRegis.isSelected()){
                String note = "";
                if (noteInternets.getText() != null){
                    note = noteInternets.getText();
                }
                ServiceTicketBUS.getInstance().regisServ("SERV4",note, currentDate);
                updateTableNewRegisServ();

            }
        }
        internetRegis.setSelected(false);
        parkingRegis.setSelected(false);
        playGroundRegis.setSelected(false);
        poolRegis.setSelected(false);
        gymRegis.setSelected(false);

        noteInternets.setText(null);
        notePools.setText(null);
        noteGyms.setText(null);
        notePlayGrounds.setText(null);
        noteParkings.setText(null);

    }

    void setTableCohabitant(){
        // Thiết lập font cho các cột
        String font = "Times New Roman";
        nameCol.setStyle("-fx-font-family: '" + font + "';");
        phoneCol.setStyle("-fx-font-family: '" + font + "';");
        dobCol.setStyle("-fx-font-family: '" + font + "';");
        genderCol.setStyle("-fx-font-family: '" + font + "';");


        nameCol.setCellValueFactory(new PropertyValueFactory<Cohabitant, String>("fullName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Cohabitant, String>("phoneNumber"));
        dobCol.setCellValueFactory(new PropertyValueFactory<Cohabitant, LocalDate>("dateOfBirthDay"));
        genderCol.setCellValueFactory(new PropertyValueFactory<Cohabitant, String>("gender"));

        ObservableList<Cohabitant> data = FXCollections.observableArrayList(CohabitantBUS.getInstance().getCohabitantsWithTenantId(this.ID));
        cohabitantTable.setItems(data);


    }

    void setTabelLeaseAgreement(){
        ObservableList<LeaseAgreement> data = FXCollections.observableArrayList(LeaseAgreementBUS.getInstance().getLeaseAgreementsWithTenantId(this.ID));

        LeaseAgreementBUS.getInstance().updateTabelLeaseAgreement(data, termLabel, deposiLabel, rentLabel);
    }

    void updatePieChart() {
        try {
            MonthlyRentBillBUS.getInstance().updatePiechart(pieChart,this.ID);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateInfor(){
        try{
            TenantBUS.getInstance().setInfor(fullname, phone,dob,gender,CCCD,this.ID);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void setMonthlyBillLabel() {
        try{
            MonthlyRentBillBUS.getInstance().updateMonthlyBill(monthlyBillLabel, statusOfMonthlyBills, this.ID);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void setTableMonthlyRentBill(){
        monthlyRentBillIdColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, String>("monthlyRentBillID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, LocalDate>("date"));
        repaymentPeriodColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, Integer>("repaymentPeriod"));
        totalPaymentColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, Double>("totalPayment"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<MonthlyRentBill, String>("status"));

        ObservableList<MonthlyRentBill> data = FXCollections.observableArrayList(MonthlyRentBillBUS.getInstance().getMonthlyRentBillsWithTenantId(this.ID));
        table__P3__1.setItems(data);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Page 0
        loadPage0();

        //Page 1
        updateTableNewRegisServ();
        updateTableOldRegisServ();

        //Page 2
        setTableMonthlyRentBill();
        updateTableVio();
    }
}