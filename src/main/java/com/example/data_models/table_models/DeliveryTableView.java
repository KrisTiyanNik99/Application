package com.example.data_models.table_models;

import com.data_maneger.ProductFactory;
import com.data_maneger.JsonParser;
import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class DeliveryTableView extends MainTableView {
    @Override
    public void getInformationForDisplay() {
        String fileName = getFilePath();
        List<Product> products = getProductsFromDataFile(fileName);
    }

    protected List<Product> getProductsFromDataFile(String fileName) {
        /*
            We make a basic implementation of this method that retrieves information from json files, because we want
            all tables that will appear in this menu to keep their information in such an extension. If information is
            to be retrieved from a file with a different extension than json, the entire method must be overridden.
         */

        List<Product> productsList = new ArrayList<>();

//
//        try (InputStream in = Files.newInputStream(Paths.get(filePath))) {
//            JSONTokener tokenizer = new JSONTokener(in);
//            JSONObject jsonObject = new JSONObject(tokenizer);
//
//            JSONArray jsonArray = findJsonArray(jsonObject);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonData = jsonArray.getJSONObject(i);
//
//                List<Object> jsonDataFields = parseJsonFields(jsonData);
//                DataType type = DataType.parseDataType(jsonData.getString("type"));
//                productsList.add(ProductFactory.createProductFromJsonData(jsonDataFields, type));
//            }
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

        return productsList;
    }

//    @Override
//    public List<Object> getJsonFields(JSONObject jsonData) {
//
//        String name = jsonData.getString("name");
//        double price = jsonData.getDouble("price");
//
//        return Arrays.asList(name, price);
//    }

    protected abstract String getFilePath();
}
