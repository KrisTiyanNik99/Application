package com.operation_maneger.json_manager;

import com.operation_maneger.factory_manager.ProductFactory;
import com.example.elements_models.data_models.DataType;
import com.example.elements_models.data_models.Product;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class JsonMapper {
    private static final String JSON_CLASS_TYPE_PARAMETER = "type";

    public static List<Product> mapJsonArrayToList(JSONArray jsonArray) throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject productJsonObject = jsonArray.getJSONObject(i);

            // Each JSON file must contain a 'type' parameter to identify the product class.
            DataType classType = DataType.parseDataType(productJsonObject.getString(JSON_CLASS_TYPE_PARAMETER));

            List<Object> values = getJsonValuesForClass(productJsonObject, classType);
            Product product = ProductFactory.createProductObject(classType, values);
            products.add(product);
        }

        return products;
    }

    public static List<Object> getJsonValuesForClass(JSONObject productJsonObj, DataType type) {
        List<Object> convertedParameters = new ArrayList<>();

        List<String> jsonParameters = getJsonParametersNames(productJsonObj);
        List<String> classParameters = ProductFactory.getProductConstructorParametersNames(type);
        /*
            The reason we need validation is that when I get the json parameter names, they are saved in inconsistent
            order. We also need a counter to check how many matches there are between the parameters, because the json
            file can be modified without changing the logic of the code. To create a class from a list of Objects, we
            need a subset exactly as it is of the parameters from the constructor of the class we will create.
         */

        Set<String> jsonNames = new HashSet<>(jsonParameters);
        int parameterMatch = 0;
        for (String parameterName : classParameters) {
            if (jsonNames.contains(parameterName.toLowerCase())) {
                parameterMatch++;

                String jsonParameterValue = productJsonObj.get(parameterName).toString();
                convertedParameters.add(convertJsonParameterToValue(jsonParameterValue));
            }
        }

        if (parameterMatch < classParameters.size()) {
            throw new IllegalArgumentException("The JSON file has invalid or missing parameters!");
        }

        return Collections.unmodifiableList(convertedParameters);
    }

    public static List<Object> getElementsFromJsonArray(JSONArray jsonArray) {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject= jsonArray.getJSONObject(i);
            addValuesToObjectList(jsonObject, objects);
        }

        return objects;
    }

    private static void addValuesToObjectList(JSONObject jsonObject, List<Object> objects) {
        List<String> jsonParamNames = getJsonParametersNames(jsonObject);
        for (String jsonParamName : jsonParamNames) {
            String jsonValue = jsonObject.get(jsonParamName).toString();
            objects.add(convertJsonParameterToValue(jsonValue));
        }
    }

    private static List<String> getJsonParametersNames(JSONObject jsonParamObj) {
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

        // This method may require further refinement in the future.
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
}
