package com.example.data_models.table_models;

import com.data_maneger.JsonDataManager;
import com.example.data_models.product_models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

public abstract class DeliveryTableView extends MainTableView {
    @Override
    public void getInformationForDisplay() {
        /*
            The methods in this class create objects based on the information from the file that is submitted.
            This is done in order to prevent errors that can occur when creating objects from the descendants
            of the Product class.
         */
        String fileName = getFileName();
        ObservableList<Product> products = getProductsFromDataFile(fileName);

    }

    public List<String> getColumnNames() {
        List<String> columnNames = new ArrayList<>();
        ObservableList<TableColumn<Product, ?>> columns = getColumns();
        columns.forEach(e -> columnNames.add(e.getText()));

        return columnNames;
    }

    protected ObservableList<Product> getProductsFromDataFile(String fileName) {
        JsonDataManager jsonData = new JsonDataManager();
        List<Product> productsList = jsonData.getProductsFromJsonFile(fileName);

        return FXCollections.observableArrayList(productsList);
    }

    // Метод който взима имената на параметрите на класа
    // Метод който взима имената на всички колони и има слага sellValueFactory според имената на параметрите в класа

    /*
        This method will be overridden in descendants. The idea is for successors to determine which table, what
        information to display based on the information in the file.
     */
    protected abstract String getFileName();
}
