package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.UserDTO;

public class UserResponsive {
    private boolean success;
    private UserDTO data;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public UserDTO getData() {
        return data;
    }
    public void setData(UserDTO data) {
        this.data = data;
    }
}
