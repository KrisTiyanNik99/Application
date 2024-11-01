package com.operation_maneger.function_manager;

import com.example.elements_models.data_models.Product;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class TableManager {
    public static void setAddingActionEventToCheckBox(CheckBox box, Product product,
                                                      TableView<Product> requestTable, Label productCounter) {

        box.setOnAction(event -> {
            boolean productExists = checkForMatch(product, requestTable);
            if (box.isSelected()) {
                addProductToTable(product, requestTable, productExists);
            } else {
                removeProductFromTable(product, requestTable);
                requestTable.refresh();
            }

            productCounter.setText(String.valueOf(requestTable.getItems().size()));
        });
    }

    private static void addProductToTable(Product product, TableView<Product> requestTable, boolean productExists) {
        if (!productExists) {
            requestTable.getItems().add(product);
        }
    }

    private static void removeProductFromTable(Product product, TableView<Product> requestTable) {
        requestTable.getItems().removeIf(existingProduct -> existingProduct.getName().equals(product.getName()));
    }

    private static boolean checkForMatch(Product product, TableView<Product> requestTable) {

        return requestTable.getItems().stream()
                .anyMatch(p -> product.getName().equals(p.getName()));
    }
}
