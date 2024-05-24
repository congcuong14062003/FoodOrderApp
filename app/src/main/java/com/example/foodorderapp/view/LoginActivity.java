package com.example.foodorderapp.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.foodorderapp.LoadingManager;
import com.example.foodorderapp.NetworkUtils;
import com.example.foodorderapp.R;
import com.example.foodorderapp.viewmodal.LoginViewModel;

public class LoginActivity extends BaseActivity {
    private EditText txtUserName;
    private EditText txtPassWord;
    private Button btnsLogin;
    private TextView buttonToRegister;
    private TextView errorMessageTextView;
    private LoginViewModel loginViewModel;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        // thong tin nguoi dung
        txtUserName = findViewById(R.id.txtPhoneNumber);
        txtPassWord = findViewById(R.id.txtPassword);
        btnsLogin = findViewById(R.id.btnLogin);
        buttonToRegister = findViewById(R.id.buttonToRegister);
        errorMessageTextView = findViewById(R.id.errorMessage);
        loginViewModel = new LoginViewModel(errorMessageTextView);

        btnsLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(LoginActivity.this)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNumber = txtUserName.getText().toString().trim();
                String password = txtPassWord.getText().toString().trim();
                loginViewModel.login(phoneNumber, password);
            }
        });

        loginViewModel.getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loginStatus) {
                if (loginStatus) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    // Đăng nhập thành công, chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng nhập không thành công, hiển thị thông báo lỗi
                    txtUserName.setText("");
                    txtPassWord.setText("");
                }
            }
        });

        buttonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
