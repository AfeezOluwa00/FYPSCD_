package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

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

    private ImageView fab_add_hydration;

    FireStoreDB fireStoreDB;
    private AllHydrationAdapter allHydrationAdapter;
    DatabaseHelper databaseHelper;
    private ArrayList<HydrationModel> hydrationModels;
    private RecyclerView all_hydration_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hydration);

        fireStoreDB = new FireStoreDB();

        all_hydration_list = findViewById(R.id.all_hydration_list);
        all_hydration_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyHydration.this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        all_hydration_list.setLayoutManager(linearLayoutManager);



        fab_add_hydration = findViewById(R.id.fab_add_hydration);
        fab_add_hydration.setOnClickListener(view->{
            AddHydrationDialog hydrationDialog = new AddHydrationDialog(MyHydration.this);
            hydrationDialog.show(getSupportFragmentManager(), "Add_Hyd_Dialog");

        });


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
        allHydrationAdapter = new AllHydrationAdapter(MyHydration.this);
        all_hydration_list.setAdapter(allHydrationAdapter);
        allHydrationAdapter.notifyDataSetChanged();
    }
}