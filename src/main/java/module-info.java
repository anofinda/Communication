module com.example.communication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;


    opens com.example.communication to javafx.fxml,javafx.graphics;
    exports com.example.communication to javafx.fxml,javafx.graphics;

    opens com.example.communication.controller to javafx.fxml,javafx.graphics;
    exports com.example.communication.controller to javafx.fxml,javafx.graphics;


}