package com.data_maneger.json_manager;

import com.example.elements_models.data_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonDataManager implements JsonParser {
    private static final String RESOURCE_DIR = "E:\\Request App\\Application\\src\\main\\java\\";
    private static final String JSON_ARRAY_NAME = "products";

    @Override
    public List<Product> getProductsFromJsonFile(String fileName) {
        String absolutePath = RESOURCE_DIR + fileName;

        JSONArray jsonArray = JsonReader.findJsonArray(JsonReader.getJsonFileObject(absolutePath));
        List<Product> products;

        try {
            products = JsonMapper.mapJsonArrayToList(jsonArray);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public void saveInfoToJsonFile(String newData, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;

        JSONObject jsonFileObj = JsonReader.getJsonFileObject(resourceDir);
        JSONArray arr = JsonReader.findJsonArray(jsonFileObj);

        JSONObject jsonObj = new JSONObject(newData);

        arr.put(jsonObj);
        writeJsonFile(jsonFileObj, resourceDir);
    }

    @Override
    public void saveInfoToJsonFile(List<Product> products, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;

        JSONObject root = new JSONObject();
        JSONArray productsArray = new JSONArray();

        fillJsonArrayWithData(products, productsArray);

        root.put(JSON_ARRAY_NAME, productsArray);
        writeJsonFile(root, resourceDir);
    }

    private void fillJsonArrayWithData(List<Product> products, JSONArray productsArray) {
        for (Product p : products) {
            JSONObject productJson = new JSONObject(p.toJsonFileFormat());
            productsArray.put(productJson);
        }
    }

    private void writeJsonFile(JSONObject root, String resourceDir) {
        Path path = Paths.get(resourceDir);
        try {
            Files.write(path, root.toString(5).getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cannot write in the file!");
        }
    }
}
