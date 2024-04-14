package com.example.foodorderapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    String BASE_URL = "https://food-app-server-murex.vercel.app/";

    @GET("orders/list/1")
    Call<OrderResponse> getOrders();

    @GET("foods")
    Call<ListFoodResponsive> getFoods();
}
