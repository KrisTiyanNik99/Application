package com.operation_maneger.json_manager;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class JsonReader {
    public static JSONObject getJsonFileObject(String filePath) {
        /*
            The method retrieves all the information from the json file that is searched by name in the "resource"
            folder along with the field names, values, parentheses and commas as JsonObject.
         */

        Path path = Paths.get(filePath);

        try {
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("File was not found: " + filePath);
            }

            InputStream in = Files.newInputStream(path);
            JSONTokener token = new JSONTokener(in);

            return new JSONObject(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray findJsonArray(JSONObject jsonObject) {
        /*
            All JSON files should have a consistent structure, containing a single array to store the product objects.
            For this reason, in this method it is set at the moment when 1 array is found to stop the search. This is
            done to avoid exceptions and errors in the program.
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
}
