package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.otago.otagovirtualid.utils.BottomNavigationHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**Uploading a new profile photo
 *Activity to provide the user the ability to update their ID
 *profile image as well as requesting for their permission to
 *use their devices' camera. The new image is uploaded to the
 *real-time database which is then to be inspected by AskOtago
 *staff via a web portal.
 */

public class UploadImageActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 103;
    ImageView selectedImg;
    Button galleryBtn, cameraBtn, submitBtn;
    String currentPhotoPath;
    File file;
    public Uri imageUri;
    private StorageReference mstorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_upload_image);

        //Calling navigation method
        setupBottomNavigationView();

        selectedImg = findViewById(R.id.selectedImage);
        galleryBtn = findViewById(R.id.galleryBtn);
        cameraBtn = findViewById(R.id.cameraBtn);
        submitBtn = findViewById(R.id.submitBtn);

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        mstorageRef = FirebaseStorage.getInstance().getReference("id" + yearInString);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent();
                photoPickerIntent.setType("image/*");
                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST_CODE);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FireBaseFileUploader();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

    }

    /** Setting up bottom navigation in Upload Image
     */
    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(UploadImageActivity.this, bottomNavigationView);
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void FireBaseFileUploader() {
        final StorageReference ref = mstorageRef.child(System.currentTimeMillis()+"."+getExtension(imageUri));
        //Create a reference to the database to put the name of the image into the user's details
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //Toast.makeText(UploadImageActivity.this, "Current user:" + currentFirebaseUser.getUid(), Toast.LENGTH_LONG).show();
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentFirebaseUser.getUid());

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(UploadImageActivity.this, "Image Upload Success - To be Checked by Staff", Toast.LENGTH_SHORT).show();
                        //We want the URL of the image uploaded so we can save it under user's profile:

                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                           @Override
                                                                           public void onSuccess(Uri uri) {
                                                                               Uri downloadUrl = uri;
                                                                               dbRef.child("uRLCurrPhoto").setValue(downloadUrl.toString());
                                                                           }
                                                                       });



                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

   

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE); //Sends out a code
        } else {
            dispatchTakePictureIntent();
            //openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //openCamera();
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera Permision required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //private void openCamera() {
        //Toast.makeText(this, "Camera ready to go", Toast.LENGTH_SHORT).show();
      //  Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // startActivityForResult(camera, CAMERA_REQUEST_CODE);
    //}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            file = new File(currentPhotoPath);
            selectedImg.setImageURI(Uri.fromFile(file));
            imageUri = Uri.fromFile(file);
            submitBtn.setVisibility(View.VISIBLE);
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            selectedImg.setImageURI(imageUri);
            submitBtn.setVisibility(View.VISIBLE);
        }
    }



    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); //Could be issue with type imported
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.otago.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
}