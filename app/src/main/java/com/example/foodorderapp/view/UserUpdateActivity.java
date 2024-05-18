package com.example.foodorderapp.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.foodorderapp.R;
import com.example.foodorderapp.RealPathUtil;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.viewmodal.UpdateUserViewModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
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
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private TextInputEditText editName, editSdt, editPass, editAddress;
    private Button btnSave, btnSelectImage;
    private ImageView editImage;
    private ImageView backUser;
    private UpdateUserViewModel updateViewModel;
    private ImageView updateImage;
    private Button buttonSelectImage, buttonSaveUpdate;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        init();
        updateViewModel = new ViewModelProvider(this).get(UpdateUserViewModel.class);

        int userId = UserManager.getInstance().getUserId();
        fetchUserInfo(userId);

        updateImage = findViewById(R.id.updateImage);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonSaveUpdate = findViewById(R.id.buttonSaveUpdate);
        backUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        buttonSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload image to server and update user info
                if (imageUri != null) {
                    // Do your API call to update user with imageUri
                    // Then reset the imageUri to null
                    String realPath = RealPathUtil.getRealPath(UserUpdateActivity.this, imageUri);
                    File file = new File(realPath);
                    updateViewModel.uploadImage(userId, file);
                    String name = editName.getText().toString();
                    String phoneNumber = editSdt.getText().toString();
                    String password = editPass.getText().toString();
                    String address = editAddress.getText().toString();
                    updateViewModel.update(name, phoneNumber, address, password);
                    imageUri = null;
                } else {
                    // No image selected, handle accordingly
                    Toast.makeText(UserUpdateActivity.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
                updateViewModel.getUpdateUserStatus().observe(UserUpdateActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean postOrderStatus) {
                        if (postOrderStatus) {
                            Toast.makeText(UserUpdateActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UserUpdateActivity.this, MainActivity.class);
                            intent.putExtra("fragment", "profile");
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(UserUpdateActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }



    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (ContextCompat.checkSelfPermission(UserUpdateActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(UserUpdateActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                    } else {
                        dispatchTakePictureIntent();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_IMAGE_PICK);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                if (data != null && data.getData() != null) {
                    imageUri = data.getData();
                    updateImage.setImageURI(imageUri);
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageUri = getImageUri(this, imageBitmap);
                    updateImage.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    private Uri getImageUri(UserUpdateActivity userUpdateActivity, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(userUpdateActivity.getContentResolver(), imageBitmap, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void init() {
        editName = findViewById(R.id.updateTextName);
        editSdt = findViewById(R.id.updateSDT);
        editPass = findViewById(R.id.updatePassword);
        editAddress = findViewById(R.id.updateAddress);
        btnSave = findViewById(R.id.buttonSaveUpdate);
        editImage = findViewById(R.id.updateImage);
        btnSelectImage = findViewById(R.id.buttonSelectImage);
        backUser = findViewById(R.id.backUser);
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
                        editSdt.setText(userData.getData().getPhone_number());
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

    @Override
    public void onBackPressed() {
        // Kiểm tra nếu có Fragment trong Back Stack của Activity
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            // Nếu có, quay lại Fragment trước đó
            getSupportFragmentManager().popBackStack();
        } else {
            // Nếu không, thực hiện hành động mặc định (quay lại Activity trước đó)
            super.onBackPressed();
        }
    }


}
