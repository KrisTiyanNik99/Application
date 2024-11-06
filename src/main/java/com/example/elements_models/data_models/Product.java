package com.example.elements_models.data_models;

import javafx.scene.control.CheckBox;

public abstract class Product {
    private String name;
    private double price;
    private int quantity;
    private CheckBox select;
    private String description;
    private final DataType type;

    public Product(String name, double price, DataType type, String description) {
        setName(name);
        setPrice(price);
        select = new CheckBox();
        this.type = type;
        setDescription(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            this.name = "Unknown Product Name";
        } else {
            this.name = name;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = Math.max(price, 0);
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }

    public DataType getType() {
        return type;
    }

    public CheckBox getSelect() {
        return select;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isBlank() || description.isEmpty()) {
            this.description = "Empty";
            return;
        }

        this.description = description;
    }

    public String toJsonFileFormat() {
        StringBuilder productJsonText = new StringBuilder();
        productJsonText.append("{ ").append("\"name\": ").append("\"").append(getName()).append("\",");
        productJsonText.append("\"price\": ").append("\"").append(getPrice()).append("\",");
        productJsonText.append("\"type\": ").append("\"").append(getType()).append("\",");
        productJsonText.append("\"description\": ").append("\"").append(getDescription()).append("\"").append(" }");

        return productJsonText.toString();
    }

    @Override
    public String toString() {
        StringBuilder productText = new StringBuilder();
        productText.append(quantity).append("x ").append(name).append(System.lineSeparator());
        productText.append("Price: ").append(price).append(" leva.");
        productText.append(System.lineSeparator());
        productText.append("---------------------------------------------------------------#");

        return productText.toString();
    }
}
