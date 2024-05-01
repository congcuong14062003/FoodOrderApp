package com.example.foodorderapp.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @FormUrlEncoded
    @POST("users/signup")
    Call<SignUpResponsive> signup(@Field("name") String name, @Field("phone_number") String phoneNumber, @Field("address") String address,@Field("password") String password);
    @Multipart
    @POST("/upload/avartar/{userId}")
    Call<UserResponsive> updateAvatar(
            @Path("id") int id,
            @Part MultipartBody.Part file
    );
    @FormUrlEncoded
    @POST("/upload/info")
    Call<UserResponsive> updateUser(@Field("name") String name, @Field("phone_number") String phoneNumber, @Field("address") String address,@Field("password") String password,@Field("id") int id);

}
