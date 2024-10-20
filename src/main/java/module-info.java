module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires reflections;

    opens com.example.application to javafx.fxml;
    opens com.example.data_models.product_models to javafx.base;

    exports com.example.application;
    exports com.example.data_models.product_models;
    exports com.example.data_models.table_models.column_models to javafx.fxml;
    exports com.example.data_models.table_models to javafx.fxml;
}