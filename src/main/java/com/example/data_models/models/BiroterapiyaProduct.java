package com.example.data_models.models;

import com.example.data_models.DataType;
import com.example.data_models.Product;

public class BiroterapiyaProduct extends Product {
    public BiroterapiyaProduct(String name, double price) {
        super(name, price, DataType.BIROTERAPIYA);
    }
}
