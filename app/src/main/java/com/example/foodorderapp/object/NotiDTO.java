package com.example.foodorderapp.object;

public class NotiDTO {

    private String title_notifi;
    private String notices_message;
    private String notices_datetime;


    public NotiDTO(String title_notifi, String order_datetime, String notices_message) {

        this.title_notifi = title_notifi;
        this.notices_message = notices_message;
        this.notices_datetime = notices_datetime;
    }

    public String getTitle_notifi() {
        return title_notifi;
    }

    public void setTitle_notifi(String title_notifi) {
        this.title_notifi = title_notifi;
    }

    public String getNotices_message() {
        return notices_message;
    }

    public void setNotices_message(String notices_message) {
        this.notices_message = notices_message;
    }

    public String getNotices_datetime() {
        return notices_datetime;
    }

    public void setNotices_datetime(String notices_datetime) {
        this.notices_datetime = notices_datetime;
    }
}


