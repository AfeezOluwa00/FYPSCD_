package com.sickle.healthcareapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysicklecellapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.AppUtils;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyPainVisualActivity extends AppCompatActivity {

    FireStoreDB fireStoreDB;

    String[] dataTypes = {"All", "24 Hours", "7 Days",
            "14 Days", "30 Days"};
    Spinner dateSPN;
    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    ProgressBar progressBar;

    private boolean isDataLoaded = false;
    private void reDrawGraph(String dateType) {

        barEntriesArrayList.clear();
        Date currentDate = AppUtils.getCurrentDate();

        if (dateType.equals("All")) {
            // X-axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis position to bottom
            xAxis.setGranularity(1f); // set minimum interval for the X-axis to 1
            xAxis.setTextSize(3f);
            //String[] dateArray = new String[FireStoreDB.graphPainList.size()];
            for (int jack = 0; jack < FireStoreDB.graphPainList.size(); jack++) {
                //dateArray[jack] = FireStoreDB.graphPainList.get(jack).getPainNoteDate();
                barEntriesArrayList.add(new BarEntry(jack, FireStoreDB.graphPainList.get(jack).getPainLevel()));
            }
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"20 APR", "20 Feb", "20 Mar", "20 Apr", "20 May", "20 Jun"})); // set custom labels for the X-axis
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray)); // set custom labels for the X-axis

        } else if (dateType.equals("24 Hours")) {
            // X-axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis position to bottom
            xAxis.setGranularity(1f); // set minimum interval for the X-axis to 1
            xAxis.setTextSize(3f);
            //String[] dateArray = new String[FireStoreDB.graphPainList.size()];
            for (int jack = 0; jack < FireStoreDB.graphPainList.size(); jack++) {

                if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.graphPainList.get(jack).getPainNoteDate()))) {
                    //dateArray[jack] = FireStoreDB.graphPainList.get(jack).getPainNoteDate();
                    barEntriesArrayList.add(new BarEntry(jack, FireStoreDB.graphPainList.get(jack).getPainLevel()));
                }

            }
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"20 APR", "20 Feb", "20 Mar", "20 Apr", "20 May", "20 Jun"})); // set custom labels for the X-axis
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray)); // set custom labels for the X-axis

        } else if (dateType.equals("7 Days")) {


            // X-axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis position to bottom
            xAxis.setGranularity(1f); // set minimum interval for the X-axis to 1
            xAxis.setTextSize(3f);
            // String[] dateArray = new String[FireStoreDB.graphPainList.size()];
            for (int jack = 0; jack < FireStoreDB.graphPainList.size(); jack++) {

                if (AppUtils.isDifference7Days(currentDate, AppUtils.parseFetchedDate(FireStoreDB.graphPainList.get(jack).getPainNoteDate()))) {
                    //dateArray[jack] = FireStoreDB.graphPainList.get(jack).getPainNoteDate();
                    barEntriesArrayList.add(new BarEntry(jack, FireStoreDB.graphPainList.get(jack).getPainLevel()));
                }
            }


            //xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"20 APR", "20 Feb", "20 Mar", "20 Apr", "20 May", "20 Jun"})); // set custom labels for the X-axis
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray)); // set custom labels for the X-axis


        } else if (dateType.equals("14 Days")) {
            // X-axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis position to bottom
            xAxis.setGranularity(1f); // set minimum interval for the X-axis to 1
            xAxis.setTextSize(3f);
            //String[] dateArray = new String[FireStoreDB.graphPainList.size()];
            for (int jack = 0; jack < FireStoreDB.graphPainList.size(); jack++) {

                if (AppUtils.isDifference14Days(currentDate, AppUtils.parseFetchedDate(FireStoreDB.graphPainList.get(jack).getPainNoteDate()))) {
                    //dateArray[jack] = FireStoreDB.graphPainList.get(jack).getPainNoteDate();
                    barEntriesArrayList.add(new BarEntry(jack, FireStoreDB.graphPainList.get(jack).getPainLevel()));
                }
            }
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"20 APR", "20 Feb", "20 Mar", "20 Apr", "20 May", "20 Jun"})); // set custom labels for the X-axis
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray)); // set custom labels for the X-axis

        } else if (dateType.equals("30 Days")) {
            // X-axis
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // set XAxis position to bottom
            xAxis.setGranularity(1f); // set minimum interval for the X-axis to 1
            xAxis.setTextSize(3f);
            //String[] dateArray = new String[FireStoreDB.graphPainList.size()];
            for (int jack = 0; jack < FireStoreDB.graphPainList.size(); jack++) {

                if (AppUtils.isDifference30Days(currentDate, AppUtils.parseFetchedDate(FireStoreDB.graphPainList.get(jack).getPainNoteDate()))) {
                    //dateArray[jack] = FireStoreDB.graphPainList.get(jack).getPainNoteDate();
                    barEntriesArrayList.add(new BarEntry(jack, FireStoreDB.graphPainList.get(jack).getPainLevel()));
                }
            }
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"20 APR", "20 Feb", "20 Mar", "20 Apr", "20 May", "20 Jun"})); // set custom labels for the X-axis
            //xAxis.setValueFormatter(new IndexAxisValueFormatter(dateArray)); // set custom labels for the X-axis

        }


        barDataSet = new BarDataSet(barEntriesArrayList, "Pain Levels");

        barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(Color.RED);
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(6f);
        barChart.getDescription().setEnabled(false);


        // refresh the chart
        barChart.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_visual_pain);
        fireStoreDB = new FireStoreDB();
        barEntriesArrayList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);

        barChart = findViewById(R.id.idBarChart);
        dateSPN = findViewById(R.id.dateSPN);
        dateSPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isDataLoaded) {
                    reDrawGraph(dataTypes[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                dataTypes);

        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        dateSPN.setAdapter(ad);


        progressBar.setVisibility(View.VISIBLE);
        fireStoreDB.getAllGraphPainsData(MyPainVisualActivity.this,
                MyPainVisualActivity.this, FirebaseAuth.getInstance().getCurrentUser().getEmail());

    }


    public void setGraphPainData() {
        progressBar.setVisibility(View.GONE);
        isDataLoaded = true;
        reDrawGraph("All");
    }
}
