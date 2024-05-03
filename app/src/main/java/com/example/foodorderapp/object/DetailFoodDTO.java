package com.example.foodorderapp.object;

public class DetailFoodDTO {
    private int id;
    private String img_thumbnail;
    private String name;
    private String ingredients;
    private double price;

    private String description;
    private int total_orders;
    private String average_rating;
    private int total_reviews;

    public DetailFoodDTO(int id, String img_thumbnail, String name, String ingredients, double price, String description, int total_orders, String average_rating, int total_reviews) {
        this.id = id;
        this.img_thumbnail = img_thumbnail;
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.description = description;
        this.total_orders = total_orders;
        this.average_rating = average_rating;
        this.total_reviews = total_reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg_thumbnail() {
        return img_thumbnail;
    }

    public void setImg_thumbnail(String img_thumbnail) {
        this.img_thumbnail = img_thumbnail;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal_orders() {
        return total_orders;
    }

    public void setTotal_orders(int total_orders) {
        this.total_orders = total_orders;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public int getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(int total_reviews) {
        this.total_reviews = total_reviews;
    }
}
