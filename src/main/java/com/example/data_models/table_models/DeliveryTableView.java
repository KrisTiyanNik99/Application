package com.example.data_models.table_models;

import com.data_maneger.JsonDataManager;
import com.data_maneger.ProductFactory;
import com.data_maneger.TableUtils;
import com.example.data_models.product_models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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

        List<String> classFieldsNames = ProductFactory.getAllDeclaredFieldsNames(TableUtils.checkClassType(products));
        connectColumnsToClassFieldsByNames(classFieldsNames);
        setItems(products);
    }

    protected void connectColumnsToClassFieldsByNames(List<String> classFieldsNames) {
        /*
            A method that associates table columns with class fields based on column names. Column names must be the
            same or have some common root or word in them. But the column names must be different from each other,
            because the algorithm stops further searching at the first match of the searched parameter.
         */

        for (TableColumn<Product, ?> column : getColumns()) {
            String columnName = column.getText();

            for (String classFieldName : classFieldsNames) {
                if (columnName.toLowerCase().contains(classFieldName.toLowerCase())) {
                    column.setCellValueFactory(new PropertyValueFactory<>(classFieldName));
                    column.setResizable(false);
                    break;
                }
            }
        }
    }

    protected ObservableList<Product> getProductsFromDataFile(String fileName) {
        JsonDataManager jsonData = new JsonDataManager();
        List<Product> productsList = jsonData.getProductsFromJsonFile(fileName);

        return FXCollections.observableArrayList(productsList);
    }

    /*
        This method will be overridden in descendants. The idea is for successors to determine which table, what
        information to display based on the information in the file.
     */
    protected abstract String getFileName();
}
