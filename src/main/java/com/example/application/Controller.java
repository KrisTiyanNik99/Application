package com.example.application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnBiroterapiya;

    @FXML
    private Button btnConsumer;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private ImageView btnExit;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnVedena;

    @FXML
    private Label cardMenuClose;

    @FXML
    private Label cardMenuOpen;

    @FXML
    private Label exitBtn;

    @FXML
    private Label productCounter;

    @FXML
    private AnchorPane shoppingCartMenu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnExit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });
        exitBtn.setOnMouseClicked(event -> {
            System.exit(0);
        });

        shoppingCartMenu.setTranslateX(250);

        cardMenuOpen.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(shoppingCartMenu);

            slide.setToX(0);
            slide.play();

            shoppingCartMenu.setTranslateX(250);

            slide.setOnFinished((ActionEvent e) -> {
                cardMenuOpen.setVisible(false);
                cardMenuClose.setVisible(true);
            });
        });

        cardMenuClose.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(shoppingCartMenu);

            slide.setToX(250);
            slide.play();

            shoppingCartMenu.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                cardMenuClose.setVisible(false);
                cardMenuOpen.setVisible(true);
            });
        });
    }
}