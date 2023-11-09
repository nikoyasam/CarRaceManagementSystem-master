module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.world_rally_cross_championship to javafx.fxml;
    exports com.world_rally_cross_championship;
    exports com.Models;
    opens com.Models to javafx.fxml;
    exports com.Utilities;
    opens com.Utilities to javafx.fxml;
    exports com.Controllers;
    opens com.Controllers to javafx.fxml;
}