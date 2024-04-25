package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.R;
import com.example.foodorderapp.object.DetailFoodDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.DetailFoodResponsive;
import com.squareup.picasso.Picasso;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    Button btn_order_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Nhận ID của thức ăn từ Intent
        int foodId = getIntent().getIntExtra("foodId", -1);
        Log.d("Màn Detail", "FoodId: " + foodId);
        if (foodId != -1) {
            getFetailFood(foodId);
        }
    }

    private void getFetailFood(int foodId) {
        // Gọi API để nhận dữ liệu chi tiết của món ăn
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<DetailFoodResponsive> call = apiService.getDetailFood(foodId);
        call.enqueue(new Callback<DetailFoodResponsive>() {
            @Override
            public void onResponse(Call<DetailFoodResponsive> call, Response<DetailFoodResponsive> response) {
                if (response.isSuccessful()) {
                    DetailFoodResponsive detailFoodResponse = response.body();
                    detailFoodResponse.setSuccess(true);
                    if (detailFoodResponse != null && detailFoodResponse.isSuccess()) {
                        DetailFoodDTO detailFoodDTO = detailFoodResponse.getData();
                        Log.d("Màn Chi tiết", "Data đồ ăn: " + detailFoodDTO);
                        // Xử lý dữ liệu và cập nhật giao diện
                        updateUI(detailFoodDTO);
                    } else {
                        Log.e("DetailActivity", "API call failed: Invalid response.");
                    }
                } else {
                    Log.e("DetailActivity", "API call failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<DetailFoodResponsive> call, Throwable t) {
                Log.e("DetailActivity", "API call failed: " + t.getMessage());
            }
        });
    }

    private void updateUI(DetailFoodDTO detailFoodDTO) {
        setContentView(R.layout.activity_detail); // Gọi setContentView() sau khi dữ liệu đã được xử lý

        // Ánh xạ các view từ layout
        ImageView img_detail = findViewById(R.id.img_detail);
        TextView name_detail = findViewById(R.id.name_detail);
        TextView ingredient_detail = findViewById(R.id.ingredient_detail);
        TextView price_detail = findViewById(R.id.price_detail);
        TextView total_reviews = findViewById(R.id.total_reviews_detail);
        TextView solds = findViewById(R.id.sold);
        TextView description = findViewById(R.id.description_detail);
        ImageView[] starImages = new ImageView[5];
        starImages[0] = findViewById(R.id.star1);
        starImages[1] = findViewById(R.id.star2);
        starImages[2] = findViewById(R.id.star3);
        starImages[3] = findViewById(R.id.star4);
        starImages[4] = findViewById(R.id.star5);

        // Set dữ liệu cho các view
        Picasso.get().load(detailFoodDTO.getImg_thumbnail()).into(img_detail);
        name_detail.setText(detailFoodDTO.getName());
        description.setText(detailFoodDTO.getDescription());
        ingredient_detail.setText(detailFoodDTO.getIngredients());
        price_detail.setText(String.valueOf(detailFoodDTO.getPrice())); // Chuyển đổi giá trị double sang String
        total_reviews.setText(String.valueOf(detailFoodDTO.getTotal_reviews())); // Chuyển đổi giá trị double sang String
        solds.setText(String.valueOf(detailFoodDTO.getTotal_orders())); // Chuyển đổi giá trị double sang String
        Log.d("Màn chi tiết đồ ăn", "id đồ ăn: " +detailFoodDTO.getId());
        // Set số sao
        setRatingStars(detailFoodDTO.getAverage_rating(), starImages);

        btn_order_food = findViewById(R.id.book_food_btn);
        btn_order_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, OrderMainActivity.class);
                intent.putExtra("foodId", detailFoodDTO.getId());
                startActivity(intent);
                finish();
            }
        });
    }
    private void setRatingStars(String averageRating, ImageView[] starImages) {
        // Kiểm tra nếu averageRating là null hoặc rỗng, thì mặc định toàn bộ sẽ là sao trắng
        if (averageRating == null || averageRating.isEmpty()) {
            setAllStarImage(false, starImages);
        } else {
            Log.d("DetailActivity", "Món ăn có đánh giá " + averageRating);
            int avgNumberRating = Integer.parseInt(averageRating.split("\\.")[0]);
            int minNumber = Math.min(avgNumberRating, 5);
            for (int i = 0; i < minNumber; i++) {
                starImages[i].setImageResource(R.drawable.pinkstar);
            }
        }
    }
    private void setAllStarImage(boolean isFilled, ImageView[] starImages) {
        int starResource = isFilled ? R.drawable.pinkstar : R.drawable.star;
        for (ImageView starImage : starImages) {
            starImage.setImageResource(starResource);
        }
    }
}

