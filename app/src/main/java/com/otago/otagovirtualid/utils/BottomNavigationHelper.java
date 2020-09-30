package com.otago.otagovirtualid.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otago.otagovirtualid.AskOtagoActivity;
import com.otago.otagovirtualid.MainActivity;
//import com.otago.otagovirtualid.MainPage;
import com.otago.otagovirtualid.PerkListActivity;
import com.otago.otagovirtualid.R;
import com.otago.otagovirtualid.UploadImageActivity;
import com.otago.otagovirtualid.idTemplateActivity;


/** BottomNavigationHelper is called by each activity.
 * This facilitates the navigation between each activity. Each intent corresponds to an activity.
 * E.g. If the user selects the ID icon(the case) on the nav bar then the user will be taken to the ID activity.
 */
public class BottomNavigationHelper {
    
    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    //case R.id.navigation_home:
                        //Intent intentHome = new Intent(context, MainPage.class); //ActivityNum = 0
                        //context.startActivity(intentHome);
                        //break;

                    case R.id.navigation_id:
                        Intent intentID = new Intent (context, idTemplateActivity.class); //ActivityNum = 1
                        context.startActivity(intentID);
                        break;

                    case R.id.navigation_perks:
                        Intent intentPerks = new Intent(context, PerkListActivity.class); //ActivityNum = 2
                        context.startActivity(intentPerks);
                        break;

                    case R.id.navigation_uploadImage:
                        Intent intentImage = new Intent(context, UploadImageActivity.class); //ActivityNum = 3
                        context.startActivity(intentImage);
                        break;

                    case R.id.navigation_help:
                        Intent intentHelp = new Intent(context, AskOtagoActivity.class); //ActivityNum = 4
                        context.startActivity(intentHelp);
                        break;
                }


                return false;
            }
        });


    }


}
