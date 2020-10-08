package com.otago.otagovirtualid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.otago.otagovirtualid.utils.BottomNavigationHelper;

import java.util.ArrayList;

/**
 *Perks Activity class
 *Creation of the perks activity window as well as
 *initiating the elements within.
 */

public class PerkListActivity extends AppCompatActivity {

    //Number used as a counter which corresponds to an activity (for navigation purposes)
    private static int ActivityNum = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        setContentView(R.layout.activity_student_perks);

        //Calling navigation method
        setupBottomNavigationView();

        // Lookup the recyclerview in activity layout
        final RecyclerView rvPerks = (RecyclerView) findViewById(R.id.rvPerks);

        final ArrayList<Perk> perks = new ArrayList<Perk>();
        //Reference to database
        //Get a Realtime Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Get the instance
        DatabaseReference ref = database.getReference();
        //Set reference to the users section
        DatabaseReference perksref = ref.child("perks");

        //Get the Perks from Firebase
        perksref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    //Add perk to ArrayList
                    perks.add(new Perk(child.child("title").getValue(String.class) + " \n"  + child.child("description").getValue(String.class), true));
                    // Create adapter passing in the data
                    StudentPerksAdapter adapter = new StudentPerksAdapter(perks);
                    // Attach the adapter to the recyclerview to populate items
                    rvPerks.setAdapter(adapter);
                    // Set layout manager to position the items
                    rvPerks.setLayoutManager(new LinearLayoutManager(PerkListActivity.this));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /**
     * Setting up bottom navigation in Perks
     */
    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(PerkListActivity.this, bottomNavigationView);

        // This ensures that the correct navigation icon is highlighted for each activity.
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum); // using ActivityNum to reference the activity we are referring to
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
    }

}
