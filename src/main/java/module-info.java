module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires reflections;

    opens com.example.application to javafx.fxml;
    opens com.example.elements_models.data_models to javafx.base;

    exports com.example.application;
    exports com.example.elements_models.data_models;
    exports com.example.elements_models.table_models.column_models to javafx.fxml;
    exports com.example.elements_models.table_models to javafx.fxml;
}