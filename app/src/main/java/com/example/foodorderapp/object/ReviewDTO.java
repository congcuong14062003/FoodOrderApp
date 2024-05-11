package com.example.foodorderapp.object;

public class ReviewDTO {
    private int id;
    private String name;
    private String avatar_thumbnail;
    private String comment;
    private int rate;
    private String reviews_datetime;

    public ReviewDTO(int id, String name, String avatar_thumbnail, String comment, int rate, String reviews_datetime) {
        this.id = id;
        this.name = name;
        this.avatar_thumbnail = avatar_thumbnail;
        this.comment = comment;
        this.rate = rate;
        this.reviews_datetime = reviews_datetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_thumbnail() {
        return avatar_thumbnail;
    }

    public void setAvatar_thumbnail(String avatar_thumbnail) {
        this.avatar_thumbnail = avatar_thumbnail;
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

    public String getReviews_datetime() {
        return reviews_datetime;
    }

    public void setReviews_datetime(String reviews_datetime) {
        this.reviews_datetime = reviews_datetime;
    }
}
