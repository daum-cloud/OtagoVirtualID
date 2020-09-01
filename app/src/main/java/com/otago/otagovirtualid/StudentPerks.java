package com.otago.otagovirtualid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.otago.otagovirtualid.databinding.ActivityStudentPerksBinding;

public class StudentPerks extends AppCompatActivity {

    private ActivityStudentPerksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_student_perks);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_student_perks);
        setSupportActionBar(binding.toolbar);
    }

}
