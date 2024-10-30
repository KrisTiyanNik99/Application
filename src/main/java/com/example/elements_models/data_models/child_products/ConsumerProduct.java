package com.example.elements_models.data_models.child_products;

import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;

public class ConsumerProduct extends Product {
    public ConsumerProduct(String name, double price, String description) {
        super(name, price, DataType.CONSUMER, description);
    }
}
