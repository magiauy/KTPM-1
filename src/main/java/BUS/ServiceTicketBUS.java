package BUS;

import DAO.ServiceDAO;
import DAO.ServiceTicketDAO;
import DTO.MonthlyRentBill;
import DTO.Service;
import DTO.ServiceTicket;
import DTO.ServiceUsuage;
import com.example.managingbuildingjava.CustomerController;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class ServiceTicketBUS {
    private ArrayList<ServiceTicket> serviceTickets = new ArrayList<>();

    private static ServiceTicketBUS instance;

    public static ServiceTicketBUS getInstance() {
        if (instance == null) {
            instance = new ServiceTicketBUS();
        }
        return instance;
    }

    public ServiceTicketBUS() {
        this.serviceTickets = ServiceTicketDAO.getInstance().selectAll();
    }

    public ArrayList<ServiceTicket> getAll() {
        ServiceTicketDAO serviceTicketDAO = ServiceTicketDAO.getInstance();
        return serviceTicketDAO.selectAll();
    }

    public boolean add(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().insert(serviceTicket) != 0;
        if (check) {
            this.serviceTickets.add(serviceTicket);
        }
        return check;
    }

    public boolean delete(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().delete(serviceTicket.getServiceTicketID()) != 0;
        if (check) {
            this.serviceTickets.remove(serviceTicket);
        }
        return check;
    }

    public boolean update(ServiceTicket serviceTicket) {
        boolean check = ServiceTicketDAO.getInstance().update(serviceTicket) != 0;
        if (check) {
            int index = getIndexByServiceTicketID(serviceTicket.getServiceTicketID());
            if (index != -1) {
                this.serviceTickets.set(index, serviceTicket);
            }
        }
        return check;
    }

    public int getIndexByServiceTicketID(String serviceTicketID) {
        for (int i = 0; i < this.serviceTickets.size(); i++) {
            if (Objects.equals(this.serviceTickets.get(i).getServiceTicketID(), serviceTicketID)) {
                return i;
            }
        }
        return -1; // Not found
    }
    public int countRows(){
        return ServiceTicketDAO.getInstance().countRows() + 1;
    }
    public ArrayList<String> getCurrentMonthMonthlyRentBillIDsByTenantID(String tenantID) {
        return ServiceTicketDAO.getInstance().getCurrentMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID());
    }
    public ArrayList<String> getOldMonthMonthlyRentBillIDsByTenantID(String tenantID) {
        return ServiceTicketDAO.getInstance().getOldMonthMonthlyRentBillIDsByTenantID(CustomerController.getInstance().getID());
    }


    public ArrayList<ServiceTicket> search(LocalDate day1, LocalDate day2, String type) {
        ArrayList<ServiceTicket> resul = new ArrayList<>();
        if (day1 == null || day2 == null) {
            return resul;
        }

        switch (type) {
            case "Tìm Theo Ngày" -> {
                for (ServiceTicket i : serviceTickets) {
                    LocalDate res = i.getDate();
                    if (!res.isBefore(day1) && !res.isAfter(day2)) {
                        resul.add(i);
                    }
                }
            }

        }

        return resul;

    }

    public ArrayList<ServiceTicket> searchID(String Text, String type) {
        ArrayList<ServiceTicket> resul = new ArrayList<>();
        if (Text == null) {
            return resul;
        }
        Text = Text.toLowerCase();
        switch (type) {
            case "Tìm Theo Mã" -> {
                for (ServiceTicket i : serviceTickets) {
                    if (i.getServiceTicketID().toLowerCase().contains(Text) || i.getServiceID().toLowerCase()
                            .contains(Text) || i.getMonthlyRentBillID().toLowerCase().contains(Text)) {
                        resul.add(i);
                    }
                }
            }

        }

        return resul;

    }

    public void exportDPF(String url, String id) {
        ServiceTicket ticket = ServiceTicketDAO.getInstance().selectById(id);
        Service service = ServiceDAO.getInstance().selectById(ticket.getServiceID());
        String dest = url + "\\PhieuDichVu"+ticket.getServiceTicketID()+".pdf";
        try {
            // Tạo PdfWriter
            PdfWriter writer = new PdfWriter(dest);

            // Tạo PdfDocument
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Tạo Document
            Document document = new Document(pdfDoc);

            // Sử dụng font hỗ trợ tiếng Việt
            String fontPath = "src/DejaVuSans.ttf";  // Cập nhật đường dẫn tới font của bạn
            PdfFont font = PdfFontFactory.createFont(fontPath, PdfEncodings.IDENTITY_H, true);

            String logoPath = "src/main/resources/Picture/logo.jpg";  // Đường dẫn tới logo của bạn
            ImageData imageData = ImageDataFactory.create(logoPath);
            com.itextpdf.layout.element.Image logo = new Image(imageData).scaleToFit(100, 100).setFixedPosition(20, 750);  // Định vị và kích thước logo
            document.add(logo);

            Paragraph companyInfo = new Paragraph("Công ty bất động sản Star Sky\n"
                    + "Địa chỉ: 273 An Dương Vương, Phường 3, Quận 5, Thành phố Hồ Chí Minh\n"
                    + "Điện thoại: (028) 38354004\n"
                    + "Email: StarSkyVipPro@gmail")
                    .setFont(font)
                    .setFontSize(8)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10);  // Add space after company info
            document.add(companyInfo);

            // Thêm tiêu đề
            Paragraph title = new Paragraph("Phiếu Dịch Vụ")
                    .setFont(font)
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);  // Add space after title
            document.add(title);

            document.add(new Paragraph("Dịch vụ: " + service.getName()).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Mã phiếu dịch vụ: " + ticket.getServiceTicketID()).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Mã hóa đơn tiền thuê hàng tháng: " + ticket.getMonthlyRentBillID()).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Mã dịch vụ: " + ticket.getServiceID()).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Số lượng: " + (ticket.getQuantity() != null ? ticket.getQuantity().toString() : "")).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Tổng số tiền: " + (ticket.getTotalAmount() != null ? ticket.getTotalAmount().toString() : "")).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Ngày: " + (ticket.getDate() != null ? ticket.getDate().toString() : "")).setFont(font));
            document.add(new Paragraph("---------------------------------------------------------------------------------").setFont(font));
            document.add(new Paragraph("Ghi chú: " + ticket.getNote()).setFont(font));

            // Đóng tài liệu
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
