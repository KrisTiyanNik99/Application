package com.example.application;

import com.example.data_models.product_models.Product;
import com.example.data_models.table_models.DeliveryTableView;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private Button btnAddProduct, btnDeleteProduct, btnSend, btnHome, btnVedena, btnBiroterapiya, btnConsumer;

    @FXML
    private ImageView btnExit, btnMin;

    @FXML
    private DeliveryTableView mainTable;

    @FXML
    private TableView<Product> requestTable;

    @FXML
    private TableColumn<Product, ?> name, price;

    @FXML
    private TableColumn<Product, Double> quantity;

    @FXML
    private Label cardMenuClose, cardMenuOpen, exitBtn, productCounter;

    @FXML
    private BorderPane mainPanel;

    @FXML
    private AnchorPane shoppingCartMenu;

    public void switchInformationForm(ActionEvent event) {
        if (event.getSource() == btnHome) {
            mainTable.setVisible(false);
            return;
        }

        for (Map.Entry<Button, String> kvp : initMapButtonsToFileInfo().entrySet()) {
            if (event.getSource() == kvp.getKey()) {

                String fileName = kvp.getValue();
                mainTable.getInformationForDisplay(fileName);
                setCheckBoxActions(mainTable);
                mainTable.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActions();
        setUpRequestColumns();
        setSlideActionOnShoppingCartMenu();
    }

    private void setupActions() {
        addCloseActionToElement(btnExit);
        addCloseActionToElement(exitBtn);
        addMinimizeActionToElement(btnMin);
    }

    private void setUpRequestColumns() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void addCloseActionToElement(Node node) {
        node.setOnMouseClicked(mouseEvent -> System.exit(0));
    }

    private void addMinimizeActionToElement(Node node) {
        node.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private void setSlideActionOnShoppingCartMenu() {
        shoppingCartMenu.setTranslateX(250);

        setSlideAction(0, 250, cardMenuOpen, cardMenuClose);
        setSlideAction(250, 0, cardMenuClose, cardMenuOpen);
    }

    private void setSlideAction(int slideX, int shoppingCartMenuX, Label currentMenu, Label otherMenu) {
        currentMenu.setOnMouseClicked(click -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(shoppingCartMenu);

            slide.setToX(slideX);
            slide.play();

            shoppingCartMenu.setTranslateX(shoppingCartMenuX);

            slide.setOnFinished((ActionEvent e) -> {
                currentMenu.setVisible(false);
                otherMenu.setVisible(true);
            });
        });
    }

    private void setCheckBoxActions(DeliveryTableView table) {
        for (Product product : table.getItems()) {
            CheckBox selected = product.getSelect();
            CheckBox imperative = product.getImperative();

            setAddActionEvents(selected, product);
            setAddActionEvents(imperative, product);
        }
    }

    private void setAddActionEvents(CheckBox box, Product product) {
        box.setOnAction(event -> {
            int productNum = Integer.parseInt(productCounter.getText());

            if (box.isSelected()) {
                boolean productExists = requestTable.getItems().stream()
                        .anyMatch(existingProduct -> existingProduct.getName().equals(product.getName()));

                if (!productExists) {
                    productNum++;
                    requestTable.getItems().add(product);
                }
            } else {
                productNum--;
                requestTable.getItems().removeIf(existingProduct -> existingProduct.getName().equals(product.getName()));
            }

            productCounter.setText(String.valueOf(productNum));
        });
    }

    // A method by which we connect the desired button with file that we want to extract info when it is pressed
    private Map<Button, String> initMapButtonsToFileInfo() {
        // We add exactly this file with which we want to associate the button we press on the left menu
        Map<Button, String> mapButtonsToTables = new HashMap<>();

        mapButtonsToTables.put(btnVedena, "vedena.json");
        mapButtonsToTables.put(btnBiroterapiya, "biroterapiya.json");
        mapButtonsToTables.put(btnConsumer, "consumer.json");

        return mapButtonsToTables;
    }
}