package com.operation_maneger.json_manager;

import com.example.elements_models.data_models.Product;
import org.jetbrains.annotations.NotNull;
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

        return fillListWithProducts(jsonArray);
    }

    @Override
    public void saveInfoToJsonFile(String newData, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;
        writeNewDataInJsonFile(newData, resourceDir);
    }

    @Override
    public void saveInfoToJsonFile(List<Product> products, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;

        JSONArray productsArray = fillJsonArrayWithData(products);
        writeJsonArrayToFile(productsArray, resourceDir);
    }

    @NotNull
    private static List<Product> fillListWithProducts(JSONArray jsonArray) {
        List<Product> products;
        try {
            products = JsonMapper.mapJsonArrayToList(jsonArray);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private void writeJsonArrayToFile(JSONArray productsArray, String resourceDir) {
        JSONObject root = new JSONObject();
        root.put(JSON_ARRAY_NAME, productsArray);
        writeJsonFile(root, resourceDir);
    }

    private JSONArray fillJsonArrayWithData(List<Product> products) {
        JSONArray productsArray = new JSONArray();
        for (Product p : products) {
            productsArray.put(new JSONObject(p.toJsonFileFormat()));
        }

        return productsArray;
    }

    private void writeNewDataInJsonFile(String newData, String resourceDir) {
        JSONObject jsonFileObj = JsonReader.getJsonFileObject(resourceDir);
        putNewDataInJsonObj(newData, jsonFileObj);
        writeJsonFile(jsonFileObj, resourceDir);
    }

    private void putNewDataInJsonObj(String newData, JSONObject jsonFileObj) {
        JSONArray arr = JsonReader.findJsonArray(jsonFileObj);
        arr.put(new JSONObject(newData));
    }

    private void writeJsonFile(JSONObject root, String resourceDir) {
        try {
            Path path = Paths.get(resourceDir);
            Files.write(path, root.toString(5).getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Cannot write in the file!");
        }
    }
}
