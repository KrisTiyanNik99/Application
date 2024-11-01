package com.example.elements_models.table_models;

import com.operation_maneger.factory_manager.ProductFactory;
import com.operation_maneger.factory_manager.ReflectionUtils;
import com.example.elements_models.data_models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DeliveryTableView extends TableView<Product> {

    public void setupTableWithData(List<Product> products) {
        //Configures table columns by mapping them to product fields and populates the table with product data.

        mapColumnsToFields(products);
        setItemsInTable(products);
    }

    public void mapColumnsToFields(List<Product> products) {
        List<String> productClassFieldsNames = ReflectionUtils
                .getAllDeclaredClassFieldsNames(ProductFactory.checkTableItemsClassType(products));
        connectColumnsToClassFieldsByNames(productClassFieldsNames);
    }

    public void setItemsInTable(List<Product> products) {
        ObservableList<Product> obsProducts = FXCollections.observableArrayList(products);
        setItems(obsProducts);
    }

    private void connectColumnsToClassFieldsByNames(List<String> classFieldsNames) {
        /*
            Associates each table column with a class field based on similar names. Columns must have unique identifiers
            because the algorithm stops at the first matched field name.
         */

        for (TableColumn<Product, ?> column : getColumns()) {
            String columnName = column.getText();
            connectColumnToField(classFieldsNames, column, columnName);
        }
    }

    private void connectColumnToField(List<String> classFieldsNames, TableColumn<Product, ?> column, String columnName) {
        for (String classFieldName : classFieldsNames) {
            if (columnName.toLowerCase().contains(classFieldName.toLowerCase())) {
                column.setCellValueFactory(new PropertyValueFactory<>(classFieldName));
                column.setResizable(false);
                break;
            }
        }
    }
}
