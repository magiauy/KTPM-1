package com.example.managingbuildingjava;

import DTO.FinancialReport;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
    private Label time, monthlyRevenueLabel;
    private void loadPage(String page) throws IOException {
        stop = true;
        Parent root = null;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        bp.setCenter(root);
    }
    private void upddateMonthlyRevenueLabel(ArrayList<FinancialReport> financialReports){
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
                System.out.println(dem);
            }
        });
        thread.start();
    }
    @FXML
    void Close_Clicked(MouseEvent event){
        stop = true;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<FinancialReport> financialReports = FinancialReport.getAllFinancialReportsFromDatabase();
        upddateMonthlyRevenueLabel(financialReports);

    }
}