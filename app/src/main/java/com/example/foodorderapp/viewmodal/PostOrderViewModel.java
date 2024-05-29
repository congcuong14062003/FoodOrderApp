package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderapp.object.PostOrderDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.OrderFoodResponsive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostOrderViewModel {
    private MutableLiveData<Boolean> postOrderStatus;

    public LiveData<Boolean> getPostOrderStatus() {
        if (postOrderStatus == null) {
            postOrderStatus = new MutableLiveData<>();
        }
        return postOrderStatus;
    }

    public void postOrder(int food_id, int user_id, int quantity, double total_price) {
        PostOrderDTO postOrder = new PostOrderDTO();
        postOrder.setFood_id(food_id);
        postOrder.setUser_id(user_id);
        postOrder.setQuantity(quantity);
        postOrder.setTotal_price(total_price);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<OrderFoodResponsive> call = apiService.postOrder(postOrder.getFood_id(), postOrder.getUser_id(), postOrder.getQuantity(), postOrder.getTotal_price());
        call.enqueue(new Callback<OrderFoodResponsive>() {
            @Override
            public void onResponse(Call<OrderFoodResponsive> call, Response<OrderFoodResponsive> response) {
                Log.d("PostOrderViewModel", "Response nè: " + response.code()); // Log response code
                if (response.code() == 200) {
                    OrderFoodResponsive orderFoodResponsive = response.body();
                    if (orderFoodResponsive != null) {
                        postOrderStatus.setValue(true);
                        Log.d("PostOrderViewModel", "API call successful.");
                    } else {
                        postOrderStatus.setValue(false);
                    }
                } else {
                    postOrderStatus.setValue(false);
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
            public void onFailure(Call<OrderFoodResponsive> call, Throwable t) {
                postOrderStatus.setValue(false);
                Log.e("PostOrderViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }
}
