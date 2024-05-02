package com.example.foodorderapp.viewmodal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.Manifest;

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
    private Uri uri;
    private static String dataFile;
    private MutableLiveData<Boolean> status;

    public LiveData<Boolean> getStatus() {
        if (status == null) {
            status = new MutableLiveData<>();
        }
        return status;
    }

    // Method to upload image
    public void uploadImage(ActivityResultLauncher<Intent> launcher, Context context, ImageView selectImg) {
        ActivityResultLauncher<Intent> mActivityResultLauncher = launcher;

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    // Method to handle activity result
    public void handleActivityResult(Intent data, Context context, ImageView selectImg) {
        if (data == null) {
            return;
        }
        uri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            selectImg.setImageBitmap(bitmap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to request permission
    public boolean requestPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void callApiUpdateAvatar(Context context, ImageView selectImg) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        String strRealPath = RealPathUtil.getRealPath(context, uri);

        File file = new File(strRealPath);
        RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartAvt = MultipartBody.Part.createFormData(dataFile, file.getName(), requestBodyAvt);

        apiService.updateAvatar(UserManager.getInstance().getUserId(), multipartAvt).enqueue(new Callback<UserResponsive>() {
            @Override
            public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                UserResponsive user = response.body();
                if(user!=null){
                    Glide.with(context).load(user.getData().getAvatar_thumbnail()).into(selectImg);
                }
            }

            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                Toast.makeText(context,"Call api fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void update(String name, String phoneNumber, String address, String password) {
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setAddress(address);
        user.setPassword(password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        Call<UserResponsive> call = api.updateUser(name,phoneNumber,address,password,UserManager.getInstance().getUserId());
        call.enqueue(new Callback<UserResponsive>() {
            @Override
            public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if (response.isSuccessful()) {
                    UserResponsive userResponsive = response.body();
                    if (userResponsive != null && userResponsive.isSuccess()) {
                        status.setValue(true);
                    } else {
                        status.setValue(false);
                    }
                } else {
                    status.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                status.setValue(false);
                Log.e("User update", "API call failed: " + t.getMessage());
            }
        });
    }
}
