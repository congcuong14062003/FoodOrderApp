package com.example.foodorderapp.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "https://food-app-server-murex.vercel.app/";

    @GET("orders/list/1")
    Call<OrderResponse> getOrders();

    @GET("foods")
    Call<ListFoodResponsive> getFoods();

    @POST("foods/search")
    Call<ListFoodResponsive> getFoodListByName(@Body SearchRequest searchRequest);

    @GET("users/info/{userId}")
    Call<UserResponsive> getUserInfo(@Path("userId") int userId);
    @FormUrlEncoded
    @POST("users/login") // Địa chỉ API cho đăng nhập
    Call<LoginResponsive> login(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @GET("notices/list_notices/{userId}")
    Call<NotiResponsive> getNotis(@Path("userId") int userId);
}
