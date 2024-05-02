package com.example.foodorderapp.retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "https://food-app-server-murex.vercel.app/";

    @GET("orders/list/{userId}")
    Call<OrderResponse> getOrders(@Path("userId") int userId);
    // đặt hàng
    @FormUrlEncoded
    @POST("orders/post_orders")
    Call<OrderFoodResponsive> postOrder(@Field("food_id") int food_id, @Field("user_id") int user_id,  @Field("quantity") int quantity,  @Field("total_price") double total_price);

    // danh sách món ăn
    @GET("foods")
    Call<ListFoodResponsive> getFoods();
    // chi tiết món ăn
    @GET("foods/find/{foodId}")
    Call<DetailFoodResponsive> getDetailFood(@Path("foodId") int foodId);
    // thông tin người dùng

    @POST("foods/search")
    Call<ListFoodResponsive> getFoodListByName(@Body SearchRequest searchRequest);

    @GET("users/info/{userId}")
    Call<UserResponsive> getUserInfo(@Path("userId") int userId);

    // đăng nhập
    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponsive> login(@Field("phone_number") String phoneNumber, @Field("password") String password);

    @FormUrlEncoded
    @POST("users/signup")
    Call<SignUpResponsive> signup(@Field("name") String name, @Field("phone_number") String phoneNumber, @Field("address") String address,@Field("password") String password);
    @Multipart
    @POST("upload/avartar/{userId}")
    Call<UserResponsive> updateAvatar(
            @Path("userId") int userId,
            @Part MultipartBody.Part file
    );
    @FormUrlEncoded
    @POST("upload/info")
    Call<UserResponsive> updateUser(@Field("name") String name, @Field("phone_number") String phoneNumber, @Field("address") String address,@Field("password") String password,@Field("id") int id);

    @GET("notices/list_notices/{userId}")
    Call<NotiResponsive> getNotis(@Path("userId") int userId);
}
