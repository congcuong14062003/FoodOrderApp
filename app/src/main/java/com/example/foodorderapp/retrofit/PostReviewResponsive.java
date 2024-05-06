package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.OrderDTO;
import com.example.foodorderapp.object.PostOrder;

import java.util.List;

public class OrderFoodResponsive {
    private boolean status;
    private PostOrder data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PostOrder getData() {
        return data;
    }

    public void setData(PostOrder data) {
        this.data = data;
    }
}
