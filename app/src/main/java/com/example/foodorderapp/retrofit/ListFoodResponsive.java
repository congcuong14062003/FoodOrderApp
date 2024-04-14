package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.object.OrderDTO;

import java.util.List;

public class ListFoodResponsive {
    private boolean status;
    private List<FoodDTO> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FoodDTO> getData() {
        return data;
    }

    public void setData(List<FoodDTO> data) {
        this.data = data;
    }
}
