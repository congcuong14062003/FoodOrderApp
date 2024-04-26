package com.example.foodorderapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "https://food-app-server-murex.vercel.app/";

    @GET("orders/list/{userId}")
    Call<OrderResponse> getOrders(@Path("userId") int userId);

    // danh sách món ăn
    @GET("foods")
    Call<ListFoodResponsive> getFoods();
    // chi tiết món ăn
    @GET("foods/find/{foodId}")
    Call<DetailFoodResponsive> getDetailFood(@Path("foodId") int foodId);
    // thông tin người dùng
    @GET("users/info/{userId}")
    Call<UserResponsive> getUserInfo(@Path("userId") int userId);

    // đăng nhập
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponsive> login(@Field("phone_number") String phoneNumber, @Field("password") String password);


    @GET("notices/list/1")
    Call<NotiResponsive> getNotis();

}
