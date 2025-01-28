module demo.demogia {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    opens demo.demogia to javafx.fxml;
    opens demo.demogia.model to javafx.base;

    exports demo.demogia;
    exports demo.demogia.model;
}