package com.example.foodorderapp.object;

public class UserDTO {
        private int id;
        private String name;
        private String phoneNumber;
        private String address;
        private String password;
        private String avatar_thumbnail;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar_thumbnail() {
        return avatar_thumbnail;
    }

    public void setAvatar_thumbnail(String avatar_thumbnail) {
        this.avatar_thumbnail = avatar_thumbnail;
    }
}
