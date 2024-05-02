package com.example.foodorderapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.foodorderapp.R;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.UserDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.viewmodal.UpdateUserViewModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserUpdateActivity extends AppCompatActivity {
    private EditText editName, editSdt, editPass, editAddress;
    private Button btnSave;
    private ImageView editImage, backMainActivity;
    private Uri uri;
    private static String dataFile;
    private UpdateUserViewModel updateViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
        updateViewModel = new ViewModelProvider(this).get(UpdateUserViewModel.class);

        int userId = UserManager.getInstance().getUserId();
        fetchUserInfo(userId);
        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
            }
        });

        updateViewModel.getStatus().observe(this, new Observer<Boolean>() {
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
                if (uri != null) {
                    updateViewModel.callApiUpdateAvatar(UserUpdateActivity.this, editImage);
                }
                String name = editName.getText().toString();
                String phoneNumber = editSdt.getText().toString();
                String password = editPass.getText().toString();
                String address = editAddress.getText().toString();
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

    private void init() {
        editName = findViewById(R.id.updateTextName);
        editSdt = findViewById(R.id.updateSDT);
        editPass = findViewById(R.id.updateMatKhau);
        editAddress = findViewById(R.id.updateAddress);
        btnSave = findViewById(R.id.buttonSaveUpdate);
        editImage = findViewById(R.id.updateImage);
        backMainActivity = findViewById(R.id.backMainActivity);
    }

    private void displaySelectedImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            editImage.setImageBitmap(bitmap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            Uri selectedImageUri = result.getData().getData();
                            displaySelectedImage(selectedImageUri);
                        }
                    }
                }
            }
    );
    private void fetchUserInfo(int userId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponsive> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<UserResponsive>() {
            @Override
           public void onResponse(Call<UserResponsive> call, Response<UserResponsive> response) {
                if(response.isSuccessful()) {
                    UserDTO userData = response.body().getData();
                    if(userData != null) {
                        editName.setText(userData.getName());
                        editAddress.setText(userData.getAddress());
                        editSdt.setText(userData.getPhoneNumber());
                        editPass.setText(userData.getPassword());
                        String avatarUser = userData.getAvatar_thumbnail();
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
                Toast.makeText(UserUpdateActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
