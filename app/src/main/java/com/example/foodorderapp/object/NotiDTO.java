package com.example.foodorderapp.object;

public class NotiDTO {
    private String img_thumbnail;
    private String name;
    private int quantity;
    private String order_datetime;
    private double total_price;

    public NotiDTO(String img_thumbnail, String name, int quantity, String order_datetime, float total_price) {
        this.img_thumbnail = img_thumbnail;
        this.name = name;
        this.quantity = quantity;
        this.order_datetime = order_datetime;
        this.total_price=total_price;
    }

    public String getImgNoti() {
        return img_thumbnail;
    }

    public void setImgNoti(String img_thumbnail) {
        this.img_thumbnail = img_thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrder_datetime() {
        return order_datetime;
    }

    public void setorder_datetime(String order_datetime) {
        this.order_datetime = order_datetime;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void settotal_price(float total_price) {
        this.total_price = total_price;
    }
}
