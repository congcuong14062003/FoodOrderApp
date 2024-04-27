package com.example.foodorderapp.object;

public class FoodDTO {
<<<<<<< HEAD
    int id;
=======
    private int id;
>>>>>>> CUONG_NG
    private String img_thumbnail;
    private String name;
    private String ingredients;
<<<<<<< HEAD
    private String average_rating;
    private int total_reviews;


    public FoodDTO(int id, String img_thumbnail, String name, String ingredients, String average_rating, int total_reviews) {
        this.id = id;
=======
    private double price;
    private String description;
    private int total_reviews;
    private String average_rating;

    public FoodDTO(String img_thumbnail, String name, String ingredients,String average_rating, String description, int total_reviews,int id) {
>>>>>>> CUONG_NG
        this.img_thumbnail = img_thumbnail;
        this.id=id;
        this.name = name;
        this.ingredients = ingredients;
<<<<<<< HEAD
        this.average_rating = average_rating;
        this.total_reviews = total_reviews;
=======
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
>>>>>>> CUONG_NG
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
