package com.data_source;

import com.example.data_models.Product;
import com.example.data_models.models.BiroterapiyaProduct;
import com.example.data_models.models.ConsumerProduct;
import com.example.data_models.models.VedenaProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TestSource {

    public ObservableList<Product> getVedenaTestData() {
        ObservableList<Product> data = FXCollections.observableArrayList(
                new VedenaProduct("Test1", 34.50),
                new VedenaProduct("Test1", 34.50),
                new VedenaProduct("Test1", 34.50),
                new VedenaProduct("Test1", 34.50),
                new VedenaProduct("Test1", 34.50),
                new VedenaProduct("Test1", 34.50)
        );

        return data;
    }

    public ObservableList<Product> getBiroterapiyaTestData() {
        ObservableList<Product> data = FXCollections.observableArrayList(
                new BiroterapiyaProduct("Test5", 99.50),
                new BiroterapiyaProduct("Test5", 99.50),
                new BiroterapiyaProduct("Test5", 99.50),
                new BiroterapiyaProduct("Test5", 99.50),
                new BiroterapiyaProduct("Test5", 99.50),
                new BiroterapiyaProduct("Test5", 99.50)
        );

        return data;
    }

    public ObservableList<Product> getConsumerTestData() {
        ObservableList<Product> data = FXCollections.observableArrayList(
                new ConsumerProduct("Test1", 1.99, 5),
                new ConsumerProduct("Test1", 1.99, 5),
                new ConsumerProduct("Test1", 1.99, 5),
                new ConsumerProduct("Test1", 1.99, 5),
                new ConsumerProduct("Test1", 1.99, 5),
                new ConsumerProduct("Test1", 1.99, 5)
        );

        return data;
    }
}
