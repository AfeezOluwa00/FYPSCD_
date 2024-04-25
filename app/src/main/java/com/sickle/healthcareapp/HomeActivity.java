package com.sickle.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.mysicklecellapp.R;
import com.google.android.material.navigation.NavigationView;
import com.sickle.healthcareapp.Common.Common;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sickle.healthcareapp.home.MedicineActivity;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
//    Button SignOutBtn;
  LinearLayout myDoctors,BtnRequst,profile,appointment,searchPatBtn,hospitals,medicines;
  ImageButton btnOpenDraweruser;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        appointment = findViewById(R.id.appointement);
        drawer = findViewById(R.id.drawerLayout);
        btnOpenDraweruser = findViewById(R.id.btnOpenDrawer);
medicines=findViewById(R.id.medicines);
medicines.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent k = new Intent(HomeActivity.this, MedicineActivity.class);
        startActivity(k);
    }
});
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        // Handle click event for the ImageButton
        btnOpenDraweruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manually open the drawer when the button is clicked
                drawer.openDrawer(GravityCompat.START);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(HomeActivity.this, PatientAppointementsActivity.class);
                startActivity(k);
            }
        });

        searchPatBtn = (LinearLayout) findViewById(R.id.search_doc);
        hospitals = (LinearLayout) findViewById(R.id.near_hospitals);
        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(HomeActivity.this, Hospitals_Activity.class);
                startActivity(k);
            }
        });
        searchPatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, SearchPatActivity.class);
                startActivity(k);
            }
        });

        myDoctors = (LinearLayout) findViewById(R.id.mydoctor);
        myDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, MyDoctorsAvtivity.class);
                startActivity(k);
            }
        });



        myDoctors = (LinearLayout) findViewById(R.id.mydoctor);
        myDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(HomeActivity.this, MyDoctorsAvtivity.class);
                startActivity(k);
            }
        });
        BtnRequst = findViewById(R.id.medical_folder);
        BtnRequst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicalFile.class);
                intent.putExtra("patient_email",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                startActivity(intent);
            }
        });

        LinearLayout riskCalBtn = findViewById(R.id.riskCalBtn);
        riskCalBtn.setOnClickListener(view->{
            Intent intent = new Intent(HomeActivity.this, RiskAssesmentActivity.class);
            startActivity(intent);
        });


        LinearLayout moodTrackerLayout = findViewById(R.id.MoodTracker);
        LinearLayout weatherLayout = findViewById(R.id.WeatherBtn);

        moodTrackerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity for Mood Tracker
                Intent intent = new Intent(HomeActivity.this, MyTrackers.class);
                startActivity(intent);
            }
        });


        weatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start a new activity for Weather
                Intent intent = new Intent(HomeActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });





        Common.CurrentUserid= FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        FirebaseFirestore.getInstance().collection("User").document(Common.CurrentUserid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Common.CurrentUserName = documentSnapshot.getString("name");
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rateus:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Common.CurrentPatientName = "";
                Common.CurrentPatientPhone = "";
                Common.CurrentUserType = "patient";
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.privacypolicy:
                //  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            // Add more navigation items as needed
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
