module com.javafinal.kheladi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;
    requires nepse;




    opens com.javafinal.kheladi to javafx.fxml;
    exports com.javafinal.kheladi;
}