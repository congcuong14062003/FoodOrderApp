package com.example.foodorderapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "https://food-app-server-murex.vercel.app/";

    @GET("orders/list/1")
    Call<OrderResponse> getOrders();

    @GET("foods")
    Call<ListFoodResponsive> getFoods();

    @GET("users/info/{userId}")
    Call<UserResponsive> getUserInfo(@Path("userId") int userId);
    @FormUrlEncoded
    @POST("users/login") // Địa chỉ API cho đăng nhập
    Call<LoginResponsive> login(@Field("phone_number") String phoneNumber, @Field("password") String password);
}
