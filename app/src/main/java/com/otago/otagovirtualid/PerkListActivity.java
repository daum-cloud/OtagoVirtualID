package com.otago.otagovirtualid;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otago.otagovirtualid.utils.BottomNavigationHelper;

import java.util.ArrayList;

public class PerkListActivity extends AppCompatActivity {

    ArrayList<Perk> perks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_perks);

        setupBottomNavigationView();

        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvPerks);

        // Initialize contacts
        perks = Perk.createPerkList(20);
        // Create adapter passing in the sample user data
        StudentPerksAdapter adapter = new StudentPerksAdapter(perks);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!


    }

    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        BottomNavigationHelper.enableNavigation(PerkListActivity.this, bottomNavigationView);
    }


}
