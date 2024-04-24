package BUS;

import DAO.FinancialReportDAO;
import DTO.FinancialReport;
import com.example.managingbuildingjava.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;

public class FinancialReportBUS {
    public FinancialReportBUS(ArrayList<FinancialReport> financialReports) {
        this.financialReports = financialReports;
    }
    private ArrayList<FinancialReport> financialReports = new ArrayList<>();
    private static FinancialReportBUS instance;
    public static FinancialReportBUS getInstance() {
        if (instance == null) {
            instance = new FinancialReportBUS();
        }
        return instance;
    }
    public FinancialReportBUS() {
        this.financialReports = FinancialReportDAO.getInstance().selectAll();
    }
    public ArrayList<FinancialReport> getAll() {
        FinancialReportDAO financialReportDAO = FinancialReportDAO.getInstance();
        return financialReportDAO.selectAll();
    }

    public boolean add(FinancialReport financialReport) {
        boolean check = FinancialReportDAO.getInstance().insert(financialReport) != 0;
        if (check) {
            this.financialReports.add(financialReport);
        }
        return check;
    }
    public boolean delete(FinancialReport financialReport) {
        boolean check = FinancialReportDAO.getInstance().delete(financialReport.getFinancialReportID()) != 0;
        if (check) {
            this.financialReports.remove(financialReport);
        }
        return check;
    }
    public boolean update(FinancialReport financialReport) {
        boolean check = FinancialReportDAO.getInstance().update(financialReport) != 0;
        if (check) {
            int index = getIndexByFinancialReportID(financialReport.getFinancialReportID());
            if (index != -1) {
                this.financialReports.set(index, financialReport);
            }
        }
        return check;
    }
    public int getIndexByFinancialReportID(String financialReportID) {
        for (int i = 0; i < this.financialReports.size(); i++) {
            if (this.financialReports.get(i).getFinancialReportID().equals(financialReportID)) {
                return i;
            }
        }
        return -1; // Not found
    }
    public void setMonthlyRevenueLabel(Label monthlyRevenueLabel){
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
    }
    public void setMonthlyOpexLabel(BarChart barChartOfMonthlyOpex){
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
    }

}
