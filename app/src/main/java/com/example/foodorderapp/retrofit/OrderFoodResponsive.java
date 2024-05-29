package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.PostOrderDTO;

public class OrderFoodResponsive {
    private boolean status;
    private PostOrderDTO data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PostOrderDTO getData() {
        return data;
    }

    public void setData(PostOrderDTO data) {
        this.data = data;
    }
}
