package com.otago.otagovirtualid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.otago.otagovirtualid.utils.BottomNavigationHelper;

/**Main Page Class
 *User is redirected to this page activity once login has been approved
 *from the "MainAcitivity" class.
 */

public class MainPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    BottomNavigationView bottomNavigationView;
    private static int ActivityNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        //Calling method
        setupBottomNavigationView();

    }

    /** Setting up bottom navigation in Main page
     */
    public void setupBottomNavigationView(){

        //Instance of bottom navigation view
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(MainPage.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum);
        menuItem.setChecked(true);
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


}
