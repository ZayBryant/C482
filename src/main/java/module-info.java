module com.example.software1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.software1 to javafx.fxml;
    exports com.example.software1;
    exports com.example.software1.controller;
    opens com.example.software1.controller to javafx.fxml;

    exports com.example.software1.model;
    opens com.example.software1.model to javafx.fxml;
}