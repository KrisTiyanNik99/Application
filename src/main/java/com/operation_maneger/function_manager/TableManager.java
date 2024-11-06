package com.operation_maneger.function_manager;

import com.example.elements_models.data_models.Product;
import com.operation_maneger.gmail_manager.GmailUtils;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public static void sendRequestByMail(TableView<Product> requestTable) throws Exception {
        StringBuilder sb = getFormatedProductInfo(requestTable);
        GmailUtils.sendMail("Bar Request", sb.toString());
    }

    @NotNull
    private static StringBuilder getFormatedProductInfo(TableView<Product> requestTable) {
        StringBuilder sb = new StringBuilder();
        BigDecimal priceSum = new BigDecimal("0.00");
        for (Product item : requestTable.getItems()) {
            sb.append(item).append(System.lineSeparator());
            priceSum = priceSum.add(BigDecimal.valueOf(item.getPrice()));
        }

        sb.append(System.lineSeparator());
        sb.append("#============================================# Обща сума: ");
        sb.append(priceSum.setScale(2, RoundingMode.HALF_DOWN)).append(" #");

        return sb;
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
