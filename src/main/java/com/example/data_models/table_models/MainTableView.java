package com.example.data_models.table_models;

import com.example.data_models.product_models.Product;
import javafx.scene.control.TableView;

public abstract class MainTableView extends TableView<Product> {
    public abstract void getInformationForDisplay();
}
