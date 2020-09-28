package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**Ask Otago
 *Activity to display further information and links for the user to follow
 *Otago University and for further contact and inquiries.
 */

public class AskOtagoActivity extends AppCompatActivity{

    Button submitForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askhelp);

        submitForm = findViewById(R.id.submitForm);


        //Links that lead user to browser
        TextView fb = (TextView) findViewById(R.id.facebookText);
        fb.setMovementMethod(LinkMovementMethod.getInstance());

        TextView ln = (TextView) findViewById(R.id.linkedInText);
        ln.setMovementMethod(LinkMovementMethod.getInstance());

        TextView insta = (TextView) findViewById(R.id.instagramText);
        insta.setMovementMethod(LinkMovementMethod.getInstance());

        TextView twtr = (TextView) findViewById(R.id.twitterText);
        twtr.setMovementMethod(LinkMovementMethod.getInstance());

        TextView yt = (TextView) findViewById(R.id.youtubeText);
        yt.setMovementMethod(LinkMovementMethod.getInstance());

        //When user clicks help form button
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FormActivity.class));
            }
        });




    }
}
