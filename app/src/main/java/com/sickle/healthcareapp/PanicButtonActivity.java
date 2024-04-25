package com.sickle.healthcareapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mysicklecellapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PanicButtonActivity extends AppCompatActivity {

    private static final int REQUEST_PHONE_CALL = 1;
    private EditText contactNameEditText, contactNumberEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_button);

        db = FirebaseFirestore.getInstance();
        contactNameEditText = findViewById(R.id.contact_name_edit_text);
        contactNumberEditText = findViewById(R.id.contact_number_edit_text);

        Button saveContactButton = findViewById(R.id.save_contact_button);
        saveContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContactToFirestore();
            }
        });

        Button panicButton = findViewById(R.id.panic_button);
        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

    private void saveContactToFirestore() {
        String contactName = contactNameEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();

        if (!contactName.isEmpty() && !contactNumber.isEmpty()) {
            Map<String, Object> contact = new HashMap<>();
            contact.put("name", contactName);
            contact.put("number", contactNumber);

            db.collection("emergencyContacts")
                    .add(contact)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(PanicButtonActivity.this, "Contact saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PanicButtonActivity.this, "Failed to save contact", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter contact name and number", Toast.LENGTH_SHORT).show();
        }
    }

    private void makePhoneCall() {
        String contactNumber = contactNumberEditText.getText().toString().trim();
        if (!contactNumber.isEmpty()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                String dial = "tel:" + contactNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            }
        } else {
            Toast.makeText(this, "Please save a contact first", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission denied to make phone call", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
