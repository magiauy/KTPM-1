package BUS;

import DAO.MonthlyRentBillDAO;
import DTO.FinancialReport;
import DTO.MonthlyRentBill;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.Objects;

public class MonthlyRentBillBUS {
    private ArrayList<MonthlyRentBill> monthlyRentBills = new ArrayList<>();
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
}
