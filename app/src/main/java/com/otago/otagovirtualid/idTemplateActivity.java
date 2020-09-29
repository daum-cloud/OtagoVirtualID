package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
import com.otago.otagovirtualid.utils.BottomNavigationHelper;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**Template Display
 *Activity for displaying the ID in a certain layout for the
 *"Main Page" activity once the user has successfully logged in.
 */

public class idTemplateActivity extends AppCompatActivity {

    //Get a Realtime Database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Get the instance
    DatabaseReference ref = database.getReference();
    //Set reference to the users section
    DatabaseReference usersref = ref.child("users");

    private static int ActivityNum = 1;

    //For QR code:
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter!=null && nfcAdapter.isEnabled()){
            Toast.makeText(this, "NFC available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "NFC not turned on", Toast.LENGTH_SHORT).show();
        }

        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_id_template);

        //call navigation method
        setupBottomNavigationView();




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


    /** Setting up bottom navigation in ID Template
     */
    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(idTemplateActivity.this, bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum);
        menuItem.setChecked(true);
    }
}