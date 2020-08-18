package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class UploadImageActivity extends AppCompatActivity {

    ImageView selectedImg;
    Button galleryBtn, cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        selectedImg = findViewById(R.id.uploadedImage);
        galleryBtn = findViewById(R.id.galleryBtn);
        cameraBtn = findViewById(R.id.cameraBtn);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadImageActivity.this, "Camera button responding", Toast.LENGTH_SHORT).show();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadImageActivity.this, "Gallery button responding", Toast.LENGTH_SHORT).show();
            }
        });

    }
}