package com.example.foodorderapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UserManager {
    private static UserManager instance;
    private int userId;

    private UserManager() {
        // Khởi tạo userId mặc định hoặc thực hiện các thao tác khởi tạo khác nếu cần thiết
        userId = -1; // Giá trị mặc định, có thể là một giá trị không hợp lệ
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void clearUserId(){
        this.userId = -1;
    }

}
