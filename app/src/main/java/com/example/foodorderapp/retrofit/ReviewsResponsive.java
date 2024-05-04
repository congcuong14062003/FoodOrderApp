package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.NotiDTO;
import com.example.foodorderapp.object.ReviewDTO;

import java.util.List;

public class ReviewsResponsive {
    private boolean status;
    private List<ReviewDTO> data;

    public boolean isStatus() {return status;}

    public void setStatus(boolean status) {this.status=status;}

    public List<ReviewDTO> getData(){return data;}

    public void setData(List<ReviewDTO> data) {this.data=data;}


}
