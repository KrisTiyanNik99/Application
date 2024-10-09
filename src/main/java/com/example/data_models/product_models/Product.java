package com.example.data_models.product_models;

import javafx.scene.control.CheckBox;

public abstract class Product {
    private String name;
    private double price;
    private int quantity;
    private CheckBox selected;
    private CheckBox imperative;
    private DataType type;

    public Product(String name, double price, DataType type) {
        setName(name);
        setPrice(price);
        this.selected = new CheckBox();
        this.imperative = new CheckBox();
        this.type = type;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(quantity, 0);
    }

    public DataType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append(quantity).append("x ").append(name).append(System.lineSeparator());
        text.append("Price: ").append(price).append(" leva.");
        text.append("----------------------------------------------------#");
        text.append(System.lineSeparator());
        return text.toString();
    }
}
