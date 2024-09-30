package com.example.application;

import com.example.data_models.Product;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private Button btnAddProduct, btnDeleteProduct, btnSend, btnHome, btnVedena, btnBiroterapiya, btnConsumer;

    @FXML
    private ImageView btnExit, btnMin;

    @FXML
    private TableView<Product> vedenaTable, biroterapiyaTable, consumerTable;

    @FXML
    private TableColumn<Product, ?> vedenaProductName, vedenaPrice, vedenaSelected, vedenaImperative, vedenaType;

    @FXML
    private TableColumn<Product, ?> biroterapiyaName, biroterapiyaPrice, biroterapiyaSelected, biroterapiyaImperative, biroterapiyaType;

    @FXML
    private TableColumn<Product, ?> consumerName, consumerPrice, consumerSelected, consumerImperative, consumerType;

    @FXML
    private Label cardMenuClose, cardMenuOpen, exitBtn, productCounter;

    @FXML
    private BorderPane mainPanel;

    @FXML
    private AnchorPane shoppingCartMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActions();
        initializeTables();
        setSlideActionOnShoppingCartMenu();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == btnHome) {
            for (Map.Entry<Button, TableView<Product>> btv : initMapButtonsToTables().entrySet()) {
                btv.getValue().setVisible(false);
            }

            return;
        }

        for (Map.Entry<Button, TableView<Product>> kvp : initMapButtonsToTables().entrySet()) {
            if (event.getSource() == kvp.getKey()) {
                kvp.getValue().setVisible(true);
                // showTableInfo();
            }else {
                kvp.getValue().setVisible(false);
            }
        }
    }

    private void initializeTables() {
        linkTableColumnsToProduct();
    }
    private void setupActions() {
        addCloseActionToNode(btnExit);
        addCloseActionToNode(exitBtn);
        addMinimizeActionToElement(btnMin);
    }

    private void linkTableColumnsToProduct() {
        for (Map.Entry<TableView<Product>, List<TableColumn<Product, ?>>> entry  : initTableColumnMapping().entrySet()) {
            // Да измисля програмка която да срявнява 2 стринга и да прави нещо за да може да сравни имената на колоната с тези на продукта
        }
    }

    private void addCloseActionToNode(Node node) {
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

    // A method by which we connect the desired button with table that we want to appear when it is pressed
    private Map<Button, TableView<Product>> initMapButtonsToTables() {

        Map<Button, TableView<Product>> mapButtonsToTables = new HashMap<>();

        mapButtonsToTables.put(btnVedena, vedenaTable);
        mapButtonsToTables.put(btnBiroterapiya, biroterapiyaTable);
        mapButtonsToTables.put(btnConsumer, consumerTable);

        return mapButtonsToTables;
    }

    // We need this method to be able to set which table to which columns to be linked
    private Map<TableView<Product>, List<TableColumn<Product, ?>>> initTableColumnMapping() {
        Map<TableView<Product>, List<TableColumn<Product, ?>>> tableColumnMap = new HashMap<>();

        // We add the columns in the same order as the fields in the Product class are arranged
        tableColumnMap.put(vedenaTable, Arrays.asList(vedenaProductName, vedenaPrice, vedenaSelected, vedenaImperative, vedenaType));
        tableColumnMap.put(biroterapiyaTable, Arrays.asList(biroterapiyaName, biroterapiyaPrice, biroterapiyaSelected, biroterapiyaImperative, biroterapiyaType));
        tableColumnMap.put(consumerTable, Arrays.asList(consumerName, consumerPrice, consumerSelected, consumerImperative, consumerType));

        return tableColumnMap;
    }
}