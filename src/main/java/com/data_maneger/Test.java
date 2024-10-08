package com.data_maneger;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        JsonDataManager json = new JsonDataManager();

        List<Product> products = json.getProductsFromJsonFile("vedena.json");
        products.forEach(e -> System.out.println(e.toString()));
    }
}
