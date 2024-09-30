package com.example.data_models.models;

import com.example.data_models.DataType;
import com.example.data_models.Product;

public class ConsumerProduct extends Product {
    public ConsumerProduct(String name, double price, int quantity) {
        super(name, price, DataType.CONSUMMER);
        setQuantity(quantity);
    }
}
