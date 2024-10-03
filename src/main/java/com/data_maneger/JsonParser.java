package com.data_maneger;

import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface JsonParser {
    List<Product> readProductsFromJson(String filePath);
    JSONArray findJsonArray(JSONObject jsonObject);
}
