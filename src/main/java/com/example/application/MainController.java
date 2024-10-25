package com.example.application;

import com.data_maneger.FunctionManager;
import com.example.data_models.product_models.Product;
import com.example.data_models.table_models.DeliveryTableView;
import com.example.data_models.table_models.column_models.SpinnerTableColumn;
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

import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML
    private Button btnDeleteProduct, btnSend, btnSave, btnHome, btnVedena, btnBiroterapiya, btnConsumer;

    @FXML
    private ImageView btnExit, btnMin;

    @FXML
    private DeliveryTableView mainTable;

    @FXML
    private TableView<Product> requestTable;

    @FXML
    private TableColumn<Product, ?> name;

    @FXML
    private SpinnerTableColumn quantity;

    @FXML
    private Label cardMenuClose, cardMenuOpen, exitBtn, productCounter;

    @FXML
    private BorderPane mainPanel;

    @FXML
    private AnchorPane shoppingCartMenu;

    private Map<String, List<Product>> productDataByFile = new HashMap<>();

    private static String infoFileName;

    public void switchInformationForm(ActionEvent event) {
        if (event.getSource() == btnHome) {
            mainTable.setVisible(false);
            return;
        }

        for (Map.Entry<Button, String> kvp : initMapButtonsToFileInfo().entrySet()) {
            if (event.getSource() == kvp.getKey()) {

                String fileName = kvp.getValue();
                infoFileName = fileName;

                if (productDataByFile.containsKey(fileName)) {
                    mainTable.getInformationForDisplay(productDataByFile.get(fileName));
                } else {
                    mainTable.getInformationForDisplay(fileName);
                    productDataByFile.put(fileName, mainTable.getItems());
                }

                setCheckBoxActionToTableColumn(mainTable);
                mainTable.setVisible(true);
                break;
            }
        }
    }

    public void addProductActionToElement(ActionEvent event) {
        FunctionManager.addActionToProductForm((Node) event.getSource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActions();
        setUpRequestTableColumns();
        setSlideActionOnShoppingCartMenu();
    }

    private void setupActions() {
        addCloseActionToElement(btnExit);
        addCloseActionToElement(exitBtn);

        addDeleteActionToElement(btnDeleteProduct);
        addSaveActionToElement(btnSave);
        addMinimizeActionToElement(btnMin);
    }

    private void addCloseActionToElement(Node node) {
        node.setOnMouseClicked(mouseEvent -> System.exit(0));
    }

    private void addSaveActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> FunctionManager.saveAction(mainTable));
    }

    private void addDeleteActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> {
            FunctionManager.deleteActionFromTables(mainTable, requestTable, productCounter);
            FunctionManager.deleteElementFromMap(mainTable, infoFileName, productDataByFile);
        });
    }

    private void addMinimizeActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private void setSlideActionOnShoppingCartMenu() {
        shoppingCartMenu.setTranslateX(250);

        FunctionManager.setSlideAction(cardMenuOpen, cardMenuClose, 0, 250, shoppingCartMenu);
        FunctionManager.setSlideAction(cardMenuClose, cardMenuOpen, 250, 0, shoppingCartMenu);
    }

    private void setUpRequestTableColumns() {
        // This method will stay because in future we may add some columns to our request table
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void setCheckBoxActionToTableColumn(DeliveryTableView mainTable) {
        for (Product item : mainTable.getItems()) {
            CheckBox select = item.getSelect();

            FunctionManager.setAddingActionEventToCheckBox(select, item, requestTable, productCounter);
        }
    }

    private Map<Button, String> initMapButtonsToFileInfo() {
        /*
            A method by which we connect the desired button with file that we want to extract info when it is pressed
            We add exactly this file with which we want to associate the button we press on the left menu.
         */
        Map<Button, String> mapButtonsToTables = new HashMap<>();

        mapButtonsToTables.put(btnVedena, "com/data/vedena.json");
        mapButtonsToTables.put(btnBiroterapiya, "com/data/biroterapiya.json");
        mapButtonsToTables.put(btnConsumer, "com/data/consumer.json");

        return mapButtonsToTables;
    }
}