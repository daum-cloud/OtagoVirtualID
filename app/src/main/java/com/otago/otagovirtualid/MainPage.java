package com.otago.otagovirtualid;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth fAuth;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        bottomNavigationView.setSelectedItemId(R.id.navigation_id);

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
            Toast.makeText(MainPage.this, "Please sign in first" ,Toast.LENGTH_LONG).show();
        }
    }

    //Called when user clicks perks button
    public void perkButton(View view) {
        Intent intent = new Intent(this, PerkListActivity.class);
        startActivity(intent);
    }

    //Called when user clicks Upload Image
    public void uploadImage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, UploadImageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }


    //Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        //Takes user to upload image activity
        switch (menuItem.getItemId()) {
            case R.id.navigation_uploadImage:
                Intent intent = new Intent(this, UploadImageActivity.class);
                startActivity(intent);
        }

        //Takes user to perks activity

        switch (menuItem.getItemId()) {
            case R.id.navigation_perks:
                Intent intent = new Intent(this, PerkListActivity.class);
                startActivity(intent);

        }

        //Takes user to askOtago activity
        switch (menuItem.getItemId()) {
            case R.id.navigation_help:
                Intent intent = new Intent(this, AskOtagoActivity.class);
                startActivity(intent);

        }
        return false;
    }
}
