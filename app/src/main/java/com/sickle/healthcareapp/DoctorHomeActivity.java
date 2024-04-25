package com.sickle.healthcareapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mysicklecellapp.R;
import com.google.android.material.navigation.NavigationView;
import com.sickle.healthcareapp.Common.Common;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DoctorHomeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, NavigationView.OnNavigationItemSelectedListener{
    static String doc;
    LinearLayout SignOutBtn2;
    LinearLayout BtnRequst;
    LinearLayout listPatients;
    LinearLayout appointementBtn;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private ImageButton btnOpenDrawer;

    @OnClick(R.id.profile)
    void profileBtnClick(){
        Intent k = new Intent(DoctorHomeActivity.this, ProfileDoctorActivity.class);
        startActivity(k);
    }
    Unbinder unbinder;

    @OnClick(R.id.myCalendarBtn)
    void myCalendarOnclick() {
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show();
        Intent k = new Intent(DoctorHomeActivity.this, MyCalendarDoctorActivity.class);
        startActivity(k);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home); //ici layout de page d'acceuil MEDECIN
        unbinder = ButterKnife.bind(this,this);
        Common.CurreentDoctor = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Common.CurrentUserType = "doctor";
        btnOpenDrawer = findViewById(R.id.btnOpenDrawer);

        listPatients = findViewById(R.id.listPatients);
        BtnRequst=findViewById(R.id.btnRequst);
        appointementBtn = findViewById(R.id.appointement);
        drawer = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        // Handle click event for the ImageButton
        btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manually open the drawer when the button is clicked
                drawer.openDrawer(GravityCompat.START);
            }
        });

        BtnRequst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(DoctorHomeActivity.this, ConfirmedAppointmensActivity.class);
                startActivity(k);
            }
        });
        listPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(DoctorHomeActivity.this, MyPatientsActivity.class);
                startActivity(k);
            }
        });
        appointementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // doc = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
                //showDatePickerDialog(v.getContext());
                Intent k = new Intent(DoctorHomeActivity.this, DoctorAppointementActivity.class);
                startActivity(k);
            }
        });

    }

    public void showDatePickerDialog(Context wf){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                wf,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = "month_day_year: " + month + "_" + dayOfMonth + "_" + year;
        openPage(view.getContext(),doc,date);
    }

    private void openPage(Context wf, String d,String day){
        Intent i = new Intent(wf, AppointementActivity.class);
        i.putExtra("key1",d+"");
        i.putExtra("key2",day);
        i.putExtra("key3","doctor");
        wf.startActivity(i);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rateus:
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);                break;
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
