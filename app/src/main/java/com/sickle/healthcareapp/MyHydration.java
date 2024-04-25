package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mysicklecellapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.adapter.AllHydrationAdapter;
import com.sickle.healthcareapp.adapter.AllMoodsAdapter;
import com.sickle.healthcareapp.db.DatabaseHelper;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.home.AddHydrationDialog;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;

import java.util.ArrayList;

public class MyHydration extends AppCompatActivity {

    private ImageView fab_add_hydration,fab_visualization_hydration;

    FireStoreDB fireStoreDB;
    private AllHydrationAdapter allHydrationAdapter;
    DatabaseHelper databaseHelper;
    private ArrayList<HydrationModel> hydrationModels;
    private RecyclerView all_hydration_list;

    ProgressBar progressPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hydration);

        fireStoreDB = new FireStoreDB();

        progressPB = findViewById(R.id.progressPB);
        all_hydration_list = findViewById(R.id.all_hydration_list);
        all_hydration_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyHydration.this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        all_hydration_list.setLayoutManager(linearLayoutManager);



        fab_visualization_hydration = findViewById(R.id.fab_visualization_hydration);
        fab_visualization_hydration.setOnClickListener(view->{
            Intent intent = new Intent(MyHydration.this, MyHydrationVisualActivity.class);
            startActivity(intent);

        });

        fab_add_hydration = findViewById(R.id.fab_add_hydration);
        fab_add_hydration.setOnClickListener(view->{
            AddHydrationDialog hydrationDialog = new AddHydrationDialog(MyHydration.this);
            hydrationDialog.show(getSupportFragmentManager(), "Add_Hyd_Dialog");

        });

        progressPB.setVisibility(View.VISIBLE);
        fireStoreDB.getAllHydrationData(MyHydration.this,MyHydration.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());

    }



    public void loadAllHydrations() {

        databaseHelper = new DatabaseHelper(this);
        hydrationModels = databaseHelper.getAllHydrationList();

//        allHydrationAdapter = new AllHydrationAdapter(MyHydration.this,hydrationModels);
//        all_hydration_list.setAdapter(allHydrationAdapter);
    }

    public void setadapterForMedicinceData() {
        progressPB.setVisibility(View.GONE);
        allHydrationAdapter = new AllHydrationAdapter(MyHydration.this);
        all_hydration_list.setAdapter(allHydrationAdapter);
        allHydrationAdapter.notifyDataSetChanged();
    }
}