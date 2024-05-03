package com.example.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.foodorderapp.view.LoginActivity;
<<<<<<< HEAD
=======
import com.example.foodorderapp.view.MainActivity;
>>>>>>> b9ad88c6b6543931e462649936d63b3c31c58bcd

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        // nhánh dev
        // cường code thêm
        // cường code thêm part 2
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                if(NetworkUtils.isNetworkAvailable(IntroActivity.this)){
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(IntroActivity.this, "Vui lòng kết nối mạng", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }
}