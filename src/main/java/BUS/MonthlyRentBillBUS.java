package BUS;
import DAO.*;
import DTO.*;
import com.example.managingbuildingjava.CustomerController;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    public boolean checkExist(String id) {
        ArrayList<MonthlyRentBill> monthlyRentBills = MonthlyRentBillDAO.getInstance().selectAll();
        for (MonthlyRentBill monthlyRentBill : monthlyRentBills) {
            if (monthlyRentBill.getApartmentID().equals(id)) {
                if (LocalDate.now().isAfter(monthlyRentBill.getDate()) && LocalDate.now().isBefore(monthlyRentBill.getDate().plusMonths(1)) ||
                LocalDate.now().isEqual(monthlyRentBill.getDate()) ||
                        LocalDate.now().isEqual(monthlyRentBill.getDate().plusMonths(1))){
                    return true;
                }
            }
        }
        return false;
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
        LocalDate currentDate = LocalDate.now();
        for (MonthlyRentBill monthlyRentBill : monthlyRentBills) {
            if (Objects.equals(monthlyRentBill.getTenantID(), tenantId)) {
                LocalDate dueDate = monthlyRentBill.getDate().plusMonths(1).plusDays(monthlyRentBill.getRepaymentPeriod()+1);
                if (currentDate.isAfter(dueDate) && monthlyRentBill.getStatus().equals("Chưa thanh toán")) {
                    // Nếu hóa đơn quá hạn, cập nhật trạng thái thành "Quá hạn"
                    monthlyRentBill.setStatus("Quá hạn");
                    MonthlyRentBillBUS monthlyRentBillBUS = new MonthlyRentBillBUS();
                    boolean updateSuccess = monthlyRentBillBUS.update(monthlyRentBill);
                    ViolationTicket violationTickets = new ViolationTicket();
                    violationTickets.setMonthlyRentBillID(monthlyRentBill.getMonthlyRentBillID());
                    violationTickets.setViolationID("V1");
                    violationTickets.setQuantity(1);
                    violationTickets.setDate(LocalDate.now());
                    violationTickets.setPrice(200000.0);
                    violationTickets.setNote("");
                    ViolationTicketBUS violationTicketBUS = new ViolationTicketBUS();
                    boolean updateResult = violationTicketBUS.add(violationTickets);
                } else {
                    monthlyRentBillsWithTenantID.add(monthlyRentBill);
                }

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

    public MonthlyRentBill getMonthlyRentBillWithMRB(String id){
        ArrayList<MonthlyRentBill> monthlyRentBillArrayList = MonthlyRentBillBUS.getInstance().getMonthlyRentBillsWithTenantId(CustomerController.getInstance().getID());
        for (MonthlyRentBill monthlyRentBill : monthlyRentBillArrayList){
            if (Objects.equals(monthlyRentBill.getMonthlyRentBillID(), id)) {
                return monthlyRentBill;
            }
        }
        return null;
    }

    public ArrayList<MonthlyRentBill> getMonthlyRentBillWithAPTID(String id){
        ArrayList<MonthlyRentBill> monthlyRentBillArrayList = MonthlyRentBillDAO.getInstance().getMonthlyRentBillsByApartmentID(id);

        return monthlyRentBillArrayList;
    }

    public MonthlyRentBill getMonthlyRentBillWithMRB_BuildingManager(String monthlyRentBillID){
        ArrayList<MonthlyRentBill> monthlyRentBillArrayList = MonthlyRentBillBUS.getInstance().getAll();
        for (MonthlyRentBill monthlyRentBill : monthlyRentBillArrayList){
            if (Objects.equals(monthlyRentBill.getMonthlyRentBillID(), monthlyRentBillID)) {
                return monthlyRentBill;
            }
        }
        return null;
    }

    public int XuatExcelPhieuThang(String maPhieuNhap, String url){
        MonthlyRentBill monthlyRentBill = MonthlyRentBillDAO.getInstance().selectById(maPhieuNhap);
        List<ServiceTicket> ListServiceTicket = ServiceTicketDAO.getInstance().selectByMonthlyRentBillID(maPhieuNhap);
        List<ViolationTicket> ListViolationTicket = ViolationTicketDAO.getInstance().selectByMonthlyRentBillID(maPhieuNhap);
        XWPFDocument document = new XWPFDocument();
        // Thiết lập kích thước trang
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        CTPageSz pageSize = sectPr.addNewPgSz();
        pageSize.setW(BigInteger.valueOf(15840)); // Width: 12240 twips (tương đương khoảng 8.5 inches)
        pageSize.setH(BigInteger.valueOf(15840)); // Height: 15840 twips (tương đương khoảng 11 inches)

        XWPFParagraph logo = document.createParagraph();
        XWPFRun logoRun = logo.createRun();
        logoRun.setFontSize(8);
        logoRun.setText("Công ty bất động sản Star Sky");
        logoRun.addBreak();
        logoRun.setText("Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5,Thành phố Hồ Chí Minh");
        logoRun.addBreak();
        logoRun.setText("Điện thoại: (028)38354004");
        logoRun.addBreak();
        logoRun.setText("Email: StarSkyVipPro@gmail");
        logo.createRun().addCarriageReturn();
        //title
        XWPFParagraph title = document.createParagraph();

        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(16);
        titleRun.setText("HÓA ĐƠN PHIẾU THU TIỀN THÁNG");

        //Thong tin nhanh
        XWPFParagraph info = document.createParagraph();
        XWPFRun infoRun = info.createRun();
        infoRun.addBreak();
        infoRun.setText("Mã phiếu thu tiền: " + monthlyRentBill.getMonthlyRentBillID());
        infoRun.addBreak();
        infoRun.addBreak();
        LocalDate localDate = monthlyRentBill.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String ngayNhap = localDate.format(formatter);
        infoRun.setText("Ngày xuất phiếu: " + ngayNhap);
        infoRun.addBreak();

        // Bang thong tin chi tiet
        XWPFTable table = document.createTable();
        XWPFTableRow tableRow = table.getRow(0);
        tableRow.getCell(0).setText("STT");
        XWPFTableCell cell = tableRow.createCell();
        cell.setText("Ngày");
//        cell.setWidth("400");
        XWPFTableCell cell2 = tableRow.createCell();
        cell2.setText("Tên phiếu dịch vụ/phạt");
//        cell2.setWidth("500");
        tableRow.createCell().setText("Đơn vị");

        tableRow.createCell().setText("Giá/đơn vị (VND)");
        tableRow.createCell().setText("Số lượng");
        tableRow.createCell().setText("Thành tiền");
        XWPFTableCell cell3 = tableRow.createCell();
        cell3.setText("Ghi chú");
        cell3.setWidth(String.valueOf(4));

        // Đặt kiểu căn giữa cho nội dung của tất cả các ô trong bảng
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell0 : row.getTableCells()) {
                XWPFParagraph paragraph = cell0.getParagraphs().get(0); // Lấy đối tượng đoạn văn bản trong ô
                paragraph.setAlignment(ParagraphAlignment.CENTER); // Đặt căn giữa cho đoạn văn bản
            }
        }

        int dem=1;
        for (ServiceTicket serviceTicket:ListServiceTicket){
            Service service = ServiceDAO.getInstance().selectById(serviceTicket.getServiceID());
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(dem));
            dataRow.getCell(1).setText(String.valueOf(serviceTicket.getDate()));
            dataRow.getCell(2).setText(service.getName());
            dataRow.getCell(3).setText(service.getUnit());
            dataRow.getCell(4).setText(String.valueOf(service.getPricePerUnit()));
            dataRow.getCell(5).setText(String.valueOf(serviceTicket.getQuantity()));
            dataRow.getCell(6).setText(String.valueOf(serviceTicket.getTotalAmount()));
            if (serviceTicket.getNote()!=null){
                dataRow.getCell(7).setText(String.valueOf(serviceTicket.getNote()));
            }
            dem++;
        }
        for (ViolationTicket serviceTicket:ListViolationTicket){
            Violation violation = ViolationDAO.getInstance().selectById(serviceTicket.getViolationID());
            XWPFTableRow dataRow = table.createRow();
            dataRow.getCell(0).setText(String.valueOf(dem));
            dataRow.getCell(1).setText(String.valueOf(serviceTicket.getDate()));
            dataRow.getCell(2).setText(violation.getName());
            dataRow.getCell(5).setText("1");
            dataRow.getCell(6).setText(String.valueOf(serviceTicket.getPrice()));
            dataRow.getCell(7).setText(String.valueOf(serviceTicket.getNote()));
            dem++;
        }

        XWPFTableRow dataRow = table.createRow();
        dataRow.getCell(1).setText("TỔNG: ");
        dataRow.getCell(6).setText(String.valueOf(monthlyRentBill.getTotalPayment()));
        dataRow.getCell(7).setText("VND");

        // Đặt padding cho nội dung của tất cả các ô trong bảng
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell1 : row.getTableCells()) {
                // Lấy hoặc tạo một đối tượng XWPFParagraph trong ô
                XWPFParagraph paragraph;
                if (!cell1.getParagraphs().isEmpty()) {
                    paragraph = cell1.getParagraphs().get(0);
                } else {
                    paragraph = cell1.addParagraph();
                }

                // Thiết lập khoảng cách giữa nội dung văn bản và biên của ô
                paragraph.setIndentationLeft(100); // Khoảng cách bên trái
                paragraph.setIndentationRight(100); // Khoảng cách bên phải
                paragraph.setSpacingAfter(100); // Khoảng cách dưới
                paragraph.setSpacingBefore(100); // Khoảng cách trên
            }
        }


        XWPFParagraph footer = document.createParagraph();
        XWPFRun footerRun = footer.createRun();
        footerRun.setFontSize(11);
        footerRun.addBreak();
        footerRun.setText("Quản lý tòa nhà      ");
        footerRun.addBreak();
        footerRun.setText("    (ký tên)     ");
        footerRun.addBreak();
        footerRun.addBreak();
        footerRun.addBreak();
        footer.setAlignment(ParagraphAlignment.RIGHT);

        // Thêm ngày in cho footer
        XWPFParagraph dateStamp = document.createParagraph();
        //  dateStamp.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun dateStampRun = dateStamp.createRun();
        dateStampRun.setText("Ngày in: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String fileName = "PhieuThu" +monthlyRentBill.getMonthlyRentBillID() +".doc"; // Tên tệp Word
        File destFile = new File(url, fileName);
        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            document.write(fos);
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public ArrayList<MonthlyRentBill> searchMonthlyRentBills(String keyword, String buildingManagerID) {
        return MonthlyRentBillDAO.getInstance().search(keyword, buildingManagerID);
    }

    public ArrayList<MonthlyRentBill> fill(String buildingManager, LocalDate dayStart, LocalDate dayEnd){
        return MonthlyRentBillDAO.getInstance().fill(buildingManager, dayStart, dayEnd);
    }
}
