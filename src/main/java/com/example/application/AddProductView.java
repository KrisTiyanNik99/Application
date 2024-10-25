package com.example.application;

import com.data_maneger.FunctionManager;
import com.data_maneger.ProductFactory;
import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static com.data_maneger.ProductFactory.getClassByType;

public class AddProductView implements Initializable {

    @FXML
    private Button btnAddProduct;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

    @FXML
    private ComboBox<String> productType;

    @FXML
    private TextField productDescription;

    public void returnToTable(ActionEvent event) {
        FunctionManager.addActionToTableForm((Node) event.getSource());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpActions();
    }

    private void setUpActions() {
        addValuesToComboBox(productType);
        addProductActionToElement(btnAddProduct);
    }

    private void addValuesToComboBox(ComboBox<String> comboBox) {

        DataType[] types = DataType.values();
        List<String> stringType = Arrays.stream(types).map(Enum::toString).toList();

        ObservableList<String> values = FXCollections.observableArrayList(stringType);
        comboBox.getItems().addAll(values);
    }

    private void addProductActionToElement(Node btnAddProduct) {

        btnAddProduct.setOnMouseClicked(mouseEvent -> {
            boolean checkBol = checkForFilledMandatoryFields();

            if (checkBol) {
                addProductToFile();
                alertMessage("Successfully", "You successfully add a Product",
                        "New Product is added to your table and file!", Alert.AlertType.INFORMATION);
            } else {
                alertMessage("Missing info", "Please fill all fields",
                        "Fill all fields with correct information", Alert.AlertType.INFORMATION);
            }

            productName.setText("");
            productPrice.setText("");
            productDescription.setText("");
        });
    }

    private void addProductToFile() {
        String name = productName.getText();
        double price = FunctionManager.formatToTwoDecimalPlaces(productPrice.getText());
        DataType type = DataType.parseDataType(productType.getValue());
        String description = productDescription.getText();

        try {
            List<Object> values = Arrays.asList(name, price, description);
            String fileName = type.getFileDirectory();

            Product product = ProductFactory.createProductObject(getClassByType(type), values);
            FunctionManager.saveAction(product, fileName);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Incorrect information for creating a class!");
        }
    }

    private boolean checkForFilledMandatoryFields() {
        String prodName = productName.getText();
        String prodPrice = productPrice.getText();
        String dataType = productType.getValue();

        return (!prodName.isBlank() && !prodPrice.isBlank() && checkForDoubleNum(prodPrice) && checkForEnum(dataType));
    }

    private boolean checkForEnum(String dataType) {
        try {
            DataType.parseDataType(dataType);
            return true;
        } catch (NullPointerException e) {
            alertMessage("Incorrect price", "Choose from the drop down menu some option!",
                    "You need to select one oprion from the drop down menu which category will be the product.",
                    Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean checkForDoubleNum(String prodPrice) {

        try {
            Double.parseDouble(prodPrice);
            return true;
        } catch (NumberFormatException e) {
            alertMessage("Incorrect price", "The price is incorrect filled",
                    "The 'Product Price' field must be float(double) number who is separated with '.'!",
                    Alert.AlertType.WARNING);
            return false;
        }
    }

    private void alertMessage(String title, String headerText, String text, Alert.AlertType type) {
        Alert invalid = new Alert(type);

        invalid.setTitle(title);
        invalid.setHeaderText(headerText);
        invalid.setContentText(text);
        invalid.showAndWait();
    }
}
