package com.example.foodorderapp.object;

public class OrderDTO {
    private String img_thumbnail;
    private int quantity;
    private String name;
    private String ingredients;
    private double price;
    private String order_datetime;
    private  double total_price;

    public OrderDTO(String img_thumbnail, int quantity, String name, String ingredients, double price, String order_datetime, double total_price) {
        this.img_thumbnail = img_thumbnail;
        this.quantity = quantity;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.order_datetime = order_datetime;
        this.total_price = total_price;
    }

    public String getImageUrl() {
        return img_thumbnail;
    }

    public void setImageUrl(String img_thumbnail) {
        this.img_thumbnail = img_thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return order_datetime;
    }

    public void setPurchaseDate(String order_datetime) {
        this.order_datetime = order_datetime;
    }

    public double getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(double total_price) {
        this.total_price = total_price;
    }
}
