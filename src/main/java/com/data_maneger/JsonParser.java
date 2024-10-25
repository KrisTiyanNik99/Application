package com.data_maneger;

import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface JsonParser {
    List<Product> getProductsFromJsonFile(String fileName);
    JSONObject getJsonFileObject(String fileName);
    JSONArray findJsonArray(JSONObject jsonObject);
    List<String> getJsonParametersNames(JSONObject jsonParamObj);

    void saveInfoToJsonFile(String newData, String fileName);
}
