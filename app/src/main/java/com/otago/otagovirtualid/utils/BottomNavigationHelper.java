package com.otago.otagovirtualid.utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otago.otagovirtualid.AskOtagoActivity;
import com.otago.otagovirtualid.MainActivity;
import com.otago.otagovirtualid.MainPage;
import com.otago.otagovirtualid.PerkListActivity;
import com.otago.otagovirtualid.R;
import com.otago.otagovirtualid.UploadImageActivity;
import com.otago.otagovirtualid.idTemplateActivity;

public class BottomNavigationHelper {
    
    public static void enableNavigation(final Context context, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        Intent intent1 = new Intent(context, MainPage.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.navigation_id:
                        Intent intent2 = new Intent (context, idTemplateActivity.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.navigation_perks:
                        Intent intent3 = new Intent(context, PerkListActivity.class);
                        context.startActivity(intent3);
                        break;

                    case R.id.navigation_uploadImage:
                        Intent intent4 = new Intent(context, UploadImageActivity.class);
                        context.startActivity(intent4);
                        break;

                    case R.id.navigation_help:
                        Intent intent5 = new Intent(context, AskOtagoActivity.class);
                        context.startActivity(intent5);
                        break;
                }


                return false;
            }
        });


    }


}
