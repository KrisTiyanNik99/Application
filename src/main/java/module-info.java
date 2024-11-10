module com.example.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires reflections;
    requires org.jetbrains.annotations;
    requires mail;
    requires com.google.api.client;
    requires com.google.api.client.auth;
    requires com.google.api.services.gmail;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.client.http.apache.v2;
    requires jdk.httpserver;
    requires java.net.http;

    opens com.example.application to javafx.fxml;
    opens com.example.elements_models.data_models to javafx.base;

    exports com.example.application;
    exports com.example.elements_models.data_models;
    exports com.example.elements_models.table_models.column_models to javafx.fxml;
    exports com.example.elements_models.table_models to javafx.fxml;
    exports com.example.application.controllers;
    opens com.example.application.controllers to javafx.fxml;
}