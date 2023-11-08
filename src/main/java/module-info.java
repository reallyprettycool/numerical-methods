module com.example.project1_6318248 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.project1_6318248 to javafx.fxml;
    exports com.example.project1_6318248;
}