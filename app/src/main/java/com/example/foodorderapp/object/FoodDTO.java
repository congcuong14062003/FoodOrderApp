package com.example.foodorderapp.object;

public class FoodDTO {
    private int id;
    private String img_thumbnail;
    private int quantity;
    private String name;
    private String ingredients;
    private double price;
    private String description;
    private int total_reviews;
    private String average_rating;

    public FoodDTO(String img_thumbnail, String name, String ingredients,String average_rating, String description, int total_reviews,int id) {
        this.img_thumbnail = img_thumbnail;
        this.id=id;
        this.name = name;
        this.ingredients = ingredients;
        this.average_rating= average_rating;
        this.description=description;
        this.total_reviews=total_reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(int total_reviews) {
        this.total_reviews = total_reviews;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getImageUrl() {
        return img_thumbnail;
    }

    public void setImageUrl(String img_thumbnail) {
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



}
