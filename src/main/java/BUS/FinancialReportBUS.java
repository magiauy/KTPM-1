package BUS;

import DAO.ApartmentDAO;
import DAO.FinancialReportDAO;
import DAO.MonthlyRentBillDAO;
import DAO.ServiceTicketDAO;
import DAO.ViolationTicketDAO;
import DTO.Apartment;
import DTO.Building;
import DTO.FinancialReport;
import DTO.MonthlyRentBill;
import DTO.Service;
import DTO.ServiceTicket;
import DTO.ViolationTicket;

import com.example.managingbuildingjava.CustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;

public class FinancialReportBUS {

    private MonthlyRentBillDAO monthlyRentBillDAO;
    private ApartmentDAO apartmentDAO;

    private ServiceTicketDAO serviceTicketDAO;
    private ViolationTicketDAO violationTicketDAO;

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
        this.monthlyRentBillDAO = new MonthlyRentBillDAO();
        this.apartmentDAO = new ApartmentDAO();
        this.serviceTicketDAO = new ServiceTicketDAO();
        this.violationTicketDAO = new ViolationTicketDAO();
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

    public ArrayList<FinancialReport> search(LocalDate text, LocalDate text1, String type) {
        ArrayList<FinancialReport> result = new ArrayList<>();

        if (text == null || text1 == null || type == null || type.isEmpty()) {
            return result;
        }

        switch (type) {

            case "Tìm Theo Ngày" -> {
                for (FinancialReport i : this.financialReports) {
                    LocalDate res = i.getDate();
                    if (!res.isBefore(text) && !res.isAfter(text1)) {
                        result.add(i);
                    }

                }
            }

        }
        System.out.println("Kết quả tìm kiếm: " + result);
        return result;
    }

    public Float calculateMonthlyRevenueForBuilding(String buildingID, Month month, int year) {
        ArrayList<Apartment> apartments = apartmentDAO.getApartmentsByBuildingID(buildingID);
        float totalRevenue = 0.0f;
        for (Apartment apartment : apartments) {
            ArrayList<MonthlyRentBill> rentBills = monthlyRentBillDAO
                    .getMonthlyRentBillsByApartmentID(apartment.getApartmentID());
            for (MonthlyRentBill bill : rentBills) {
                if (bill.getDate().getMonth() == month && bill.getDate().getYear() == year
                        && bill.getStatus().equals("Paid")) {
                    System.out.println(bill);
                    totalRevenue += bill.getTotalPayment().floatValue();

                }
            }
        }

        System.out.println("Tổng doanh thu: " + totalRevenue);
        return totalRevenue;
    }

    public Float LoiNhuan(String buildingID, Month month, int year, Float cPhiVanHanh) {
        Float totalRevenue = 0.0f;
        Float totalExpenses = 0.0f;
        Float totalExpenses1 = 0.0f; 
        String idphieu = null; 

        ArrayList<Apartment> apartments = apartmentDAO.getApartmentsByBuildingID(buildingID);
        for (Apartment apartment : apartments) {
            ArrayList<MonthlyRentBill> rentBills = monthlyRentBillDAO
                    .getMonthlyRentBillsByApartmentID(apartment.getApartmentID());
            for (MonthlyRentBill bill : rentBills) {
                if (bill.getDate().getMonth() == month && bill.getDate().getYear() == year
                        && bill.getStatus().equals("Paid")) {
                    totalRevenue += bill.getTotalPayment().floatValue();
                    idphieu = bill.getMonthlyRentBillID();
                }
            }
        }

        if (idphieu != null) {
            ArrayList<ServiceTicket> services = serviceTicketDAO.getidSerVice(idphieu);
            if (services != null) {
                for (ServiceTicket service : services) {
                    if (service.getDate().getMonth() == month && service.getDate().getYear() == year) {
                        totalExpenses += service.getTotalAmount().floatValue();
                    }
                }
            }

            ArrayList<ViolationTicket> violations = violationTicketDAO.getidViolationTicket(idphieu);
            if (violations != null) {
                for (ViolationTicket violation : violations) {
                    if (violation.getDate().getMonth() == month && violation.getDate().getYear() == year) {
                        totalExpenses1 += violation.getPrice().floatValue();
                    }
                }
            }
        }

        Float profit = totalRevenue - totalExpenses - cPhiVanHanh - (totalExpenses1 * 0.8f);
        System.out.println("Tổng Lợi Nhuận: " + profit);
        return profit;
    }

}
