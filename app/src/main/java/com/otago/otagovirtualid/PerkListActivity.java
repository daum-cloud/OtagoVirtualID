package com.otago.otagovirtualid;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**Perks Activity class
 *Creation of the perks activity window as well as
 *initiating the elements within.
 */

public class PerkListActivity extends AppCompatActivity {

    ArrayList<Perk> perks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_perks);

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
}
