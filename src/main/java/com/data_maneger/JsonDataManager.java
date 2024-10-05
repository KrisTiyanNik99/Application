package com.data_maneger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonDataManager implements JsonParser {
    @Override
    public List<String> getJsonFields(JSONObject jsonDataObj) {
        JSONArray jsonArray = findJsonArray(jsonDataObj);
        if (jsonArray.length() <= 0) {
            //throw Моя измислена грешка!
        }

        /*
            Takes the parameters from the first object in the json file and returns them as an array. In this particular
            case, we only check the first object, because the entire json file has the same structure of the individual
            objects in it - there will be no objects with different number of parameters - they are all the same from
            the beginning to the end. The json files themselves, however, may differ in some of the parameters that
            individual objects have. For this reason, this method will automatically take any parameters that are
            contained in the individual object.
         */
        Set<String> fieldNames = new LinkedHashSet<>();
        //for -> jsonArray = JSONObject firstEntity = jsonArray.getJSONObject(i);
        JSONObject firstEntity = jsonArray.getJSONObject(0);

        Iterator<String> keys = firstEntity.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            fieldNames.add(key);
        }

        return Arrays.asList(fieldNames.toString());
    }

    @Override
    public Object mapJsonFieldsToValues() {
        return ;
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

        throw new IllegalArgumentException("Array was not  found!");
    }

    @Override
    public String readJsonFile(String fileName) {
        /*
            A method that for now only converts JsonObject to String, because working with String is much easier,
            faster and intuitive. Its name remains as such because future plans are to override it to perform complex
            operations.
         */
        JSONObject jsonData = getJsonFileObject(fileName);

        return jsonData.toString();
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
}
