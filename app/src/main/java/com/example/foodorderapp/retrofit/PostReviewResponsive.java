package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.PostReviewDTO;

public class PostReviewResponsive {
    private boolean status;
    private PostReviewDTO data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PostReviewDTO getData() {
        return data;
    }

    public void setData(PostReviewDTO data) {
        this.data = data;
    }
}
