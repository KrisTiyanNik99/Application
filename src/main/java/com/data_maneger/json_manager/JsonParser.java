package com.data_maneger.json_manager;

import com.example.data_models.product_models.Product;

import java.util.List;

public interface JsonParser {
    List<Product> getProductsFromJsonFile(String fileName);
    void saveInfoToJsonFile(String newData, String fileName);
    void saveInfoToJsonFile(List<Product> products, String fileName);
}