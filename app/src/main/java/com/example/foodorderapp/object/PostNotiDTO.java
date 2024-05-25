package com.example.foodorderapp.object;

public class PostNotiDTO {

    private int user_id;
    private String title_notifi;
    private String notices_message;

    public PostNotiDTO(int user_id, String title_notifi, String notices_message) {
        this.user_id = user_id;
        this.title_notifi = title_notifi;
        this.notices_message = notices_message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}


