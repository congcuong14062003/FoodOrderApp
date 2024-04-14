package com.example.foodorderapp.object;

public class FoodDTO {
    private String img_thumbnail;
    private int quantity;
    private String name;
    private String ingredients;
    private double price;

    public FoodDTO(String img_thumbnail, String name, String ingredients) {
        this.img_thumbnail = img_thumbnail;
        this.name = name;
        this.ingredients = ingredients;
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
