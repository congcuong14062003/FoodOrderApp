package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.viewmodal.PostReviewViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class PostReviewActivity extends BaseActivity {

    private ImageView star1, star2, star3, star4, star5 , backBtn;
    Button btnSubmitReview;
    PostReviewViewModel postReviewViewModel;
    TextInputEditText commentUser;
    private int currentRating = 5; // Biến để lưu trữ số sao hiện tại người dùng đã chọn
    int foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        star1 = findViewById(R.id.Rstar1);
        star2 = findViewById(R.id.Rstar2);
        star3 = findViewById(R.id.Rstar3);
        star4 = findViewById(R.id.Rstar4);
        star5 = findViewById(R.id.Rstar5);
        backBtn = findViewById(R.id.backBtnReview);
        btnSubmitReview = findViewById(R.id.submitBtnReview);
        commentUser = findViewById(R.id.commentUser);
        postReviewViewModel = new PostReviewViewModel();
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                if (intent != null && intent.hasExtra("foodId")) {
                    foodId = intent.getIntExtra("foodId", -1);
                    // Sử dụng foodId khi cần thiết
                    int user_id = UserManager.getInstance().getUserId();
                    String comment = commentUser.getText().toString();
                    int rate = currentRating;
                    postReviewViewModel.postReview(foodId, user_id, comment, rate);
                }
            }
        });
        postReviewViewModel.getPostOrderStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean postOrderStatus) {
                if (postOrderStatus) {
                    Toast.makeText(PostReviewActivity.this, "Đánh giá thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PostReviewActivity.this, DetailActivity.class);
                    intent.putExtra("foodId", foodId); // Truyền ID của thức ăn qua Intent
                    startActivity(intent);
                    finish();
                } else {

                }
            }
        });

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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

    @Override
    public void onBackPressed() {
        // Kiểm tra nếu có Fragment trong Back Stack của Activity
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Nếu có, quay lại Fragment trước đó
            getSupportFragmentManager().popBackStack();
        } else {
            // Nếu không, thực hiện hành động mặc định (quay lại Activity trước đó)
            super.onBackPressed();
        }
    }
}
