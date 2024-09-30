package com.example.application;

import com.example.data_models.Product;
import com.example.data_models.table_models.MainTableView;
import com.example.data_models.table_models.models.BiroterapiyaTableView;
import com.example.data_models.table_models.models.ConsumerTableView;
import com.example.data_models.table_models.models.VedenaTableView;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
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
    private VedenaTableView vedenaTable;

    @FXML
    private BiroterapiyaTableView biroterapiyaTable;

    @FXML
    private ConsumerTableView consumerTable;

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
            for (Map.Entry<Button, MainTableView> btv : initMapButtonsToTables().entrySet()) {
                btv.getValue().setVisible(false);
            }

            return;
        }

        for (Map.Entry<Button, MainTableView> kvp : initMapButtonsToTables().entrySet()) {
            if (event.getSource() == kvp.getKey()) {

                System.out.println(kvp.getKey().getText());

                MainTableView test = kvp.getValue();
                test.setVisible(true);
                testMethod(test);
                // showTableInfo();
            }else {
                kvp.getValue().setVisible(false);
            }
        }
    }

    // TESTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
    private void testMethod(MainTableView test) {
        ObservableList<TableColumn<Product, ?>> testList = test.getColumns();
        System.out.println(testList.size());
        for (TableColumn<Product, ?> productTableColumn : testList) {
            System.out.println(productTableColumn.getText());
        }
    }

    private void initializeTables() {
        //linkTableColumnsToProduct();
    }
    private void setupActions() {
        addCloseActionToNode(btnExit);
        addCloseActionToNode(exitBtn);
        addMinimizeActionToElement(btnMin);
    }

    // NOT FINISHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
    private void linkTableColumnsToProduct() {
        //for () {
            // Да измисля програмка която да срявнява 2 стринга и да прави нещо за да може да сравни имената на колоната с тези на продукта
        //}
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
    private Map<Button, MainTableView> initMapButtonsToTables() {
        // We add exactly this table with which we want to associate the button we press
        Map<Button, MainTableView> mapButtonsToTables = new HashMap<>();

        mapButtonsToTables.put(btnVedena, vedenaTable);
        mapButtonsToTables.put(btnBiroterapiya, biroterapiyaTable);
        mapButtonsToTables.put(btnConsumer, consumerTable);

        return mapButtonsToTables;
    }
}