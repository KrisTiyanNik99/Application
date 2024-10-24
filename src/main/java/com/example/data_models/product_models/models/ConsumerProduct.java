package com.example.data_models.product_models.models;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;

public class ConsumerProduct extends Product {
    public ConsumerProduct(String name, double price, String description) {
        super(name, price, DataType.CONSUMER, description);
    }
}
