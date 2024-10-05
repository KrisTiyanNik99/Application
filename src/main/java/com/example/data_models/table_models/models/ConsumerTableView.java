package com.example.data_models.table_models.models;

import com.example.data_models.table_models.DeliveryTableView;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class ConsumerTableView extends DeliveryTableView {
    @Override
    protected String getFilePath() {
        return "consumer.json";
    }
}
