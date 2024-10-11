package com.data_maneger;

import com.example.data_models.product_models.DataType;
import com.example.data_models.product_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.data_maneger.ProductFactory.getClassByType;

public class JsonDataManager implements JsonParser {

    public List<Product> getProductsFromJsonFile(String fileName) {
        JSONArray jsonArray = findJsonArray(getJsonFileObject(fileName));

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // ALL json file will have and must have a parameter with name "type"
            DataType classType = DataType.valueOf(jsonObject.getString("type"));
            List<Object> values = getJsonValues(jsonObject, classType);

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

        URL resource = getClass().getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }

        try {
            Path path = Paths.get(resource.toURI());
            InputStream in = Files.newInputStream(path);

            JSONTokener token = new JSONTokener(in);

            return new JSONObject(token);
        } catch (URISyntaxException | IOException e) {
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

    private static JSONArray findJsonArray(JSONObject jsonObject) {
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

        throw new IllegalArgumentException("Array was not  found!");
    }

    private List<Object> getJsonValues(JSONObject productJson, DataType type) {
        List<Object> convertedParameters = new ArrayList<>();

        List<String> classParameters = ProductFactory.getConstructorClassParametersNames(type);
        List<String> jsonParameters = getJsonParametersNames(productJson);
        /*
            The reason we need these nested loops (validation) is because when I get the json parameter names, they are
            saved in inconsistent order. We also need a counter to check how many matches there are between the
            parameters, because the json file can be modified without changing the logic of the code. To create a class
            from a list of Objects, we need a subset exactly as it is of the parameters from the constructor of the
            class we will create.
         */

        int parameterMatch = 0;
        for (String parameterName : classParameters) {
            for (String jsonParametersName : jsonParameters) {
                if (parameterName.equalsIgnoreCase(jsonParametersName)) {
                    parameterMatch++;

                    String jsonParameterValue = productJson.get(jsonParametersName).toString();
                    convertedParameters.add(convertJsonParameterToValue(jsonParameterValue));
                }
            }
        }

        if (parameterMatch < classParameters.size()) {
            throw new IllegalArgumentException("Invalid parameters in the json file!");
        }

        return Collections.unmodifiableList(convertedParameters);
    }
}
