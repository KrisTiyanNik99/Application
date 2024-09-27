package com.example.application;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TableView<?> biroterapiyaTable;

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
    private ImageView btnMin;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnVedena;

    @FXML
    private Label cardMenuClose;

    @FXML
    private Label cardMenuOpen;

    @FXML
    private TableView<?> consumerTable;

    @FXML
    private Label exitBtn;

    @FXML
    private BorderPane mainPanel;

    @FXML
    private Label productCounter;

    @FXML
    private AnchorPane shoppingCartMenu;

    @FXML
    private TableView<?> vedenaTable;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeAction();
        minimizeAction();
        setSlideActionOnShoppingCartMenu();
    }

    public void closeAction() {
        btnExit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });
        exitBtn.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    public void minimizeAction() {
        btnMin.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    public void setSlideActionOnShoppingCartMenu() {
        shoppingCartMenu.setTranslateX(250);

        setSlideAction(cardMenuOpen, 0, 250, cardMenuClose);
        setSlideAction(cardMenuClose, 250, 0, cardMenuOpen);
    }

    public void setSlideAction(Label currentMenu, int transSlideX, int transX, Label otherMenu) {
        currentMenu.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(shoppingCartMenu);

            slide.setToX(transSlideX);
            slide.play();

            shoppingCartMenu.setTranslateX(transX);

            slide.setOnFinished((ActionEvent e) -> {
                currentMenu.setVisible(false);
                otherMenu.setVisible(true);
            });
        });
    }
}