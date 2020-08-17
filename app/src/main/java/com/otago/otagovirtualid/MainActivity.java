package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fAuth;

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

    public void idTemplate(View view){
        //Do something in response to button

        //Checking that the user is authenticated
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, idTemplateActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "ERROR! Please sign in first " ,Toast.LENGTH_LONG).show();
        }


    }
}