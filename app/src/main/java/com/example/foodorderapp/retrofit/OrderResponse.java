package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.object.OrderDTO;

import java.util.List;

public class OrderResponse {
    private boolean status;
    private List<OrderDTO> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<OrderDTO> getData() {
        return data;
    }

    public void setData(List<OrderDTO> data) {
        this.data = data;
    }
}
