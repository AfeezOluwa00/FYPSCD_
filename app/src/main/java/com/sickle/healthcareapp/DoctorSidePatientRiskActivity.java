package com.sickle.healthcareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysicklecellapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.AppUtils;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;
import com.sickle.healthcareapp.model.moodsModel.Mood;
import com.sickle.healthcareapp.model.painModels.PainModel;

import java.util.ArrayList;
import java.util.Date;

public class DoctorSidePatientRiskActivity extends AppCompatActivity {

    private String patient_email;

    public static ArrayList<Mood> docSideMoodsFor24HoursRiskList = new ArrayList<>();
    public static ArrayList<PainModel> docSidePainsFor24HoursRiskList = new ArrayList<>();
    public static ArrayList<HydrationModel> docSideHydrationFor24HoursRiskList = new ArrayList<>();

    TextView assessTV, desTV;
    ImageView riskIV;
    FireStoreDB fireStoreDB;
    ProgressBar progressPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_patient_risk);

        patient_email = getIntent().getStringExtra("patient_email");

        docSideMoodsFor24HoursRiskList.clear();
        docSidePainsFor24HoursRiskList.clear();
        docSideHydrationFor24HoursRiskList.clear();

        fireStoreDB = new FireStoreDB();
        progressPB = findViewById(R.id.progressPB);
        assessTV = findViewById(R.id.assessTV);
        desTV = findViewById(R.id.desTV);
        riskIV = findViewById(R.id.riskIV);

        fireStoreDB.getAllDocSideMoodsAssessData(DoctorSidePatientRiskActivity.this, DoctorSidePatientRiskActivity.this,
                patient_email);


    }

    public void fetchedMoodsForRisk() {
        fireStoreDB.getAllDocSidePainsAssessData(DoctorSidePatientRiskActivity.this, DoctorSidePatientRiskActivity.this,
                patient_email);
    }


    public void fetchedPainsForRisk() {

        fireStoreDB.getAllDocSideHydrationAssessData(DoctorSidePatientRiskActivity.this, DoctorSidePatientRiskActivity.this,
                patient_email);

    }

    public void fetchedHydrationForRisk() {

        Date currentDate = AppUtils.getCurrentDate();

        //Filtering the Moods arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.docSideMoodsForRiskMoodsList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.docSideMoodsForRiskMoodsList.get(jack).getSelectedMoodNoteDate()))) {
                docSideMoodsFor24HoursRiskList.add(FireStoreDB.docSideMoodsForRiskMoodsList.get(jack));
            }
        }


        ////Filtering the Pains arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.docSideForRiskMoodsList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.docSideForRiskMoodsList.get(jack).getPainNoteDate()))) {
                docSidePainsFor24HoursRiskList.add(FireStoreDB.docSideForRiskMoodsList.get(jack));
            }
        }


        ////Filtering the Hydration arraylist for 24 hours
        for (int jack = 0; jack < FireStoreDB.docSideHydrationForRiskList.size(); jack++) {
            if (AppUtils.isDifference1Day(currentDate, AppUtils.parseFetchedDate(FireStoreDB.docSideHydrationForRiskList.get(jack).getHydrationDate()))) {
                docSideHydrationFor24HoursRiskList.add(FireStoreDB.docSideHydrationForRiskList.get(jack));
            }
        }


        if (docSideHydrationFor24HoursRiskList.size() == 0 && docSidePainsFor24HoursRiskList.size() == 0 && docSideMoodsFor24HoursRiskList.size() == 0) {
            assessTV.setText("There is not enough data please kindly log daily trackers");
            desTV.setText("No Suggestion so far");
            progressPB.setVisibility(View.GONE);
            return;
        }

        int hydrationValue = 0;
        //Calcualte total hydration level
        //33.814 onces in one liter
        for (HydrationModel hydrationModel : docSideHydrationFor24HoursRiskList) {
            hydrationValue += hydrationModel.getOunceValue();
        }

        //Moods
        int excited = 0;
        int happy = 0;
        int neutral = 0;
        int sad = 0;
        int depressed = 0;


        //Segregrate Moods
        for (Mood mood : docSideMoodsFor24HoursRiskList) {
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
        for (PainModel painModel : docSidePainsFor24HoursRiskList) {
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
                assessTV.setText("Patient has a LOW risk of pain spell");
                desTV.setText("Patient has a pretty good hydration level, He can enjoy the day");
                riskIV.setImageResource(R.drawable.baseline_low);
            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {
                assessTV.setText("Patient has a MEDIUM risk of pain spell");
                desTV.setText("Patient has a pretty good hydration level, He can take medicince and do exercise regularly");
                riskIV.setImageResource(R.drawable.baseline_medium);
            } else {
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                assessTV.setText("Patient has a HIGH risk of pain spell");
                desTV.setText("Patient has a pretty good hydration level, He might be contacting you anytime");
            }
        } else if (hydrationValue >= 50) {
            if ((excited + happy + neutral) > (sad + depressed) && (little_pain + pain == 0)) {
                assessTV.setText("Patient has a MEDIUM risk of pain spell");
                desTV.setText("Patient has a LOW HYDRATION level. He needs to be HYDRATED at earliest.");
                riskIV.setImageResource(R.drawable.baseline_medium);

            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {

                assessTV.setText("Patient has a MEDIUM risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_medium);
                desTV.setText("Patient has a MEDIUM HYDRATION level. He should HYDRATE himself");
            } else {
                assessTV.setText("Patient has a MEDIUM risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_medium);
                desTV.setText("Patient has a GOOD HYDRATION level, He might consult you later on");
            }
        } else {
            if ((excited + happy + neutral) > (sad + depressed) && (little_pain + pain == 0)) {
                assessTV.setText("Patient has a HIGH risk of pain spell");
                desTV.setText("Patient has a LOW HYDRATION level. He should HYDRATE himself at earliest. You can be consulted anytime");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
            } else if ((sad + depressed) > (excited + happy + neutral) && (pain > 0 || severe_pain > 0 || painfull > 0)) {
                assessTV.setText("Patient has a HIGH risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                desTV.setText("Patient has a MEDIUM HYDRATION level, He should be HYDRATED himself. He can consult you anytime");
            } else {
                assessTV.setText("Patient has High risk of pain spell");
                riskIV.setImageResource(R.drawable.baseline_red_alert);
                desTV.setText("Patient has a GOOD HYDRATION level please, Seems like hez not feeling well. He can consult you anytime");

            }
        }


        progressPB.setVisibility(View.GONE);

    }
}
