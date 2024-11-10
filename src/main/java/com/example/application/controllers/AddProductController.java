package com.example.application.controllers;

import com.operation_maneger.factory_manager.ProductFactory;
import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;
import com.operation_maneger.function_manager.FileUtils;
import com.operation_maneger.function_manager.UIUtils;
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

public class AddProductController implements Initializable {

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
        UIUtils.switchSceneAction((Node) event.getSource(), "mainView.fxml",
                "Request App", this.getClass());
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
        ObservableList<String> values = FXCollections.observableArrayList(
                Arrays.stream(types)
                .map(Enum::toString)
                .toList());

        comboBox.getItems().addAll(values);
    }

    private void addProductActionToElement(Node btnAddProduct) {

        btnAddProduct.setOnMouseClicked(mouseEvent -> {
            boolean checkBol = checkForFilledMandatoryFields();
            if (checkBol) {
                addProductToFile();
                UIUtils.alertMessage("Successfully", "You successfully add a Product",
                        "New Product is added to your table and file!", Alert.AlertType.INFORMATION);
            } else {
                UIUtils.alertMessage("Missing info", "Please fill all fields",
                        "Fill all fields with correct information", Alert.AlertType.WARNING);
            }

            resetFields();
        });
    }

    private void resetFields() {
        productName.setText("");
        productPrice.setText("");
        productDescription.setText("");
    }

    private void addProductToFile() {
        String name = productName.getText();
        double price = formatToTwoDecimalPlaces(productPrice.getText());
        DataType type = DataType.parseDataType(productType.getValue());
        String description = productDescription.getText();

        saveProduct(name, price, description, type);
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
            UIUtils.alertMessage("Incorrect price", "Choose from the drop down menu some option!",
                    "You need to select one option from the drop down menu which category will be the product.",
                    Alert.AlertType.WARNING);

            return false;
        }
    }

    private boolean checkForDoubleNum(String prodPrice) {

        try {
            Double.parseDouble(prodPrice);

            return true;
        } catch (NumberFormatException e) {
            UIUtils.alertMessage("Incorrect price", "The price is incorrect filled",
                    "The 'Product Price' field must be float(double) number who is separated with '.'!",
                    Alert.AlertType.WARNING);

            return false;
        }
    }

    public static double formatToTwoDecimalPlaces(String numberStr) {
        try {
            double number = Double.parseDouble(numberStr);

            return Double.parseDouble(String.format("%.2f", number));
        } catch (NumberFormatException e) {
            return 0.00;
        }
    }

    private static void saveProduct(String name, double price, String description, DataType type) {
        try {
            List<Object> values = Arrays.asList(name, price, description);
            String fileName = type.getFileDirectory();

            Product product = ProductFactory.createProductObject(type, values);
            FileUtils.saveAction(product, fileName);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Incorrect information for creating a class!");
        }
    }
}
