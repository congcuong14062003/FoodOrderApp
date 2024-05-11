package com.example.foodorderapp.object;

public class PostReviewDTO {
    private int food_id;
    private int user_id;
    private String comment;
    private int rate;


    public PostReviewDTO(int food_id, int user_id, String comment, int rate) {
        this.food_id = food_id;
        this.user_id = user_id;
        this.comment = comment;
        this.rate = rate;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
