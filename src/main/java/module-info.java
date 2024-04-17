module com.example.managingbuildingjava {
        requires javafx.fxml;
        requires java.logging;
        requires javafx.controls;
        requires com.dlsc.formsfx;
        requires java.sql;
        requires java.desktop;

        opens com.example.managingbuildingjava to javafx.fxml;
        exports com.example.managingbuildingjava;
}