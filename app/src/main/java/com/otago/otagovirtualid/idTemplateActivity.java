package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class idTemplateActivity extends AppCompatActivity {

    //Get a reference to the User
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("/otago-virtual-id/user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_template);

        User user = null;

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(idTemplateActivity.this, "Failed to read database. Contact System Admin", Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtViewID = findViewById(R.id.txtName);

        if(user != null) {
            txtViewID.setText(user.getFirstName() + " " + user.getLastName());
        } else {
            Toast.makeText(idTemplateActivity.this, "Error with retrieving ID. Contact System Admin", Toast.LENGTH_LONG).show();
        }




    }
}