package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mysicklecellapp.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Create a layout file for your splash screen if not already done

        // Use a handler to delay the start of the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash delay
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Close the splash activity so that the user cannot go back to it
            }
        }, SPLASH_DELAY);
    }
}
