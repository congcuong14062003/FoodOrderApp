package com.example.foodorderapp.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;
import com.example.foodorderapp.RealPathUtil;
import com.example.foodorderapp.UserManager;
import com.example.foodorderapp.object.UserDTO;
import com.example.foodorderapp.retrofit.ApiService;
import com.example.foodorderapp.retrofit.UserResponsive;
import com.example.foodorderapp.viewmodal.UpdateUserViewModel;

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

public class UserUpdateFragment extends Fragment {
    private EditText editName, editSdt, editPass, editAddress;
    private Button btnSave;
    private ImageView selectImg;
    private Uri uri;
    private static String dataFile;
    private UpdateUserViewModel updateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        init(rootView);
        updateViewModel = new ViewModelProvider(requireActivity()).get(UpdateUserViewModel.class);

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    updateViewModel.callApiUpdateAvatar(requireContext(), selectImg);
                    String name = editName.getText().toString();
                    String phoneNumber = editSdt.getText().toString();
                    String password = editPass.getText().toString();
                    String address = editAddress.getText().toString();
                    updateViewModel.update(name, phoneNumber, address, password);
                }
            }
        });

        return rootView;
    }

    private void init(View rootView) {
        editName = rootView.findViewById(R.id.editTextName);
        editSdt = rootView.findViewById(R.id.editSDT);
        editPass = rootView.findViewById(R.id.editPassword);
        editAddress = rootView.findViewById(R.id.editAddress);
        btnSave = rootView.findViewById(R.id.buttonSaveUpdate);
        selectImg = rootView.findViewById(R.id.selectImage);
    }
    private void displaySelectedImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
            selectImg.setImageBitmap(bitmap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            Uri selectedImageUri = result.getData().getData();
                            displaySelectedImage(selectedImageUri);
                        }
                    }
                }
            }
    );


}
