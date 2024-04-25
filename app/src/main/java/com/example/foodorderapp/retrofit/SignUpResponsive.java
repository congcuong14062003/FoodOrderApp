package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.SignUpUser;
import com.example.foodorderapp.object.UserDTO;

import java.util.List;

public class SignUpResponsive {
    private boolean success;
    private SignUpUser data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SignUpUser getData() {
        return data;
    }

    public void setData(SignUpUser data) {
        this.data = data;
    }
}
