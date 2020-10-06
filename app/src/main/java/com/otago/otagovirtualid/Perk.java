package com.otago.otagovirtualid;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**Perks Class;
 *storing temporary values in an array
 *for temporary demonstration. Actual
 *real-time data to be implemented via
 *Firebase.
 */

public class Perk {

    private String pName;
    private boolean pActive;

    public Perk(String name, boolean active) {
        pName = name;
        pActive = active;
    }

    public String getName() {
        return pName;
    }

    public boolean isOnline() {
        return pActive;
    }



}
