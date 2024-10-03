package com.example.data_models.product_models.models;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;

public class VedenaProduct extends Product {
    public VedenaProduct(String name, double price) {
        super(name, price, DataType.VEDENA);
    }
}
