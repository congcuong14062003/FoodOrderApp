package com.example.foodorderapp.object;

public class ResultDTO {
    private String name;
    private String description;
    private int total_reviews;
    private String average_rating;
    private int image;

    public ResultDTO(String name, String description, int total_reviews, int image, String average_rating){
        this.name=name;
        this.description=description;
        this.total_reviews=total_reviews;
        this.image=image;
        this.average_rating= average_rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
