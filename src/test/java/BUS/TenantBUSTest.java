package BUS;

import DAO.MonthlyRentBillDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import DAO.LeaseAgreementDAO;
import DAO.TenantDAO;
import DTO.LeaseAgreement;
import DTO.MonthlyRentBill;
import DTO.Tenant;

import java.time.LocalDate;
import java.util.ArrayList;

class TenantBUSTest {

//    @BeforeAll
//    static void setUp() throws Exception {
//        TenantDAO tenantDAO = TenantDAO.getInstance();
//
//        // Insert Tenants
//        tenantDAO.insert(new Tenant("T1", "Nguyen", "Van A", "0123456789", LocalDate.parse("1990-01-01"), "Nam", "123456789"));
//        tenantDAO.insert(new Tenant("T2", "Tran", "Thi B", "0987654321", LocalDate.parse("1992-02-02"), "Nu", "987654321"));
//        tenantDAO.insert(new Tenant("T3", "Pham", "Van D", "0932154789", LocalDate.parse("1992-07-25"), "Nam", "852963147"));
//        tenantDAO.insert(new Tenant("T4", "Ho", "Thi E", "0789456123", LocalDate.parse("1997-03-05"), "Nu", "369852147"));
//        tenantDAO.insert(new Tenant("T5", "Vu", "Thi F", "0123456780", LocalDate.parse("1993-02-15"), "Nu", "369852741"));
//        LeaseAgreementDAO leaseAgreementDAO = LeaseAgreementDAO.getInstance();
//
//        // Insert Lease Agreements
//        leaseAgreementDAO.insert(new LeaseAgreement("LA1","APM1", "T1", "BM1", LocalDate.parse("2020-01-01"), LocalDate.parse("2021-01-01")
//                , LocalDate.parse("2022-01-01"), 12.0, 1000.0, 100.0));
//        leaseAgreementDAO.insert(new LeaseAgreement("LA2","APM2", "T2", "BM2", LocalDate.parse("2020-02-02"), LocalDate.parse("2021-02-02")
//                , LocalDate.parse("2022-02-02"), 12.0, 2000.0, 200.0));
//        leaseAgreementDAO.insert(new LeaseAgreement("LA3","APM3", "T3", "BM3", LocalDate.parse("2020-03-03"), LocalDate.parse("2021-03-03")
//                , LocalDate.parse("2022-03-03"), 12.0, 3000.0, 300.0));
//        leaseAgreementDAO.insert(new LeaseAgreement("LA4","APM4", "T4", "BM4", LocalDate.parse("2020-04-04"), LocalDate.parse("2021-04-04")
//                , LocalDate.parse("2022-04-04"), 12.0, 4000.0, 400.0));
//        leaseAgreementDAO.insert(new LeaseAgreement("LA5","APM5", "T5", "BM5", LocalDate.parse("2020-05-05"), LocalDate.parse("2021-05-05")
//                , LocalDate.parse("2022-05-05"), 12.0, 5000.0, 500.0));
//
//        MonthlyRentBillDAO monthlyRentBillDAO = MonthlyRentBillDAO.getInstance();
//        monthlyRentBillDAO.insert(new MonthlyRentBill("MRB1", "APM1", "T1", LocalDate.parse("2020-01-01"), 12, 1000.0, "Paid"));
//        monthlyRentBillDAO.insert(new MonthlyRentBill("MRB2", "APM2", "T2", LocalDate.parse("2020-02-02"), 12, 2000.0, "Paid"));
//        monthlyRentBillDAO.insert(new MonthlyRentBill("MRB3", "APM3", "T3", LocalDate.parse("2020-03-03"), 12, 3000.0, "Paid"));
//        monthlyRentBillDAO.insert(new MonthlyRentBill("MRB4", "APM4", "T4", LocalDate.parse("2020-04-04"), 12, 4000.0, "Paid"));
//        monthlyRentBillDAO.insert(new MonthlyRentBill("MRB5", "APM5", "T5", LocalDate.parse("2020-05-05"), 12, 5000.0, "Paid"));
//
//    }
    
    //Tìm kiếm tenant theo tenantID và có buildingManagerID tương ứng
    @Test
    void searchTenantsByTenantID_Existing() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("T1", "BM1");
        assertEquals(1, results.size());
        System.out.println("Expected: Size 1, Actual: Size " + results.size());
        System.out.println("Expected: Nguyen, Actual: " + results.get(0).getLastName());
        assertEquals("Nguyen", results.get(0).getLastName());
    }

    //Tìm kiếm tenant theo firstName và có buildingManagerID tương ứng
    @Test
    void searchTenantsByFirstName_Existing() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("Van A", "BM1");
        assertEquals(1, results.size());
        System.out.println("Expected: Size 1, Actual: Size " + results.size());
        assertEquals("T1", results.get(0).getTenantID());
        System.out.println("Expected: T1, Actual: " + results.get(0).getTenantID());

    }

    //Tìm kiếm tenant theo lastName và có buildingManagerID tương ứng
    @Test
    void searchTenantsByLastName_Existing() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("Nguyen", "BM1");
        assertEquals(1, results.size());
        System.out.println("Expected: Size 1, Actual: Size " + results.size());
        assertEquals("Van A", results.get(0).getFirstName());
        System.out.println("Expected: Van A, Actual: " + results.get(0).getFirstName());
    }

    //Tìm kiếm tenant theo tenantID không tồn tại
    @Test
    void searchTenantsByTenantID_NonExisting() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("T10", "BM1");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }

    //Tìm kiếm tenant theo firstName không tồn tại
    @Test
    void searchTenantsByFirstName_NonExisting() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("NonExistentName", "BM1");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }

    //Tìm kiếm tenant theo lastName không tồn tại
    @Test
    void searchTenantsWithDifferentBuildingManagerID() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        // T1 is managed by BM1, searching with BM2 should return empty
        ArrayList<Tenant> results = tenantBUS.searchTenants("T1", "BM2");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }

    //Tìm kiếm tenant theo tenantID và có buildingManagerID không tương ứng
    @Test
    void searchTenantsByTenantID_DifferentBM() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        // T3 is managed by BM3
        ArrayList<Tenant> results = tenantBUS.searchTenants("T3", "BM4");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }


    //Tìm kiếm tenant theo firstName và có buildingManagerID không tương ứng
    @Test
    void searchTenantsByFirstName_DifferentBM() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        // Searching for "Thi B" in BM2
        ArrayList<Tenant> results = tenantBUS.searchTenants("Tran", "BM3");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }

    //Tìm kiếm tenant theo lastName và có buildingManagerID không tương ứng
    @Test
    void searchTenantsByLastName_DifferentBM() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        // Searching for "Nguyen" in BM2
        ArrayList<Tenant> results = tenantBUS.searchTenants("Thi B", "BM3");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }

    //Tìm kiếm tenant với buildingManagerID tồn tại
    @Test
    void searchTenants_InvalidBuildingManagerID() {
        TenantBUS tenantBUS = TenantBUS.getInstance();
        ArrayList<Tenant> results = tenantBUS.searchTenants("Thi B", "BM999");
        assertEquals(0, results.size());
        System.out.println("Expected: Size 0, Actual: Size " + results.size());
    }
}