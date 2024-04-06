package com.example.foodorderapp;

public class ItemOrderHistory {
    private int imageResource;
    private int quantity;
    private String name;
    private String ingredients;
    private double price;

    public ItemOrderHistory(int imageResource, String name, String ingredients, double price, int quantity) {
        this.imageResource = imageResource;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.quantity = quantity;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

}
