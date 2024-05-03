package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.DetailFoodDTO;
import com.example.foodorderapp.object.UserDTO;

public class DetailFoodResponsive {
    private boolean success;
    private DetailFoodDTO data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DetailFoodDTO getData() {
        return data;
    }

    public void setData(DetailFoodDTO data) {
        this.data = data;
    }
}
