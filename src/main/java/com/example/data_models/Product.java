package com.example.data_models;

import javafx.scene.control.CheckBox;

public abstract class Product {
    private String name;
    private double price;
    private int quantity;
    private CheckBox selected;
    private CheckBox imperative;
    private DataType type;

    public Product(String name, double price, DataType type) {
        this.name = name;
        this.price = price;
        this.selected = new CheckBox();
        this.imperative = new CheckBox();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
