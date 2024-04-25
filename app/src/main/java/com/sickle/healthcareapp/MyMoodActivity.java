package com.sickle.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.adapter.AllMoodsAdapter;
import com.sickle.healthcareapp.db.DatabaseHelper;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.home.AddDialog;
import com.sickle.healthcareapp.home.AddMoodDialog;
import com.sickle.healthcareapp.home.HomeAdapter;
import com.sickle.healthcareapp.home.HomeItem;
import com.sickle.healthcareapp.home.MedicineActivity;
import com.sickle.healthcareapp.model.moodsModel.Mood;

import java.util.ArrayList;
import java.util.List;

public class MyMoodActivity extends AppCompatActivity {

    private ImageView fabAddMedicine;
    private RecyclerView all_moods_list;

    private ArrayList<Mood> moodItems;
    private DatabaseHelper databaseHelper;

    private AllMoodsAdapter allMoodsAdapter;

    FireStoreDB fireStoreDB;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mood);


        fireStoreDB = new FireStoreDB();

        fireStoreDB.getAllMoodsData(MyMoodActivity.this,MyMoodActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());

        all_moods_list = findViewById(R.id.all_moods_list);

        all_moods_list = findViewById(R.id.all_moods_list);

        all_moods_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyMoodActivity.this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        all_moods_list.setLayoutManager(linearLayoutManager);



        //loadAllMoods();

        fabAddMedicine = findViewById(R.id.fab_add_medicine);
        fabAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddMoodDialog addMoodDialog = new AddMoodDialog(MyMoodActivity.this);
                addMoodDialog.show(getSupportFragmentManager(), "Add_Mood_Dialog");
                // Handle fab click if needed
            }
        });
    }

    public void loadAllMoods() {
        databaseHelper = new DatabaseHelper(this);
        moodItems = databaseHelper.getMoodsList();

//        allMoodsAdapter = new AllMoodsAdapter(MyMoodActivity.this,moodItems);
        all_moods_list.setAdapter(allMoodsAdapter);
    }


    public void setadapterForMedicinceData() {

        allMoodsAdapter = new AllMoodsAdapter(MyMoodActivity.this);
        all_moods_list.setAdapter(allMoodsAdapter);
        allMoodsAdapter.notifyDataSetChanged();
    }
}
