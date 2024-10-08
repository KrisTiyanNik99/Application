package com.data_maneger;

import org.json.JSONObject;

import java.util.List;

public interface JsonParser {
    JSONObject getJsonFileObject(String fileName);
    List<String> getJsonParametersNames(JSONObject jsonParamObj);
}
