package com.example.foodorderapp.viewmodal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.RealPathUtil;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.UserDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.SignUpResponsive;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.view.UserUpdateActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.graphics.Bitmap;


public class UpdateUserViewModel extends ViewModel {
    private MutableLiveData<Boolean> updateUserStatus;
    private MutableLiveData<Boolean> uploadAvtStatus;

    public UpdateUserViewModel() {
        updateUserStatus = new MutableLiveData<>();
        uploadAvtStatus = new MutableLiveData<>();
    }

    public LiveData<Boolean> getUpdateUserStatus() {
        return updateUserStatus;
    }

    public LiveData<Boolean> getUploadAvtStatus() {
        return uploadAvtStatus;
    }

    public void uploadImage(int id, File avtFile) {
        UserDTO user = new UserDTO();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api = retrofit.create(ApiService.class);

        RequestBody requestAvt = RequestBody.create(MediaType.parse("image/*"), avtFile);
        MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("avatar_thumbnail", avtFile.getName(), requestAvt);
        Call<UserResponsive> call = api.uploadAvatar(multipartBodyAvt, id);
        call.enqueue(new Callback<UserResponsive>() {
            @Override
            public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if (response.isSuccessful()) {
                    uploadAvtStatus.setValue(true);
                } else {
                    uploadAvtStatus.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                Log.e("User update", "API call failed: " + t.getMessage());
            }
        });
    }

    public void update(String name, String phoneNumber, String address, String password) {
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setPhone_number(phoneNumber);
        user.setAddress(address);
        user.setPassword(password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<UserResponsive> call = api.updateUser(name, phoneNumber, address, password, UserManager.getInstance().getUserId());
        call.enqueue(new Callback<UserResponsive>() {
            @Override
            public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if (response.isSuccessful()) {
                    UserResponsive userResponsive = response.body();
                    if (userResponsive != null && userResponsive.isSuccess()) {
                        updateUserStatus.setValue(true);
                    } else {
                        updateUserStatus.setValue(false);
                    }
                } else {
                    updateUserStatus.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                updateUserStatus.setValue(false);
                Log.e("User update", "API call failed: " + t.getMessage());
            }
        });
    }
}

