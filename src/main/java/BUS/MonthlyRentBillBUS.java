package BUS;
import com.example.managingbuildingjava.Customer;
import javafx.scene.paint.Color;

import DAO.MonthlyRentBillDAO;
import DTO.FinancialReport;
import DTO.MonthlyRentBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Objects;

public class MonthlyRentBillBUS {
    private ArrayList<MonthlyRentBill> monthlyRentBills = new ArrayList<>();

    private static MonthlyRentBillBUS instance;
    public static MonthlyRentBillBUS getInstance() {
        if (instance == null) {
            instance = new MonthlyRentBillBUS();
        }
        return instance;
    }


    public MonthlyRentBillBUS() {
        this.monthlyRentBills = MonthlyRentBillDAO.getInstance().selectAll();
    }
    public ArrayList<MonthlyRentBill> getAll() {
        MonthlyRentBillDAO monthlyRentBillDAO = MonthlyRentBillDAO.getInstance();
        return monthlyRentBillDAO.selectAll();
    }
    public boolean add(MonthlyRentBill monthlyRentBill) {
        boolean check = MonthlyRentBillDAO.getInstance().insert(monthlyRentBill) != 0;
        if (check) {
            this.monthlyRentBills.add(monthlyRentBill);
        }
        return check;
    }
    public boolean delete(MonthlyRentBill monthlyRentBill) {
        boolean check = MonthlyRentBillDAO.getInstance().delete(monthlyRentBill.getMonthlyRentBillID()) != 0;
        if (check) {
            this.monthlyRentBills.remove(monthlyRentBill);
        }
        return check;
    }
    public boolean update(MonthlyRentBill monthlyRentBill) {
        boolean check = MonthlyRentBillDAO.getInstance().update(monthlyRentBill) != 0;
        if (check) {
            int index = getIndexByMonthlyRentBillID(monthlyRentBill.getMonthlyRentBillID());
            if (index != -1) {
                this.monthlyRentBills.set(index, monthlyRentBill);
            }
        }
        return check;
    }

    public ArrayList<MonthlyRentBill> getMonthlyRentBillsWithTenantId(String tenantId) {
        ArrayList<MonthlyRentBill> monthlyRentBillsWithTenantID = new ArrayList<>();
        for (MonthlyRentBill monthlyRentBill : monthlyRentBills) {
            if (Objects.equals(monthlyRentBill.getTenantID(), tenantId)) {
                monthlyRentBillsWithTenantID.add(monthlyRentBill);
            }
        }
        return  monthlyRentBillsWithTenantID;
    }

    public int getIndexByMonthlyRentBillID(String monthlyRentBillID) {
        for (int i = 0; i < this.monthlyRentBills.size(); i++) {
            if (this.monthlyRentBills.get(i).getMonthlyRentBillID() == monthlyRentBillID) {
                return i;
            }
        }
        return -1; // Not found
    }

    public void setMonthlyRentBillsLabel(PieChart numberOfStatusLabel){
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
    }


    public void updateMonthlyBill(javafx.scene.control.Label monthlyBillLabel,javafx.scene.control.Label statusOfMonthlyBills, String tenantId){
        ObservableList<MonthlyRentBill> monthlyRentBills = FXCollections.observableArrayList(MonthlyRentBillBUS.getInstance().getMonthlyRentBillsWithTenantId(tenantId));

        double totalPayment = 0;
        String status = "";
        Month currentDate = LocalDate.now().getMonth();
        for (MonthlyRentBill monthlyRentBill : monthlyRentBills){
            if (Objects.equals(monthlyRentBill.getTenantID(), tenantId)) {
                if (monthlyRentBill.getDate().getMonth() == currentDate) {
                    totalPayment += monthlyRentBill.getTotalPayment();
                    status = monthlyRentBill.getStatus();
                }
            }
        }
        if ("Paid".equals(status)) {
            statusOfMonthlyBills.setTextFill(Color.BLUE);
            statusOfMonthlyBills.setText(status);
        } else {
            statusOfMonthlyBills.setTextFill(Color.RED);
            statusOfMonthlyBills.setText(status);
        }

        monthlyBillLabel.setText(totalPayment+"");
    }

    public void updatePiechart(PieChart pieChart, String ID) {
        ObservableList<MonthlyRentBill> monthlyRentBills = FXCollections.observableArrayList(MonthlyRentBillBUS.getInstance().getMonthlyRentBillsWithTenantId(ID));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Variables to hold total payment and count for each status
        int unpaidTotal = 0;
        int unpaidCount = 0;
        int paidTotal = 0;
        int paidCount = 0;
        int overdueTotal = 0;
        int overdueCount = 0;

        // Iterate through the monthly rent bills to calculate the total payment and count for each status
        for (MonthlyRentBill bill : monthlyRentBills) {
            switch (bill.getStatus()) {
                case "Unpaid":
                    unpaidTotal += bill.getTotalPayment();
                    unpaidCount++;
                    break;
                case "Paid":
                    paidTotal += bill.getTotalPayment();
                    paidCount++;
                    break;
                case "Overdue":
                    overdueTotal += bill.getTotalPayment();
                    overdueCount++;
                    break;
            }
        }

        // Add data to the pie chart
        if (unpaidCount > 0) {
            pieChartData.add(new PieChart.Data("Unpaid (" + unpaidCount + ")", unpaidTotal));
        }
        if (paidCount > 0) {
            pieChartData.add(new PieChart.Data("Paid (" + paidCount + ")", paidTotal));
        }
        if (overdueCount > 0) {
            pieChartData.add(new PieChart.Data("Overdue (" + overdueCount + ")", overdueTotal));
        }

        pieChart.setData(pieChartData);
    }
}
