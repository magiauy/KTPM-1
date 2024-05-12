module com.example.managingbuildingjava {
        requires javafx.fxml;
        requires javafx.controls;
        requires com.dlsc.formsfx;
        requires java.sql;
        requires java.desktop;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.example.managingbuildingjava to javafx.fxml;
        opens DTO to javafx.base; // Mở gói DTO

        exports com.example.managingbuildingjava;
}