package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormActivity extends AppCompatActivity {

    public Button cancelButton, sendButton;
    public EditText subject, msg;
    public String[] addresses = {"otagovirtualid@gmail.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitform);

        cancelButton = findViewById(R.id.cancelButton);
        sendButton = findViewById(R.id.submitButton);
        subject = findViewById(R.id.editTextEmailSubject);
        msg = findViewById(R.id.editTextMessageBox);


        //When user clicks help cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AskOtagoActivity.class));
            }
        });

        //When user clicks send button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:otagovirtualid@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString().trim());
                intent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString().trim());
                startActivity(intent);
                System.out.println(subject.getText().toString());*/

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                //intent.setType("message/rfc822");
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses); //For some reason EXTRA_EMAIL only takes string arrays
                intent.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                //startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });
    }
}