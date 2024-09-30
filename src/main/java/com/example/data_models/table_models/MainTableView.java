package com.example.data_models.table_models;

import com.example.data_models.Product;
import javafx.scene.control.TableView;

public abstract class MainTableView extends TableView<Product> implements InformationDisplayable {
    @Override
    public void DisplayTableInformation(String ulr) {

    }
}
