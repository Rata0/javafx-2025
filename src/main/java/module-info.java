module demo.demogia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens demo.demogia to javafx.fxml;
    exports demo.demogia;
}