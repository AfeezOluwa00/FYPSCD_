package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mysicklecellapp.R;

public class MyTrackers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trackers);

        LinearLayout myMood = findViewById(R.id.myMood);
        LinearLayout myPain = findViewById(R.id.myPain);
        LinearLayout myHydration = findViewById(R.id.myHydration);


        myMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity for Mood Tracker
                Intent intent = new Intent(MyTrackers.this, MyMoodActivity.class);
                startActivity(intent);
            }
        });

        myPain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity for Mood Tracker
                Intent intent = new Intent(MyTrackers.this, MyPainActivity.class);
                startActivity(intent);
            }
        });


        myHydration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity for Mood Tracker
                Intent intent = new Intent(MyTrackers.this, MyHydration.class);
                startActivity(intent);
            }
        });
    }
}