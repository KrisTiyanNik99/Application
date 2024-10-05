package com.data_maneger;

import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface JsonParser {
    JSONObject getJsonFileObject(String fileName);
    JSONArray findJsonArray(JSONObject jsonObject);
    String readJsonFile(String fileName);
    List<String> getJsonFields(JSONObject jsonData);
    Object mapJsonFieldsToValues();
}
