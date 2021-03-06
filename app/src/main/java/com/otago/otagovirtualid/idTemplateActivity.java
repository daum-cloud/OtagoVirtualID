package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.HostApduService;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
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

/**
 *Template Display
 *Activity for displaying the ID in a certain layout for the
 *"Main Page" activity once the user has successfully logged in.
 */

public class idTemplateActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private ImageButton logout;
    //Get a Realtime Database
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //Get the instance
    DatabaseReference ref = database.getReference();
    //Set reference to the users section
    DatabaseReference usersref = ref.child("users");
    int PENDING_REQUEST_CODE = 0;

    //Number used as a counter which corresponds to an activity (for navigation purposes)
    private static int ActivityNum = 0;

    //For QR code:
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        /**
         * NFC functionality that has not been implemented fully due to time constraints. Will be
         * added later as a feature post deployment during the maintenance phase.
         */
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        //Temp removed - as was causing issues without NFC adapter
        //if (isFirstRun) {
        //        if (nfcAdapter == null && !nfcAdapter.isEnabled()) {
        //            Toast.makeText(this, "NFC not turned on", Toast.LENGTH_SHORT).show();
        //        }
        //}
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();

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
        logout = (ImageButton)findViewById(R.id.logoutBtn);

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

                //If ID is verified, show their ID
                if (snapshot.child("verified").getValue(String.class).equals("verified")) {
                    Glide.with(idTemplateActivity.this).load(snapshot.child("uRLCurrPhoto").getValue()).into(imageIDPhoto);
                    //Else - e.g. ID requires a photo to be taken (i.e. if they need to retake, or upload for first time).
                } else if (snapshot.child("verified").getValue(String.class).equals("false") || snapshot.child("verified").getValue(String.class).equals("retake")){
                    Glide.with(idTemplateActivity.this).load("https://firebasestorage.googleapis.com/v0/b/otago-virtual-id.appspot.com/o/systemImages%2FsubmitImage.png?alt=media&token=d6e8bd15-778c-47e4-a5c0-b49497b30fc0").into(imageIDPhoto);
                    // Other - if the ID hasn't been verified yet, but they've submitted an image.
                } else {
                    Glide.with(idTemplateActivity.this).load("https://firebasestorage.googleapis.com/v0/b/otago-virtual-id.appspot.com/o/systemImages%2FerrorImage.png?alt=media&token=2e86b7a6-5aef-4324-b995-6a9f7ab00e6e").into(imageIDPhoto);
                }
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

            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseError", error.getDetails());

            }

        });

        //When the user clicks the logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent loginScreen = new Intent(idTemplateActivity.this, MainActivity.class);
                (idTemplateActivity.this).finish();
                Toast.makeText(idTemplateActivity.this, "You have logged out", Toast.LENGTH_LONG).show();
                loginScreen.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(loginScreen);
            }
        });

    }

    /**
     * Setting up bottom navigation in ID Template
     */
    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(idTemplateActivity.this, bottomNavigationView);

        // This ensures that the correct navigation icon is highlighted for each activity.
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum); // using ActivityNum to reference the activity we are referring to
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
    }

}