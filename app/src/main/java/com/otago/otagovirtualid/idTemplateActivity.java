package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class idTemplateActivity extends AppCompatActivity {

    //Get a Realtime Database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Get the instance
    DatabaseReference ref = database.getReference();
    //Currently set to user for the example process
    DatabaseReference usersref = ref.child("users");

    //For QR code:
    Bitmap bitmap;
    QRGEncoder qrgEncoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_template);

        //Setting the values
        final TextView txtUsername = findViewById(R.id.txtUsername);
        final TextView txtEmail = findViewById(R.id.txtEmail);
        final TextView txtIDNo = findViewById(R.id.txtIDNo);
        final TextView txtName = findViewById(R.id.txtName);
        final TextView txtStudent = findViewById(R.id.txtStudent); //Not implemented just yet - possible for future releases
        final TextView txtDate = findViewById(R.id.txtDate);
        final ImageView imageIDPhoto = findViewById(R.id.imgID);
        final ImageView qrCode = findViewById(R.id.qr);

        //Get current logged in user from the database:
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Get the child user for our specific user
        DatabaseReference userref = usersref.child(currentFirebaseUser.getUid());


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
                //Gets image from the link (going to the Cloud storage) and displays it.
                Glide.with(idTemplateActivity.this).load(snapshot.child("uRLCurrPhoto").getValue()).into(imageIDPhoto);
                //Setting the QR code
                qrgEncoder = new QRGEncoder(
                        snapshot.child("studentCode").getValue(String.class), null,
                        QRGContents.Type.TEXT,
                        130);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrCode.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(idTemplateActivity.this, "Error with retrieving ID. Contact System Admin IN DATABASE", Toast.LENGTH_LONG).show();
            }
        });










    }
}