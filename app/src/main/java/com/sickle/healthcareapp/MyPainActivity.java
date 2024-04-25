package com.sickle.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.mysicklecellapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.adapter.AllMoodsAdapter;
import com.sickle.healthcareapp.adapter.MyPainAdapter;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.home.AddMoodDialog;
import com.sickle.healthcareapp.home.AddPainDialog;

public class MyPainActivity extends AppCompatActivity {

    private ImageView fab_add_pain;
    private RecyclerView all_pain_list;
    MyPainAdapter myPainAdapter;
    FireStoreDB fireStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pain);


        all_pain_list = findViewById(R.id.all_pain_list);


        all_pain_list.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyPainActivity.this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        all_pain_list.setLayoutManager(linearLayoutManager);


        fab_add_pain = findViewById(R.id.fab_add_pain);
        fab_add_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AddPainDialog addPainDialog = new AddPainDialog(MyPainActivity.this);
                addPainDialog.show(getSupportFragmentManager(), "Add_Pain_Dialog");
                // Handle fab click if needed
            }
        });


        fireStoreDB = new FireStoreDB();
        fireStoreDB.getAllPainsData(MyPainActivity.this, MyPainActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void setadapterForMedicinceData() {
        myPainAdapter = new MyPainAdapter(MyPainActivity.this);
        all_pain_list.setAdapter(myPainAdapter);
        myPainAdapter.notifyDataSetChanged();
    }
}