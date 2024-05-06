package com.example.foodorderapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.RealPathUtil;
import com.example.foodorderapp.UserManager;
import android.Manifest;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.viewmodal.UpdateUserViewModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

public class UserUpdateActivity extends AppCompatActivity {
    private static final int My_Request_Code = 1;
    private TextInputEditText editName, editSdt, editPass, editAddress;
    private Button btnSave, btnSelectImage;
    private ShapeableImageView editImage;
    private ImageView backMainActivity;
    private UpdateUserViewModel updateViewModel;
    private Uri mUri;
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            editImage.setImageBitmap(bitmap);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
        updateViewModel = new ViewModelProvider(this).get(UpdateUserViewModel.class);

        int userId = UserManager.getInstance().getUserId();
        fetchUserInfo(userId);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        updateViewModel.getUpdateUserStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(UserUpdateActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserUpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle failed sign-up (error message should already be set in ViewModel)
                    Toast.makeText(UserUpdateActivity.this, "Update thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        updateViewModel.getUploadAvtStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(UserUpdateActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserUpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Handle failed sign-up (error message should already be set in ViewModel)
                    Toast.makeText(UserUpdateActivity.this, "Update thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri != null) {
                       String realPath = RealPathUtil.getRealPath(UserUpdateActivity.this, mUri);
                       File file = new File(realPath);
                       updateViewModel.uploadImage(userId, file);
                } else {
                    Toast.makeText(UserUpdateActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
               String name = editName.getText().toString();
               String phoneNumber = editSdt.getText().toString();
                String password = editPass.getText().toString();String address = editAddress.getText().toString();
                updateViewModel.update(name, phoneNumber, address, password);
            }
        });
        backMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserUpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Nếu quyền chưa được cấp, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    My_Request_Code);
        } else {
            // Nếu quyền đã được cấp, thực hiện hành động tương ứng, ví dụ: chọn ảnh
            openGallery();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == My_Request_Code) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
               Toast.makeText(this, "dennied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivityResultLauncher.launch(intent);
    }


    private void init() {
      editName = findViewById(R.id.updateTextName);
       editSdt = findViewById(R.id.updateSDT);
        editPass = findViewById(R.id.updatePassword);
        editAddress = findViewById(R.id.updateAddress);
        btnSave = findViewById(R.id.buttonSaveUpdate);
        editImage = findViewById(R.id.updateImage);
        btnSelectImage = findViewById(R.id.buttonSelectImage);
        backMainActivity = findViewById(R.id.backMainActivity);
    }



    private void fetchUserInfo(int userId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponsive> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<UserResponsive>() {
            @Override
           public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if(response.isSuccessful()) {
                    UserResponsive userData = response.body();
                    if(userData != null) {
                       editName.setText(userData.getData().getName());
                       editSdt.setText(userData.getData().getPhoneNumber());
                       editAddress.setText(userData.getData().getAddress());
                       editPass.setText(userData.getData().getPassword());
                        String avatarUser = userData.getData().getAvatar_thumbnail();
                        Picasso.get().load(avatarUser).into(editImage, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Picasso", "Hình ảnh đã được tải thành công.");
                            }
                            @Override
                            public void onError(Exception e) {
                                Log.e("Picasso", "Lỗi khi tải hình ảnh: " + e.getMessage());
                            }
                        });
                        Log.d("Màn Home", "ảnh đại diện: " + avatarUser); // Log success message
                    }
                } else {
                    Toast.makeText(UserUpdateActivity.this, "Failed to fetch user info", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserResponsive> call, Throwable t) {
                if (UserUpdateActivity.this != null) {
                    Toast.makeText(UserUpdateActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
