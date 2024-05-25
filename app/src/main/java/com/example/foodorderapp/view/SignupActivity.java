package com.example.foodorderapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderapp.NetworkUtils;
import com.example.foodorderapp.R;
import com.example.foodorderapp.viewmodal.SignUpViewModel;

public class SignupActivity extends AppCompatActivity {
    private EditText txtName, txtPhoneNumber, txtAddress, txtPassword, txtRepeatPassword;
    private TextView errTextName, errSDT, errAddress, errPassword, errRepeatPassword;
    private Button btnSignUp;
    private TextView btnToLogin;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Observe signUpStatus LiveData
        signUpViewModel.getSignUpStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    // Handle successful sign-up
                    Toast.makeText(SignupActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    // Navigate to login activity or perform any other action
                } else {
                    // Handle failed sign-up (error message should already be set in ViewModel)
                    Toast.makeText(SignupActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtName.getText().toString().trim().isEmpty()){
                        errTextName.setText("Vui lòng nhập họ và tên!");
                    } else {
                        errTextName.setText("");
                    }
                }

            }
        });

        txtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtPhoneNumber.getText().toString().trim().isEmpty()){
                        errSDT.setText("Vui lòng nhập số điện thoại!");
                    }else{
                        errSDT.setText("");
                    }
                }

            }
        });
        txtAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtAddress.getText().toString().trim().isEmpty()){
                        errAddress.setText("Vui lòng nhập địa chỉ của bạn!");
                    }else {
                        errAddress.setText("");
                    }
                }

            }
        });
        txtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtPassword.getText().toString().trim().isEmpty()){
                        errPassword.setText("Vui lòng nhập mật khẩu!");
                    } else if (txtPassword.getText().toString().trim().length() < 6) {
                        errPassword.setText("Mật khẩu tối thiểu phải có 6 ký tự");
                    } else {
                        errPassword.setText("");
                    }
                }

            }
        });
        txtRepeatPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    if(txtRepeatPassword.getText().toString().trim().isEmpty()){
                        errRepeatPassword.setText("Vui lòng nhập trường nhắc lại mật khẩu!");
                    } else if (!txtPassword.getText().toString().trim().equals(txtRepeatPassword.getText().toString().trim())) {
                        errRepeatPassword.setText("Mật khẩu không khớp");
                    } else {
                        errRepeatPassword.setText("");
                    }
                }

            }
        });
        // Set click listeners
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(SignupActivity.this)) {
                    Toast.makeText(SignupActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = txtName.getText().toString().trim();
                String phoneNumber = txtPhoneNumber.getText().toString().trim();
                String address = txtAddress.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String repeatPassword = txtRepeatPassword.getText().toString().trim();
                if(name.isEmpty() && phoneNumber.isEmpty() && address.isEmpty() &&password.isEmpty() && repeatPassword.isEmpty()){
                    errTextName.setText("Vui lòng nhập họ và tên!");
                    errSDT.setText("Vui lòng nhập số điện thoại!");
                    errAddress.setText("Vui lòng nhập địa chỉ của bạn!");
                    errPassword.setText("Vui lòng nhập mật khẩu!");
                    errRepeatPassword.setText("Vui lòng nhập trường nhắc lại mật khẩu!");
                } else if (password.length() < 6) {
                    errPassword.setText("Mật khẩu tối thiểu phải có 6 ký tự");
                } else if (!repeatPassword.equals(password)) {
                    errRepeatPassword.setText("Mật khẩu không khớp");
                } else {
                    signUpViewModel.signup(name, phoneNumber, address, password);
                }
            }
        });

        btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetworkUtils.isNetworkAvailable(SignupActivity.this)) {
                    Toast.makeText(SignupActivity.this, "Vui lòng kiểm tra kết nối mạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        txtName = findViewById(R.id.editTextName);
        txtPhoneNumber = findViewById(R.id.editSDT);
        txtAddress = findViewById(R.id.editAddress);
        txtPassword = findViewById(R.id.editPassword);
        txtRepeatPassword = findViewById(R.id.editRepeatPassword);
        errTextName = findViewById(R.id.errorMessageName);
        errAddress = findViewById(R.id.errorMessageAddress);
        errSDT = findViewById(R.id.errorMessageSDT);
        errPassword = findViewById(R.id.errorMessagePassword);
        errRepeatPassword = findViewById(R.id.errorMessageRepeatPassword);
        btnSignUp = findViewById(R.id.buttonResign);
        btnToLogin = findViewById(R.id.buttonToLogin);
    }


}
