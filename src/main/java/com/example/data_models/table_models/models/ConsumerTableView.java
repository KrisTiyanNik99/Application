package com.example.data_models.table_models.models;

import com.example.data_models.table_models.DeliveryTableView;

public class ConsumerTableView extends DeliveryTableView {
    @Override
    protected String getFileName() {
        return "consumer.json";
    }
}
