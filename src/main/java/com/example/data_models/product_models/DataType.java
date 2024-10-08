package com.example.data_models.product_models;

public enum DataType {
    VEDENA,
    BIROTERAPIYA,
    CONSUMER;

    public static DataType parseDataType(String type) {
        checkForEmptyValue(type);

        try {
            return DataType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown DataType: " + type);
        }
    }

    private static void checkForEmptyValue(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Invalid DataType: null or empty");
        }
    }
}