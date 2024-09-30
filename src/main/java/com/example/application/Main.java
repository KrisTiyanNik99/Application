package com.example.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 580);
        stage.setScene(scene);
        setOptions(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void setOptions(Stage stage) {
        stage.setTitle("Request App");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
    }
}