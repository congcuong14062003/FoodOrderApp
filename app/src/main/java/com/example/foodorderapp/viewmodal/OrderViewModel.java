package com.example.foodorderapp.viewmodal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log; // Import Log class

import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.OrderDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderViewModel extends ViewModel {
    private MutableLiveData<List<OrderDTO>> orderList;
    int userId = UserManager.getInstance().getUserId();

    public LiveData<List<OrderDTO>> getOrderList() {
        if (orderList == null) {
            orderList = new MutableLiveData<>();
            loadOrderList(userId);
        }
        return orderList;
    }

    private void loadOrderList(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<OrderResponse> call = apiService.getOrders(userId);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse != null && orderResponse.isStatus()) {
                        orderList.setValue(orderResponse.getData());
                        Log.d("OrderViewModel", "API call successful."); // Log success message
                    } else {
                        Log.e("OrderViewModel", "API call failed: Invalid response."); // Log error message
                        // Xử lý lỗi khi không nhận được dữ liệu hợp lệ từ API
                    }
                } else {
                    Log.e("OrderViewModel", "API call failed: " + response.message()); // Log error message
                    // Xử lý lỗi khi không nhận được dữ liệu thành công từ API
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.e("OrderViewModel", "API call failed: " + t.getMessage()); // Log failure message
                // Xử lý lỗi khi gặp sự cố trong quá trình gọi API
            }
        });
    }

}
