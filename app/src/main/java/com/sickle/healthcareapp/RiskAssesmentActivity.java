package com.sickle.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysicklecellapp.R;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.AppUtils;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;
import com.sickle.healthcareapp.model.moodsModel.Mood;
import com.sickle.healthcareapp.model.painModels.PainModel;

import java.util.ArrayList;
import java.util.Date;

public class RiskAssesmentActivity extends AppCompatActivity {


    public static ArrayList<Mood> allMoodsFor24HoursRiskList = new ArrayList<>();
    public static ArrayList<PainModel> allPainsFor24HoursRiskList = new ArrayList<>();
    public static ArrayList<HydrationModel> allHydrationFor24HoursRiskList = new ArrayList<>();

    TextView assessTV, desTV;
    ImageView riskIV;
    FireStoreDB fireStoreDB;
    ProgressBar progressPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assesment);

        allMoodsFor24HoursRiskList.clear();
        allPainsFor24HoursRiskList.clear();
        allHydrationFor24HoursRiskList.clear();

        fireStoreDB = new FireStoreDB();
        progressPB = findViewById(R.id.progressPB);
        assessTV = findViewById(R.id.assessTV);
        desTV = findViewById(R.id.desTV);
        riskIV = findViewById(R.id.riskIV);

        fireStoreDB.getAllMoodsAssessmentData(RiskAssesmentActivity.this, RiskAssesmentActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }

    public void fetchedMoodsForRisk() {
        fireStoreDB.getAllPainsAssessmentData(RiskAssesmentActivity.this, RiskAssesmentActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    public void fetchedPainsForRisk() {

        fireStoreDB.getAllHydrationAssessmentData(RiskAssesmentActivity.this, RiskAssesmentActivity.this,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());

    }

    public void fetchedHydrationForRisk() {

        Date currentDate = AppUtils.getCurrentDate();

        //Filtering the Moods arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.allMoodsForRiskMoodsList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.allMoodsForRiskMoodsList.get(jack).getSelectedMoodNoteDate()))) {
                allMoodsFor24HoursRiskList.add(FireStoreDB.allMoodsForRiskMoodsList.get(jack));
            }
        }


        ////Filtering the Pains arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.allPainsForRiskMoodsList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.allPainsForRiskMoodsList.get(jack).getPainNoteDate()))) {
                allPainsFor24HoursRiskList.add(FireStoreDB.allPainsForRiskMoodsList.get(jack));
            }
        }


        ////Filtering the Hydration arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.allHydrationForRiskList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.allHydrationForRiskList.get(jack).getHydrationDate()))) {
                allHydrationFor24HoursRiskList.add(FireStoreDB.allHydrationForRiskList.get(jack));
            }
        }


        if (allHydrationFor24HoursRiskList.size() == 0 && allPainsFor24HoursRiskList.size() == 0 && allMoodsFor24HoursRiskList.size() == 0) {
            assessTV.setText("There is not enough data please kindly log daily trackers");
            progressPB.setVisibility(View.GONE);
            return;
        }

        int hydrationValue = 0;
        //Calcualte total hydration level
        //33.814 onces in one liter
        for (HydrationModel hydrationModel : allHydrationFor24HoursRiskList) {
            hydrationValue += hydrationModel.getOunceValue();
        }

        //Moods
        int excited = 0;
        int happy = 0;
        int neutral = 0;
        int sad = 0;
        int depressed = 0;


        //Segregrate Moods
        for (Mood mood : allMoodsFor24HoursRiskList) {
            if (mood.getSelectedMood().toLowerCase().equals("excited")) {
                excited++;
            } else if (mood.getSelectedMood().toLowerCase().equals("happy")) {
                happy++;
            } else if (mood.getSelectedMood().toLowerCase().equals("neutral")) {
                neutral++;
            } else if (mood.getSelectedMood().toLowerCase().equals("sad")) {
                sad++;
            } else if (mood.getSelectedMood().toLowerCase().equals("depressed")) {
                depressed++;
            }
        }


        //Segregrate Pain
        int little_pain = 0; //LITTLE_PAIN
        int pain = 0; //PAIN
        int severe_pain = 0; //SEVERE_PAIN
        int no_pain = 0; //"NO_PAIN"
        int painfull = 0; //"PAINFULL"
        for (PainModel painModel : allPainsFor24HoursRiskList) {
            if (painModel.getCurrentPainEmotion().toLowerCase().equals("little_pain")) {
                little_pain++;
            } else if (painModel.getCurrentPainEmotion().toLowerCase().equals("pain")) {
                pain++;
            } else if (painModel.getCurrentPainEmotion().toLowerCase().equals("severe_pain")) {
                severe_pain++;
            } else if (painModel.getCurrentPainEmotion().toLowerCase().equals("no_pain")) {
                no_pain++;
            } else if (painModel.getCurrentPainEmotion().toLowerCase().equals("painfull")) {
                painfull++;
            }
        }


        if (hydrationValue >= 70) {
            if ((excited + happy + neutral) > (sad + depressed) && (little_pain + pain == 0)) {
                assessTV.setText("You have a LOW risk of pain spell");
                desTV.setText("You have a pretty good hydration level, Enjoy your day");
                riskIV.setImageResource(R.drawable.baseline_low);
            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {
                assessTV.setText("You have a MEDIUM risk of pain spell");
                desTV.setText("You have a pretty good hydration level, take medicince and do exercise regularly");
                riskIV.setImageResource(R.drawable.baseline_medium);
            } else {
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                assessTV.setText("You have a HIGH risk of pain spell");
                desTV.setText("You have a pretty good hydration level, You can consult your doctor immediately");
            }
        } else if (hydrationValue >= 50) {
            if ((excited + happy + neutral) > (sad + depressed) && (little_pain + pain == 0)) {
                assessTV.setText("You have a MEDIUM risk of pain spell");
                desTV.setText("You have a LOW HYDRATION level please HYDRATE at earliest, You can consult your doctor if needed");
                riskIV.setImageResource(R.drawable.baseline_medium);

            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {

                assessTV.setText("You have a MEDIUM risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_medium);
                desTV.setText("You have a MEDIUM HYDRATION level please HYDRATE your self, You can consult your doctor if needed");
            } else {
                assessTV.setText("You have a MEDIUM risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_medium);
                desTV.setText("You have a GOOD HYDRATION level please, You can consult your doctor if needed");
            }
        } else {
            if ((excited + happy + neutral) > (sad + depressed) && (little_pain + pain == 0)) {
                assessTV.setText("You have a HIGH risk of pain spell");
                desTV.setText("You have a LOW HYDRATION level please HYDRATE at earliest, You can consult your doctor if needed");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {
                assessTV.setText("You have a HIGH risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                desTV.setText("You have a MEDIUM HYDRATION level please HYDRATE your self, You can consult your doctor if needed");
            } else {
                assessTV.setText("You have High risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                desTV.setText("You have a GOOD HYDRATION level please, You can consult your doctor if needed");

            }
        }


        progressPB.setVisibility(View.GONE);

    }
}
