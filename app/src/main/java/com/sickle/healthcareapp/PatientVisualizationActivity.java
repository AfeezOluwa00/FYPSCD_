package com.sickle.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysicklecellapp.R;

public class PatientVisualizationActivity extends AppCompatActivity {

    private String patient_email;

    private LinearLayout patientMoodLL,patientPainLL,patientHydrationLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_visual);

        patient_email = getIntent().getStringExtra("patient_email");

        patientMoodLL = findViewById(R.id.patientMoodLL);
        patientMoodLL.setOnClickListener(view->{

            Intent intent = new Intent(PatientVisualizationActivity.this, PatientMoodVisualActivity.class);
            intent.putExtra("patient_email",patient_email);
            startActivity(intent);


        });


        patientPainLL = findViewById(R.id.patientPainLL);
        patientPainLL.setOnClickListener(view->{
            Intent intent = new Intent(PatientVisualizationActivity.this, PatientPainVisualActivity.class);
            intent.putExtra("patient_email",patient_email);
            startActivity(intent);
        });

        patientHydrationLL = findViewById(R.id.patientHydrationLL);
        patientHydrationLL.setOnClickListener(view->{
            Intent intent = new Intent(PatientVisualizationActivity.this, PatientHydrationVisualActivity.class);
            intent.putExtra("patient_email",patient_email);
            startActivity(intent);
        });


    }
}
