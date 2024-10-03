package com.example.data_models.table_models;

import com.data_maneger.JsonParser;
import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DeliveryTableView extends MainTableView implements JsonParser {
    @Override
    public void getInformationForDisplay() {
        String filePath = getFilePath();
    }

    @Override
    public List<Product> readProductsFromJson(String filePath) {
        List<Product> products = new ArrayList<>();

        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(in);
            JSONObject jsonObject = new JSONObject(tokener);

            JSONArray jsonArray = findJsonArray(jsonObject);
            for (int i = 0; i < jsonArray.length(); i++) {

            }
            //List<Object> jsonFields = parseJsonFields(jsonArray);

            //products = getProductsFromJsonFile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public JSONArray findJsonArray(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();

            if (jsonObject.get(key) instanceof JSONArray) {
                return jsonObject.getJSONArray(key);
            }
        }

        return null;
    }

    protected abstract String getFilePath();
}
