package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Called when user clicks login
    public void loginToApp(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }

    //Called when user clicks askOtago
    public void askOtago(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, AskOtagoActivity.class);
        startActivity(intent);
    }

    //Called when user clicks Upload Image
    public void uploadImage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, UploadImageActivity.class);
        startActivity(intent);
    }
}