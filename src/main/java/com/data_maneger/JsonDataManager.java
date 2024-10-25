package com.data_maneger;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.data_maneger.ProductFactory.getClassByType;

public class JsonDataManager implements JsonParser {

    private static final String RESOURCE_DIR = "E:\\Request App\\Application\\src\\main\\java\\";

    @Override
    public List<Product> getProductsFromJsonFile(String fileName) {

        JSONArray jsonArray = findJsonArray(getJsonFileObject(fileName));
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject productJsonObject = jsonArray.getJSONObject(i);

            // ALL json file will have and must have a parameter with name "type"
            DataType classType = DataType.parseDataType(productJsonObject.getString("type"));
            List<Object> values = getJsonValues(productJsonObject, classType);

            try {
                Product product = ProductFactory.createProductObject(getClassByType(classType), values);
                products.add(product);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return products;
    }

    @Override
    public JSONObject getJsonFileObject(String fileName) {
        /*
            The method retrieves all the information from the json file that is searched by name in the "resource"
            folder along with the field names, values, parentheses and commas as JsonObject.
         */

        String directory = RESOURCE_DIR + fileName;
        Path path = Paths.get(directory);

        try {
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File not found: " + directory);
            }

            InputStream in = Files.newInputStream(path);

            JSONTokener token = new JSONTokener(in);

            return new JSONObject(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getJsonParametersNames(JSONObject jsonParamObj) {
        /*
           Takes the parameters from the json file and returns them as List. Entire json file has the same structure
           of the individual objects in it - there will be no objects with different number of parameters - they are all
           the same from the beginning to the end. The json files themselves, however, may differ in some of the parameters
           that individual objects have. For this reason, this method will automatically take any parameters that are
           contained in the individual object. We leave this method public because it is highly likely that we will use
           it in other classes as well.
         */

        List<String> fieldNames = new ArrayList<>();
        Iterator<String> keys = jsonParamObj.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            fieldNames.add(key);
        }

        return Collections.unmodifiableList(fieldNames);
    }

    @Override
    public void saveInfoToJsonFile(String newData, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;

        JSONObject jsonFileObj = getJsonFileObject(fileName);
        JSONArray arr = findJsonArray(jsonFileObj);

        JSONObject jsonObj = new JSONObject(newData);

        arr.put(jsonObj);
        writeJsonFile(jsonFileObj, resourceDir);
    }

    @Override
    public void saveInfoToJsonFile(List<Product> products, String fileName) {
        String resourceDir = RESOURCE_DIR + fileName;

        JSONObject root = new JSONObject();
        JSONArray productsArray = new JSONArray();
        for (Product p : products) {
            JSONObject productJson = new JSONObject(p.toJsonFileFormat());
            productsArray.put(productJson);
        }

        root.put("products", productsArray);
        writeJsonFile(root, resourceDir);
    }

    @Override
    public JSONArray findJsonArray(JSONObject jsonObject) {
        /*
            All json files must follow the same structure (have one array and store the units in it). For this
            reason, in this method it is set at the moment when 1 array is found to stop the search. This is done
            to avoid exceptions and errors in the program.
         */

        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();

            if (jsonObject.get(key) instanceof JSONArray) {
                return jsonObject.getJSONArray(key);
            }
        }

        throw new IllegalArgumentException("Array was not found!");
    }

    private static Object convertJsonParameterToValue(String jsonParameterName) {

        // This method need update but for now is good enough
        if (jsonParameterName.contains(".") || jsonParameterName.contains(",")) {
            try {
                return Double.parseDouble(jsonParameterName);
            } catch (NumberFormatException e) {
                return jsonParameterName;
            }
        } else {
            try {
                return Integer.parseInt(jsonParameterName);
            } catch (IllegalArgumentException e2) {
                try {
                    return DataType.valueOf(jsonParameterName);
                } catch (IllegalArgumentException e3) {
                    return jsonParameterName;
                }
            }
        }
    }

    private List<Object> getJsonValues(JSONObject productJsonObj, DataType type) {

        List<Object> convertedParameters = new ArrayList<>();

        List<String> jsonParameters = getJsonParametersNames(productJsonObj);
        List<String> classParameters = ProductFactory.getConstructorClassParametersNames(type);
        /*
            The reason we need these nested loops (validation) is because when I get the json parameter names, they are
            saved in inconsistent order. We also need a counter to check how many matches there are between the
            parameters, because the json file can be modified without changing the logic of the code. To create a class
            from a list of Objects, we need a subset exactly as it is of the parameters from the constructor of the
            class we will create.
         */

        int parameterMatch = 0;
        // We can make this with functional programming but for now this is readable
        for (String parameterName : classParameters) {
            for (String jsonParametersName : jsonParameters) {
                if (parameterName.equalsIgnoreCase(jsonParametersName)) {
                    parameterMatch++;

                    String jsonParameterValue = productJsonObj.get(jsonParametersName).toString();
                    convertedParameters.add(convertJsonParameterToValue(jsonParameterValue));
                    break;
                }
            }
        }

        if (parameterMatch < classParameters.size()) {
            throw new IllegalArgumentException("Invalid or missing parameters in the json file!");
        }

        return Collections.unmodifiableList(convertedParameters);
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
