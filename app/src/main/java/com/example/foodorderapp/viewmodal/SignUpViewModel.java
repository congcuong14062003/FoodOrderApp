package com.example.foodorderapp.viewmodal;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foodorderapp.object.SignUpUser;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.SignUpResponsive;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpViewModel {
    private MutableLiveData<Boolean> signUpStatus;
    private TextView errorMessageTextView;

    public SignUpViewModel(TextView errorMessageTextView) {
        this.errorMessageTextView = errorMessageTextView;
    }

    public LiveData<Boolean> getSignUpStatus() {
        if (signUpStatus == null) {
            signUpStatus = new MutableLiveData<>();
        }
        return signUpStatus;
    }

    public void signup(String name, String phoneNumber, String address, String password) {
        SignUpUser user = new SignUpUser();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setPassword(password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<SignUpResponsive> call = api.signup(user.getName(), user.getPhoneNumber(), user.getAddress(), user.getPassword());

        call.enqueue(new Callback<SignUpResponsive>() {
            @Override
            public void onResponse(Call<SignUpResponsive> call, Response<SignUpResponsive> response) {
                if (response.isSuccessful()) {
                    SignUpResponsive signUpResponse = response.body();
                    if (signUpResponse != null && signUpResponse.isSuccess()) {
                        signUpStatus.setValue(true);
                    } else {
                        signUpStatus.setValue(false);
                        errorMessageTextView.setText(signUpResponse != null ? signUpResponse.getMessage() : "Unknown error");
                    }
                } else {
                    signUpStatus.setValue(false);
                    errorMessageTextView.setText("Server error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<SignUpResponsive> call, Throwable t) {
                signUpStatus.setValue(false);
                errorMessageTextView.setText("Network error: " + t.getMessage());
            }
        });
    }
}
