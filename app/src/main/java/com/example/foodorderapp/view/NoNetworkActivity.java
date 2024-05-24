package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.foodorderapp.NetworkUtils;
import com.example.foodorderapp.R;

public class NoNetworkActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_network);

        Button retryButton = findViewById(R.id.retry_button);
        retryButton.setOnClickListener(v -> {
            if (NetworkUtils.isNetworkAvailable(this)) {
                finish();
            } else {
                Toast.makeText(this, "Vẫn chưa có kết nối mạng", Toast.LENGTH_LONG).show();
            }
        });
    }
}
