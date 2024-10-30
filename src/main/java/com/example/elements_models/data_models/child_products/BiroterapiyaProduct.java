package com.example.elements_models.data_models.child_products;

import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;

public class BiroterapiyaProduct extends Product {
    public BiroterapiyaProduct(String name, double price, String description) {
        super(name, price, DataType.BIROTERAPIYA, description);
    }
}
