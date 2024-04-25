package com.sickle.healthcareapp.fireStoreApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sickle.healthcareapp.Common.AppUtils;
import com.sickle.healthcareapp.MyHydration;
import com.sickle.healthcareapp.MyMoodActivity;
import com.sickle.healthcareapp.MyPainActivity;
import com.sickle.healthcareapp.home.AddDialog;
import com.sickle.healthcareapp.home.AddHydrationDialog;
import com.sickle.healthcareapp.home.AddMoodDialog;
import com.sickle.healthcareapp.home.AddPainDialog;
import com.sickle.healthcareapp.home.HomeItem;
import com.sickle.healthcareapp.home.MedicineActivity;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;
import com.sickle.healthcareapp.model.medicineNameModels.MedicineFireStoreModel;
import com.sickle.healthcareapp.model.moodsModel.Mood;
import com.sickle.healthcareapp.model.painModels.PainModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreDB {

    private static final String TAG = FireStoreDB.class.getSimpleName();
    private FirebaseFirestore dbFireStoreInstance;

    public static ArrayList<MedicineFireStoreModel> allMedicinesList = new ArrayList<>();
    public static ArrayList<Mood> allMoodsList = new ArrayList<>();
    public static ArrayList<HydrationModel> allHydrationList = new ArrayList<>();
    public static ArrayList<PainModel> allPainList = new ArrayList<>();

    public void insertNewMedicine(final Context mContext, final AddDialog addDialog,
                                  MedicineFireStoreModel homeItem, String userEmail) {

        if (AppUtils.isNetworkAvailable(mContext)) {

            dbFireStoreInstance = FirebaseFirestore.getInstance();
            Map<String, Object> MedicineData = new HashMap<>();
            MedicineData.put("medicineNDC", homeItem.getMedicineNDC());
            MedicineData.put("medicineName", homeItem.getMedicineName());
            MedicineData.put("totalDoses", homeItem.getTotalDoses());
            MedicineData.put("activeIngredientName", homeItem.getActiveIngredientName());
            MedicineData.put("activeIngredientStrength", homeItem.getActiveIngredientStrength());
            MedicineData.put("alertType", homeItem.getAlertType());
            MedicineData.put("timings", homeItem.getTimings());
            MedicineData.put("day", homeItem.getDay());
            MedicineData.put("month", homeItem.getMonth());
            MedicineData.put("year", homeItem.getYear());
            MedicineData.put("noOfTimesPerDay", homeItem.getNoOfTimesPerDay());


            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyMedicine")
                    .add(MedicineData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            addDialog.onMedicineInserted(true, "");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);

                            addDialog.onMedicineInserted(false, e.getMessage());
                        }
                    });

        } else {

            Toast.makeText(mContext, "No internet connection found ", Toast.LENGTH_LONG).show();
        }
    }


    public void insertNewMoodData(final Context mContext, final AddMoodDialog addMoodDialog,
                                  Mood homeItem, String userEmail) {

        if (AppUtils.isNetworkAvailable(mContext)) {

            dbFireStoreInstance = FirebaseFirestore.getInstance();
            Map<String, Object> MedicineData = new HashMap<>();
            MedicineData.put("selectedMood", homeItem.getSelectedMood());
            MedicineData.put("selectedMoodLevel", homeItem.getSelectedMoodLevel());
            MedicineData.put("selectedMoodNote", homeItem.getSelectedMoodNote());
            MedicineData.put("selectedMoodNoteDate", homeItem.getSelectedMoodNoteDate());



            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyMoods")
                    .add(MedicineData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            addMoodDialog.onMoodInserted(true, "");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);

                            addMoodDialog.onMoodInserted(false, e.getMessage());
                        }
                    });

        } else {

            Toast.makeText(mContext, "No internet connection found ", Toast.LENGTH_LONG).show();
        }
    }



    public void insertPainData(final Context mContext, final AddPainDialog addPainDialog,
                               PainModel painModel, String userEmail) {

        if (AppUtils.isNetworkAvailable(mContext)) {

            dbFireStoreInstance = FirebaseFirestore.getInstance();
            Map<String, Object> MedicineData = new HashMap<>();
            MedicineData.put("painLevel", painModel.getPainLevel());
            MedicineData.put("painNotes", painModel.getPainNotes());
            MedicineData.put("painAreasList", painModel.getPainAreasList());
            MedicineData.put("painNoteDate", painModel.getPainNoteDate());
            MedicineData.put("currentPainEmotion", painModel.getCurrentPainEmotion());



            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyPain")
                    .add(MedicineData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            addPainDialog.onPainValueInserted(true, "");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);

                            addPainDialog.onPainValueInserted(false, e.getMessage());
                        }
                    });

        } else {

            Toast.makeText(mContext, "No internet connection found ", Toast.LENGTH_LONG).show();
        }
    }

    public void getAllPainsData(final Context mContext, final MyPainActivity myPainActivity,
                                    String userEmail) {

        if (allPainList.size() > 0) {
            allPainList.clear();
        }


        if (AppUtils.isNetworkAvailable(mContext)) {
            dbFireStoreInstance = FirebaseFirestore.getInstance();

            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyPain")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");


                            } else {
                                List<PainModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(PainModel.class);

                                allPainList.addAll(destinationModelsDefault);


                                myPainActivity.setadapterForMedicinceData();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error While getting Data" + e, Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            Toast.makeText(mContext, "No Internet Connection found", Toast.LENGTH_LONG).show();
        }
    }




    public void insertNewHydrationData(final Context mContext, final AddHydrationDialog addHydrationDialog,
                                       HydrationModel hydrationModel, String userEmail) {

        if (AppUtils.isNetworkAvailable(mContext)) {

            dbFireStoreInstance = FirebaseFirestore.getInstance();
            Map<String, Object> MedicineData = new HashMap<>();
            MedicineData.put("ounceGlassValue", hydrationModel.getOunceGlassValue());
            MedicineData.put("ounceBottleValue", hydrationModel.getOunceBottleValue());
            MedicineData.put("ounceValue", hydrationModel.getOunceValue());
            MedicineData.put("hydrationDate", hydrationModel.getHydrationDate());



            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyHydration")
                    .add(MedicineData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            addHydrationDialog.onHydrationInserted(true, "");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);

                            addHydrationDialog.onHydrationInserted(false, e.getMessage());
                        }
                    });

        } else {

            Toast.makeText(mContext, "No internet connection found ", Toast.LENGTH_LONG).show();
        }
    }

    public void getAllHydrationData(final Context mContext, final MyHydration myHydration,
                                    String userEmail) {

        if (allHydrationList.size() > 0) {
            allHydrationList.clear();
        }


        if (AppUtils.isNetworkAvailable(mContext)) {
            dbFireStoreInstance = FirebaseFirestore.getInstance();

            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyHydration")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");


                            } else {
                                List<HydrationModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(HydrationModel.class);

                                allHydrationList.addAll(destinationModelsDefault);


                                myHydration.setadapterForMedicinceData();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error While getting Data" + e, Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            Toast.makeText(mContext, "No Internet Connection found", Toast.LENGTH_LONG).show();
        }
    }


    public void getAllMoodsData(final Context mContext, final MyMoodActivity myMoodActivity,
                                    String userEmail) {

        if (allMoodsList.size() > 0) {
            allMoodsList.clear();
        }


        if (AppUtils.isNetworkAvailable(mContext)) {
            dbFireStoreInstance = FirebaseFirestore.getInstance();

            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyMoods")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");


                            } else {
                                List<Mood> destinationModelsDefault = queryDocumentSnapshots.toObjects(Mood.class);

                                allMoodsList.addAll(destinationModelsDefault);


                                myMoodActivity.setadapterForMedicinceData();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error While getting Data" + e, Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            Toast.makeText(mContext, "No Internet Connection found", Toast.LENGTH_LONG).show();
        }
    }


    public void getAllMedicinesData(final Context mContext, final MedicineActivity medicineActivity,
                                    String userEmail) {

        if (allMedicinesList.size() > 0) {
            allMedicinesList.clear();
        }


        if (AppUtils.isNetworkAvailable(mContext)) {
            dbFireStoreInstance = FirebaseFirestore.getInstance();

            dbFireStoreInstance.collection("Patient")
                    .document(userEmail)
                    .collection("MyMedicine")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.d(TAG, "onSuccess: LIST EMPTY");


                            } else {
                                List<MedicineFireStoreModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(MedicineFireStoreModel.class);

                                allMedicinesList.addAll(destinationModelsDefault);


                                medicineActivity.setadapterForMedicinceData();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Error While getting Data" + e, Toast.LENGTH_LONG).show();
                        }
                    });

        } else {
            Toast.makeText(mContext, "No Internet Connection found", Toast.LENGTH_LONG).show();
        }
    }
}
