package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderapp.object.PostReviewDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.PostReviewResponsive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostReviewViewModel {
    private MutableLiveData<Boolean> postReviewStatus;

    public LiveData<Boolean> getPostOrderStatus() {
        if (postReviewStatus == null) {
            postReviewStatus = new MutableLiveData<>();
        }
        return postReviewStatus;
    }

    public void postReview(int food_id, int user_id, String comment, int rate) {
        PostReviewDTO postReview = new PostReviewDTO(food_id, user_id, comment, rate);
//        postReview.setFood_id(food_id);
//        postReview.setUser_id(user_id);
//        postReview.setQuantity(quantity);
//        postReview.setTotal_price(total_price);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<PostReviewResponsive> call = apiService.postReview(postReview.getFood_id(), postReview.getUser_id(), postReview.getComment(), postReview.getRate());
        call.enqueue(new Callback<PostReviewResponsive>() {
            @Override
            public void onResponse(Call<PostReviewResponsive> call, Response<PostReviewResponsive> response) {
                Log.d("PostReviewViewModel", "Response nè: " + response.code()); // Log response code
                if (response.code() == 200) {
                    PostReviewResponsive postReviewResponsiveve = response.body();
                    if (postReviewResponsiveve != null) {
                        postReviewStatus.setValue(true);
                        Log.d("PostOrderViewModel", "API call successful.");
                    } else {
                        postReviewStatus.setValue(false);
                    }
                } else {
                    postReviewStatus.setValue(false);
                    String errorMessage;
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBodyString);
                            errorMessage = jsonObject.getString("message");
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            errorMessage = "Unknown error";
                        }
                    } else {
                        errorMessage = "Server error";
                    }
                    Log.e("PostOrderViewModel", "API call failed: " + errorMessage); // Log error message
                }
            }

            @Override
            public void onFailure(Call<PostReviewResponsive> call, Throwable t) {
                postReviewStatus.setValue(false);
                Log.e("PostOrderViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }
}
