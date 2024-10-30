package com.example.elements_models.data_models.child_products;

import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;

public class VedenaProduct extends Product {
    public VedenaProduct(String name, double price, String description) {
        super(name, price, DataType.VEDENA, description);
    }
}
