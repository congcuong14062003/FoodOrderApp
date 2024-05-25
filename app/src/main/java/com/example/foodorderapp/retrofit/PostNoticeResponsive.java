package com.example.foodorderapp.retrofit;

import com.example.foodorderapp.object.NotiDTO;
import com.example.foodorderapp.object.PostNotiDTO;
import com.example.foodorderapp.object.PostReviewDTO;

public class PostNoticeResponsive {
    private boolean status;
    private PostNotiDTO data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PostNotiDTO getData() {
        return data;
    }

    public void setData(PostNotiDTO data) {
        this.data = data;
    }
}
