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
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    public static CustomerController getInstance() {
        return new CustomerController();
    }

    private String ID;

    public void setID (String ID){
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public TextField TxtField__P1__search;
    public ComboBox comboBox__P1__1;
    public Label txtField__P1__1;
    public TextField TxtField__P2__search;
    public TextField TxtField__P4__search;
    public Button bnt__P1__search;
    public TextField TxtField__P3__search;
    private volatile boolean stop = false;
    private volatile Thread thread;
    int dem =0;
    @FXML
    private BorderPane bp;
    @FXML
    private Pane mp;
    @FXML
    private TextField TxtField__P1__1;
    @FXML
    private Button bnt__P1__add;

    @FXML
    private void page0 (MouseEvent event){
        stop = false;
        TimeNow();
        bp.setCenter(mp);
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
    private void page3 (MouseEvent event) throws IOException {
        loadPage("Customer-view-Page3");
    }
    @FXML
    private Label time;
    @FXML
    private Label monthlyRevenueLabel;
    @FXML
    private PieChart numberOfStatusLabel;
    @FXML
    private BarChart barChartOfMonthlyOpex;
    private void loadPage(String page) throws IOException {
        stop = true;
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
    public void updateMonthlyRevenueLabel() {
        if (monthlyRevenueLabel == null) {
            return;
        }
        try {
            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();

            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Lọc danh sách financialReports để chỉ chọn ra các báo cáo tài chính có ngày tương ứng với tháng hiện tại
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
                // Lấy giá trị monthlyRevenue của báo cáo đầu tiên trong danh sách và gán vào monthlyRevenueLabel
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
            FinancialReportBUS financialReportBUS = new FinancialReportBUS();
            ArrayList<FinancialReport> financialReports = financialReportBUS.getAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateMonthlyRevenueLabel();
        updateNumberOfStatus();
        drawLineChartOfMonthlyOpex();
    }
}