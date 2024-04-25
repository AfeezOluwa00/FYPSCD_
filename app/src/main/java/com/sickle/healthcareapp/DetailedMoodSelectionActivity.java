package com.sickle.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mysicklecellapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailedMoodSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_mood_selection);

        RadioGroup radioGroupEmotion = findViewById(R.id.radioGroupEmotion);
        Spinner spinnerIntensity = findViewById(R.id.spinnerIntensity);
        EditText editTextNote = findViewById(R.id.editTextNote);
        Button btnLog = findViewById(R.id.btnLog);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedEmotion = ((RadioButton) findViewById(radioGroupEmotion.getCheckedRadioButtonId())).getText().toString();
                String selectedIntensity = spinnerIntensity.getSelectedItem().toString();
                String note = editTextNote.getText().toString();

                String logMessage = "Emotion: " + selectedEmotion + "\nIntensity: " + selectedIntensity + "\nNote: " + note + "\nDate: " + currentDate;
                Toast.makeText(DetailedMoodSelectionActivity.this, logMessage, Toast.LENGTH_LONG).show();
            }
        });
    }
}
