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
}