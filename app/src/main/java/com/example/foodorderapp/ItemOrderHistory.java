package com.example.foodorderapp;

public class ItemOrderHistory {
    private String imageUrl;
    private int quantity;
    private String name;
    private String ingredients;
    private double price;
    private String purchaseDate;
    private  double totalPrice;
    public ItemOrderHistory(String imageUrl, String name, String ingredients, double price, int quantity, String purchaseDate, double totalPrice) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getName() {
        return name;
    }
    public String getPurchaseDate() {
        return purchaseDate;
    }
    public String getIngredients() {
        return ingredients;
    }
    public double getPrice() {
        return price;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public int getQuantity() {
        return quantity;
    }

}
