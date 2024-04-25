package com.sickle.healthcareapp.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sickle.healthcareapp.Interface.ItemClickListener;
import com.sickle.healthcareapp.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MedicineActivity extends AppCompatActivity {
    public static ArrayList<TimeItem> timeItems = new ArrayList<>();

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private View root;

    DatabaseHelper databaseHelper;

    ItemClickListener itemClickListener;

    private List<HomeItem> homeItems;
    private static final int MY_PERMISSIONS_REQUEST_SCHEDULE_EXACT_ALARM = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_medicine);
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM},
                    MY_PERMISSIONS_REQUEST_SCHEDULE_EXACT_ALARM);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SCHEDULE_EXACT_ALARM}, 100);
        } else {
            // Permission is already granted
            // Continue with your alarm setup
        }

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        recyclerView = findViewById(R.id.recycler_view_medicine);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        homeItems = new ArrayList<>();

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(int position) {
                // Handle item click if needed
            }
        };

        loadMedicines();

        ImageView fabAddMedicine = findViewById(R.id.fab_add_medicine);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddDialog addMedicineDialog = new AddDialog(MedicineActivity.this);
                addMedicineDialog.show(getSupportFragmentManager(), "Add_Dialog");
                // Handle fab click if needed
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SCHEDULE_EXACT_ALARM) {
            // Check if the permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now set exact alarms
            } else {
                // Permission denied, handle accordingly
            }
        }
    }

    public void loadMedicines() {
        databaseHelper = new DatabaseHelper(this);
        homeItems = databaseHelper.getMedicineList();

        adapter = new HomeAdapter(homeItems, this, this, itemClickListener);
        recyclerView.setAdapter(adapter);
    }
}
