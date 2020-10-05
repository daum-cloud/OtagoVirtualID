package com.otago.otagovirtualid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**Main Activity
 *The 'Main Activity' or window of interaction with the user
 *upon application launch, prompting them to log in where they
 *they will be redirected to "Main Page".
 */

public class MainActivity extends AppCompatActivity {
    EditText mUsername, mPassword;
    Button mLoginBtn;
    FirebaseAuth fAuth;
    TextView mForgetPW;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mPreferences = getSharedPreferences("com.otago.otagovirtualid", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        mUsername = findViewById(R.id.txtUsername);
        mPassword = findViewById(R.id.txtPassword);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.btnLogin);
        mForgetPW = findViewById(R.id.txtForgotPW);

        checkSharedPreferences();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mUsername.getText().toString();
                mEditor.putString(getString(R.string.name), username);
                mEditor.commit();

                String password = mPassword.getText().toString();
                mEditor.putString(getString(R.string.password), password);
                mEditor.commit();

                // data validation

                if (TextUtils.isEmpty(username)) {
                    mUsername.setError("Username is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;

                }

                //authenticate the user - correct username and password to login

                fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), idTemplateActivity.class)); //if login is successful it will take user to main activity

                        } else {
                            Toast.makeText(MainActivity.this, "Error ! Please use your E-Vision Credentials to Login", Toast.LENGTH_SHORT).show();


                        }

                    }
                });

            }
        });

        mForgetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email to Receive Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog

                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }

    private void checkSharedPreferences(){
        String name = mPreferences.getString(getString(R.string.name), "");
        String password = mPreferences.getString(getString(R.string.password), "");

        mUsername.setText(name);
        mPassword.setText(password);
    }

}