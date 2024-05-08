package com.example.foodorderapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class TestCamActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_cam);

        imageView = findViewById(R.id.imageView);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    imageView.setImageBitmap(bitmap);
                    imageView.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }
}
