package com.example.foodorderapp.object;

public class ResultDTO {
    private String name;
    private String description;
    private int countReview;
    private int image;

    public ResultDTO(String name, String description, int countReview, int image){
        this.name=name;
        this.description=description;
        this.countReview=countReview;
        this.image=image;
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

    public int getCountReview() {
        return countReview;
    }

    public void setCountReview(int countReview) {
        this.countReview = countReview;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
