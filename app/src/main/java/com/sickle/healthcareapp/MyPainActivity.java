package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mysicklecellapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.adapter.AllMoodsAdapter;
import com.sickle.healthcareapp.adapter.MyPainAdapter;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.home.AddMoodDialog;
import com.sickle.healthcareapp.home.AddPainDialog;

public class MyPainActivity extends AppCompatActivity {

    private ImageView fab_add_pain, fab_visualization_pain;
    private RecyclerView all_pain_list;
    MyPainAdapter myPainAdapter;
    FireStoreDB fireStoreDB;
    ProgressBar progressPB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pain);


        progressPB = findViewById(R.id.progressPB);
        all_pain_list = findViewById(R.id.all_pain_list);


        all_pain_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyPainActivity.this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        all_pain_list.setLayoutManager(linearLayoutManager);


        fab_visualization_pain = findViewById(R.id.fab_visualization_pain);
        fab_visualization_pain.setOnClickListener(view -> {

            Intent intent = new Intent(MyPainActivity.this, MyPainVisualActivity.class);
            startActivity(intent);

        });

        fab_add_pain = findViewById(R.id.fab_add_pain);
        fab_add_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddPainDialog addPainDialog = new AddPainDialog(MyPainActivity.this);
                addPainDialog.show(getSupportFragmentManager(), "Add_Pain_Dialog");
                // Handle fab click if needed
            }
        });


        progressPB.setVisibility(View.VISIBLE);
        fireStoreDB = new FireStoreDB();
        fireStoreDB.getAllPainsData(MyPainActivity.this, MyPainActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void setadapterForMedicinceData() {
        progressPB.setVisibility(View.GONE);
        myPainAdapter = new MyPainAdapter(MyPainActivity.this);
        all_pain_list.setAdapter(myPainAdapter);
        myPainAdapter.notifyDataSetChanged();
    }
}