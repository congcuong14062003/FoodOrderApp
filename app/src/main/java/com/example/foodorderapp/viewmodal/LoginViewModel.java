package com.example.foodorderapp.viewmodal;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.LoginUser;
import com.example.foodorderapp.object.UserDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.LoginResponsive;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewModel {
    private MutableLiveData<Boolean> loginStatus;
    private TextView errorMessageTextView; // TextView để hiển thị thông báo lỗi

    // Constructor để truyền TextView từ LoginActivity
    public LoginViewModel(TextView errorMessageTextView) {
        this.errorMessageTextView = errorMessageTextView;
    }

    public LiveData<Boolean> getLoginStatus() {
        if (loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public void login(String phoneNumber, String password) {
        LoginUser user = new LoginUser();
        user.setPhone_number(phoneNumber);
        user.setPassword(password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginResponsive> call = apiService.login(user.getPhone_number(), user.getPassword());
        call.enqueue(new Callback<LoginResponsive>() {
            @Override
            public void onResponse(Call<LoginResponsive> call, Response<LoginResponsive> response) {

                Log.d("LoginViewModel", "Response nè: " + response.code()); // Log response code
                if (response.code() == 200) {
                    LoginResponsive loginResponse = response.body();
                    if (loginResponse != null && loginResponse.isSuccess()) {
                        loginStatus.setValue(true);
                        Log.d("LoginViewModel", "API call successful."); // Log success message
                        // Log response data
                        UserDTO user = new UserDTO();
                        if (loginResponse.getData() != null) {
                            Log.d("LoginViewModel", "User ID: " + loginResponse.getData().getId());
                            // Đặt id người dùng khi đăng nhập thành công
                            UserManager.getInstance().setUserId(loginResponse.getData().getId());
                            Log.d("LoginViewModel", "User Name: " + loginResponse.getData().getName());
                        }
                    } else {
                        loginStatus.setValue(false);
                        String errorMessage = loginResponse != null ? loginResponse.getMessage() : "Unknown error";
                        // Hiển thị thông báo lỗi
                        errorMessageTextView.setVisibility(View.VISIBLE);
                        errorMessageTextView.setText(errorMessage);
                        Log.e("LoginViewModel", "API call failed: " + errorMessage); // Log error message
                    }
                }
                else {
                    loginStatus.setValue(false);
                    String errorMessage;
                    if (response.errorBody() != null) {
                        try {
                            String errorBodyString = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorBodyString);
                            errorMessage = jsonObject.getString("message");
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            errorMessage = "Unknown error";
                        }
                    } else {
                        errorMessage = "Server error";
                    }
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    errorMessageTextView.setText(errorMessage);
                    Log.e("LoginViewModel", "API call failed: " + errorMessage); // Log error message
                }
            }

            @Override
            public void onFailure(Call<LoginResponsive> call, Throwable t) {
                loginStatus.setValue(false);
                Log.d("LoginViewModel", "Vào đây"); // Log response code
                Log.e("LoginViewModel", "API call failed: " + t.getMessage()); // Log failure message
            }
        });
    }
}
