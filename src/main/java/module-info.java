module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires reflections;

    opens com.example.application to javafx.fxml;
    exports com.example.application;
    exports com.example.data_models.table_models.models to javafx.fxml;
}