package com.example.application;

import com.operation_maneger.function_manager.FileUtils;
import com.operation_maneger.function_manager.TableManager;
import com.operation_maneger.function_manager.UIUtils;
import com.operation_maneger.json_manager.JsonDataManager;
import com.operation_maneger.json_manager.JsonParser;
import com.example.elements_models.data_models.Product;
import com.example.elements_models.table_models.DeliveryTableView;
import com.example.elements_models.table_models.column_models.SpinnerTableColumn;
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
    private Label cardMenuClose, cardMenuOpen, exitBtn, productCounter;
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
    private BorderPane mainPanel;
    @FXML
    private AnchorPane shoppingCartMenu;

    private final Map<String, List<Product>> productDataByFile = new HashMap<>();
    private static String infoFileName;

    public void switchInformationForm(ActionEvent event) {
        if (event.getSource() == btnHome) {
            startHomePage();

            return;
        }

        for (Map.Entry<Button, String> kvp : initMapButtonsToFileInfo().entrySet()) {
            if (event.getSource() == kvp.getKey()) {

                String fileName = kvp.getValue();
                infoFileName = fileName;

                JsonParser jsonData = new JsonDataManager();
                List<Product> productsList = jsonData.getProductsFromJsonFile(fileName);

                productDataByFile.putIfAbsent(fileName, productsList);
                mainTable.setupTableWithData(productDataByFile.get(fileName));

                setActionToCheckBox(mainTable);
                mainTable.setVisible(true);
                break;
            }
        }
    }

    public void switchSceneActionToElement(ActionEvent event) {
        UIUtils.switchSceneAction((Node) event.getSource(), "addProductView.fxml",
                "Add Product", this.getClass());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupActions();
        setUpRequestTableColumns();
        setSlideActionOnShoppingCartMenu();
    }

    private void setupActions() {
        startHomePage();

        addCloseActionToElement(btnExit);
        addCloseActionToElement(exitBtn);

        addDeleteActionToElement(btnDeleteProduct);
        addSaveActionToElement(btnSave);
        addMinimizeActionToElement(btnMin);
    }

    private void startHomePage() {
        /*
            This is in method because in future we don't know what kind of changes we want to do and this is the easiest
            way to make dynamic changes in the whole code.
        */

        mainTable.setVisible(false);
    }

    private void addCloseActionToElement(Node node) {
        node.setOnMouseClicked(mouseEvent -> System.exit(0));
    }

    private void addSaveActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> {
            List<Product> products = mainTable.getItems();

            FileUtils.saveAction(products, infoFileName);
            UIUtils.alertMessage("Save", "Table save.",
                    "You successfully save your current state of the table!", Alert.AlertType.INFORMATION);

            mainTable.refresh();
        });
    }

    private void addDeleteActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> {
            Product selectedProduct = mainTable.getSelectionModel().getSelectedItem();
            deleteProduct(selectedProduct);

            productCounter.setText(String.valueOf(requestTable.getItems().size()));
        });
    }

    private void deleteProduct(Product selectedProduct) {
        FileUtils.deleteProductFromTable(selectedProduct, mainTable);
        FileUtils.deleteProductFromTable(selectedProduct, requestTable);
        FileUtils.deleteElementFromMap(selectedProduct, infoFileName, productDataByFile);
    }

    private void addMinimizeActionToElement(Node element) {
        element.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private void setSlideActionOnShoppingCartMenu() {
        shoppingCartMenu.setTranslateX(250);

        UIUtils.setSlideAction(cardMenuOpen, cardMenuClose, 0, 250, shoppingCartMenu);
        UIUtils.setSlideAction(cardMenuClose, cardMenuOpen, 250, 0, shoppingCartMenu);
    }

    private void setUpRequestTableColumns() {
        // This method will stay because in future we may add some columns to our request table
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void setActionToCheckBox(DeliveryTableView mainTable) {
        for (Product item : mainTable.getItems()) {
            CheckBox select = item.getSelect();

            TableManager.setAddingActionEventToCheckBox(select, item, requestTable, productCounter);
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