package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.R;

public class PostReviewActivity extends AppCompatActivity {

    private ImageView star1, star2, star3, star4, star5;
    private int currentRating = 5; // Biến để lưu trữ số sao hiện tại người dùng đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        star1 = findViewById(R.id.Rstar1);
        star2 = findViewById(R.id.Rstar2);
        star3 = findViewById(R.id.Rstar3);
        star4 = findViewById(R.id.Rstar4);
        star5 = findViewById(R.id.Rstar5);

        // Mặc định ban đầu, đánh giá là 5 sao
        setRating(5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(1);
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(2);
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(3);
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(4);
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRating(5);
            }
        });

        // Sử dụng giá trị của biến currentRating khi gửi lên API
        // Ví dụ:
        // postReview(currentRating);
    }

    private void setRating(int rating) {
        // Cập nhật giá trị của biến currentRating
        currentRating = rating;
        // Đặt hình ảnh cho các sao dựa trên currentRating
        star1.setImageResource(rating >= 1 ? R.drawable.pinkstar : R.drawable.star);
        star2.setImageResource(rating >= 2 ? R.drawable.pinkstar : R.drawable.star);
        star3.setImageResource(rating >= 3 ? R.drawable.pinkstar : R.drawable.star);
        star4.setImageResource(rating >= 4 ? R.drawable.pinkstar : R.drawable.star);
        star5.setImageResource(rating >= 5 ? R.drawable.pinkstar : R.drawable.star);
    }

    // Phương thức để gửi dữ liệu đánh giá lên API
    private void postReview(int rating) {
        // Gửi dữ liệu đánh giá lên API với giá trị của biến rating
        
    }
}
