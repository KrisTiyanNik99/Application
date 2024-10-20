package com.example.data_models.table_models.column_models;

import com.example.data_models.product_models.Product;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

public class SpinnerTableColumn extends TableColumn<Product, Integer> {

    public SpinnerTableColumn() {
        this.setCellFactory(param -> createSpinnerCell());
    }

    private TableCell<Product, Integer> createSpinnerCell() {
        return new TableCell<>() {
            private final Spinner<Integer> spinner = new Spinner<>(1, 100, 0);

            {
                spinner.setEditable(true);

                 spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                     if (getTableRow() != null && getTableRow().getItem() != null) {
                         Product product = getTableRow().getItem();
                         product.setQuantity(newValue);
                     }
                 });

                spinner.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                    if (!isNowFocused) {
                        commitEdit(spinner.getValue());
                    }
                });
            }

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    spinner.getValueFactory().setValue(item != null ? item : 0);
                    setGraphic(spinner);
                }
            }

            @Override
            public void commitEdit(Integer newValue) {
                super.commitEdit(newValue);
                if (getTableRow() != null && getTableRow().getItem() != null) {
                    getTableRow().getItem().setQuantity(newValue);
                }
            }
        };
    }
}
