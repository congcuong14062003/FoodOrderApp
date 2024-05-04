package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.object.OrderDTO;
import com.example.foodorderapp.object.ReviewDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.ListFoodResponsive;
import com.example.foodorderapp.retrofit.OrderResponse;
import com.example.foodorderapp.retrofit.ReviewsResponsive;
import com.example.foodorderapp.retrofit.SearchRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewViewModel extends ViewModel {
    private MutableLiveData<List<ReviewDTO>> reviewList;

    public LiveData<List<ReviewDTO>> getReviewList(int foodId) {
        if (reviewList == null) {
            reviewList = new MutableLiveData<>();
            loadReviewsList(foodId);
        }
        return reviewList;
    }

    private void loadReviewsList(int foodId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ReviewsResponsive> call = apiService.getReviews(foodId);
        call.enqueue(new Callback<ReviewsResponsive>() {
            @Override
            public void onResponse(Call<ReviewsResponsive> call, Response<ReviewsResponsive> response) {
                if (response.isSuccessful()) {
                    ReviewsResponsive reviewsResponsive = response.body();
                    if (reviewsResponsive != null && reviewsResponsive.isStatus()) {
                        reviewList.setValue(reviewsResponsive.getData());
                        Log.d("ReviewViewModel", "API call successful, fetch đánh giá thành công: " + reviewsResponsive.getData()); // Log success message
                    } else {
                        Log.e("ReviewViewModel", "API call failed: Invalid response."); // Log error message
                        // Xử lý lỗi khi không nhận được dữ liệu hợp lệ từ API
                    }
                } else {
                    Log.e("ReviewViewModel", "API call failed: " + response.message()); // Log error message
                    // Xử lý lỗi khi không nhận được dữ liệu thành công từ API
                }
            }

            @Override
            public void onFailure(Call<ReviewsResponsive> call, Throwable t) {
                Log.e("ReviewViewModel", "API call failed: " + t.getMessage()); // Log failure message
                // Xử lý lỗi khi gặp sự cố trong quá trình gọi API
            }
        });
    }


}
