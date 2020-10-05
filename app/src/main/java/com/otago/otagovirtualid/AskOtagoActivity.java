package com.otago.otagovirtualid;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otago.otagovirtualid.utils.BottomNavigationHelper;

/**Ask Otago
 *Activity to display further information and links for the user to follow
 *Otago University and for further contact and inquiries.
 */


public class AskOtagoActivity extends AppCompatActivity{

    Button submitForm;
    //Number used as a counter which corresponds to an activity (for navigation purposes)
    private static int ActivityNum = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //try catch removing the header banner
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Intent intent = getIntent();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askhelp);

        //Calling method
        setupBottomNavigationView();

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

    /** Setting up bottom navigation in Ask Otago
     */
    public void setupBottomNavigationView(){

        //Navigation instance
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        //Referencing method in Utils/BottomNavigationHelper which takes the user to the corresponding activity.
        BottomNavigationHelper.enableNavigation(AskOtagoActivity.this, bottomNavigationView);

        // This ensures that the correct navigation icon is highlighted for each activity.
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ActivityNum); // using ActivityNum to reference the activity we are referring to
        menuItem.setChecked(true);
    }

    @Override
    public void onBackPressed() {
    }

}
