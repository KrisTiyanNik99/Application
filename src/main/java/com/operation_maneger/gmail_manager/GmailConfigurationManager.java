package com.operation_maneger.gmail_manager;

import com.example.elements_models.gmail_config_model.GmailConfiguration;
import com.operation_maneger.factory_manager.ReflectionUtils;
import com.operation_maneger.json_manager.JsonMapper;
import com.operation_maneger.json_manager.JsonReader;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GmailConfigurationManager {
    private static final String FILE_PATH = "E:\\Request App\\Application\\src\\main\\resources\\com\\example\\configuration\\configuration.json";

    private static GmailConfigurationManager myGmailConfigManager;
    private static GmailConfiguration myGmailConfiguration;

    private GmailConfigurationManager() {
    }

    public static GmailConfigurationManager getInstance() throws Exception {
        if (myGmailConfigManager == null) {
            myGmailConfigManager = new GmailConfigurationManager();
        }
        loadGmailConfiguration();

        return myGmailConfigManager;
    }

    public GmailConfiguration getMyGmailConfiguration() throws Exception {
        if (myGmailConfigManager == null) {
            throw new Exception("Configuration cannot load!");
        }

        return myGmailConfiguration;
    }

    private static void loadGmailConfiguration() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Object> configElements = getElementsFromJson();
        myGmailConfiguration = ReflectionUtils.createObject(GmailConfiguration.class, configElements);
    }

    private static List<Object> getElementsFromJson() {
        return JsonMapper.getElementsFromJsonArray(JsonReader.findJsonArray(JsonReader.getJsonFileObject(FILE_PATH)));
    }
}
