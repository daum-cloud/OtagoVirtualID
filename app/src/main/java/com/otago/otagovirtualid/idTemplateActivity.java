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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class idTemplateActivity extends AppCompatActivity {

    //Get a Realtime Database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Get the instance
    DatabaseReference ref = database.getReference();
    //Currently set to user for the example process
    DatabaseReference userref = ref.child("user");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_template);

        final TextView txtUsername = findViewById(R.id.txtUsername);
        final TextView txtEmail = findViewById(R.id.txtEmail);
        final TextView txtIDNo = findViewById(R.id.txtIDNo);
        final TextView txtName = findViewById(R.id.txtName);
        final TextView txtStudent = findViewById(R.id.txtStudent); //Not implemented just yet - possible for future releases
        final TextView txtDate = findViewById(R.id.txtDate);


        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtUsername.setText(snapshot.child("studentCode").getValue(String.class));
                txtEmail.setText(snapshot.child("email").getValue(String.class));
                txtIDNo.setText(snapshot.child("studentID").getValue(String.class));
                txtName.setText(snapshot.child("firstName").getValue(String.class) + " " + snapshot.child("lastName").getValue(String.class));
                //txtStudent.setText(snapshot.child("studentCode").getValue(String.class)); //See above comment
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String strDate = dateFormat.format(date);
                txtDate.setText(strDate);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(idTemplateActivity.this, "Error with retrieving ID. Contact System Admin IN DATABASE", Toast.LENGTH_LONG).show();
            }
        });









    }
}