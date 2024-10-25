package com.example.data_models.product_models;

public enum DataType {
    VEDENA("com/data/vedena.json"),
    BIROTERAPIYA("com/data/biroterapiya.json"),
    CONSUMER("com/data/consumer.json");

    private final String fileName;
    DataType(String fileName) {
        this.fileName = fileName;
    }

    public static DataType parseDataType(String dataType) {
        return DataType.valueOf(dataType.toUpperCase());
    }

    public String getFileDirectory() {
        return fileName;
    }
}