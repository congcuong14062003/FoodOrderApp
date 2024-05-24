package com.example.foodorderapp.viewmodal;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodorderapp.object.FoodDTO;
import com.example.foodorderapp.object.OrderDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.ListFoodResponsive;
import com.example.foodorderapp.retrofit.OrderResponse;
import com.example.foodorderapp.retrofit.SearchRequest;

import java.net.HttpCookie;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodViewModel extends ViewModel {
    private MutableLiveData<List<FoodDTO>> foodList;
    private MutableLiveData<Boolean> errorLiveData = new MutableLiveData<>();

    public LiveData<List<FoodDTO>> getFoodList() {
        if (foodList == null) {
            foodList = new MutableLiveData<>();
            loadFoodList();
        }
        return foodList;
    }

    public LiveData<List<FoodDTO>> getfoodList(String userInput) {
        if (foodList == null) {
            foodList = new MutableLiveData<>();

            loadfoodList(userInput);
        }
        return foodList;
    }

    public LiveData<Boolean> getErrorLiveData() {
        return errorLiveData;
    }


    private void loadfoodList( String userInput) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        String keyword = userInput;
        SearchRequest searchRequest = new SearchRequest(keyword);
        System.out.println(searchRequest.getKeyword());
        Call<ListFoodResponsive> call = apiService.getFoodListByName(searchRequest);
        call.enqueue(new Callback<ListFoodResponsive>() {
            @Override
            public void onResponse(Call<ListFoodResponsive> call, Response<ListFoodResponsive> response) {
                if (response.isSuccessful()) {
                    ListFoodResponsive listFoodResponsive = response.body();
                    if ( listFoodResponsive.isStatus() && listFoodResponsive !=null ) {
                        foodList.setValue(listFoodResponsive.getData());
                        Log.d("OrderViewModel", "API call successful."); // Log success message
                        errorLiveData.setValue(false);
                    } else {
                        Log.e("OrderViewModel", "API call failed: Invalid response."); // Log error message
                    }
                } else {
                    Log.e("FoodViewModel", "API call failed: " + response.message()); // Log error message
                    errorLiveData.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<ListFoodResponsive> call, Throwable t) {
                Log.e("OrderViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }




    private void loadFoodList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ListFoodResponsive> call = apiService.getFoods();
        call.enqueue(new Callback<ListFoodResponsive>() {
            @Override
            public void onResponse(Call<ListFoodResponsive> call, Response<ListFoodResponsive> response) {
                if (response.isSuccessful()) {
                    ListFoodResponsive listFoodResponsive = response.body();
                    if (listFoodResponsive != null && listFoodResponsive.isStatus()) {
                        foodList.setValue(listFoodResponsive.getData());
                        Log.d("FoodViewModel", "API call successful."); // Log success message
                    } else {
                        Log.e("FoodViewModel", "API call failed: Invalid response."); // Log error message
                    }
                } else {
                    Log.e("FoodViewModel", "API call failed: " + response.message()); // Log error message
                }
            }

            @Override
            public void onFailure(Call<ListFoodResponsive> call, Throwable t) {
                Log.e("OrderViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }


}
