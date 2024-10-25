package com.example.data_models.product_models.models;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;

public class BiroterapiyaProduct extends Product {
    public BiroterapiyaProduct(String name, double price, String description) {
        super(name, price, DataType.BIROTERAPIYA, description);
    }
}
