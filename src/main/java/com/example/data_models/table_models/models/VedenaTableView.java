package com.example.data_models.table_models.models;

import com.example.data_models.table_models.DeliveryTableView;

public class VedenaTableView extends DeliveryTableView {
    @Override
    protected String getFileName() {
        return "vedena.json";
    }
}
