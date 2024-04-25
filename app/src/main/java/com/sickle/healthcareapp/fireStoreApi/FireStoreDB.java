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
import com.sickle.healthcareapp.DoctorSidePatientRiskActivity;
import com.sickle.healthcareapp.MyHydration;
import com.sickle.healthcareapp.MyHydrationVisualActivity;
import com.sickle.healthcareapp.MyMoodActivity;
import com.sickle.healthcareapp.MyMoodVisualActivity;
import com.sickle.healthcareapp.MyPainActivity;
import com.sickle.healthcareapp.MyPainVisualActivity;
import com.sickle.healthcareapp.PatientHydrationVisualActivity;
import com.sickle.healthcareapp.PatientMoodVisualActivity;
import com.sickle.healthcareapp.PatientPainVisualActivity;
import com.sickle.healthcareapp.RiskAssesmentActivity;
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
    public static ArrayList<Mood> allMoodsForRiskMoodsList = new ArrayList<>();
    public static ArrayList<Mood> docSideMoodsForRiskMoodsList = new ArrayList<>();
    public static ArrayList<PainModel> allPainsForRiskMoodsList = new ArrayList<>();
    public static ArrayList<PainModel> docSideForRiskMoodsList = new ArrayList<>();
    public static ArrayList<Mood> doctorSideGraphMoodsList = new ArrayList<>();
    public static ArrayList<Mood> graphMoodsList = new ArrayList<>();
    public static ArrayList<HydrationModel> allHydrationList = new ArrayList<>();
    public static ArrayList<HydrationModel> allHydrationForRiskList = new ArrayList<>();
    public static ArrayList<HydrationModel> docSideHydrationForRiskList = new ArrayList<>();
    public static ArrayList<HydrationModel> allGraphHydrationList = new ArrayList<>();
    public static ArrayList<HydrationModel> doctorSideGraphHydrationList = new ArrayList<>();
    public static ArrayList<PainModel> allPainList = new ArrayList<>();
    public static ArrayList<PainModel> graphPainList = new ArrayList<>();
    public static ArrayList<PainModel> doctorSideGraphPainList = new ArrayList<>();

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
                                myPainActivity.setadapterForMedicinceData();

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

    public void getAllPainsAssessmentData(final Context mContext, final RiskAssesmentActivity riskAssesmentActivity,
                                    String userEmail) {

        if (allPainsForRiskMoodsList.size() > 0) {
            allPainsForRiskMoodsList.clear();
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
                                riskAssesmentActivity.fetchedPainsForRisk();

                            } else {
                                List<PainModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(PainModel.class);

                                allPainsForRiskMoodsList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedPainsForRisk();
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


    public void getAllDocSidePainsAssessData(final Context mContext, final DoctorSidePatientRiskActivity riskAssesmentActivity,
                                          String userEmail) {

        if (docSideForRiskMoodsList.size() > 0) {
            docSideForRiskMoodsList.clear();
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
                                riskAssesmentActivity.fetchedPainsForRisk();

                            } else {
                                List<PainModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(PainModel.class);

                                docSideForRiskMoodsList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedPainsForRisk();
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


    public void getAllGraphPainsData(final Context mContext, final MyPainVisualActivity myPainActivity,
                                    String userEmail) {

        if (graphPainList.size() > 0) {
            graphPainList.clear();
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

                                myPainActivity.setGraphPainData();
                            } else {
                                List<PainModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(PainModel.class);

                                graphPainList.addAll(destinationModelsDefault);


                                myPainActivity.setGraphPainData();
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


    public void doctorSideGetAllGraphPainsData(final Context mContext, final PatientPainVisualActivity myPainActivity,
                                    String userEmail) {

        if (doctorSideGraphPainList.size() > 0) {
            doctorSideGraphPainList.clear();
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
                                myPainActivity.setGraphPainData();

                            } else {
                                List<PainModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(PainModel.class);

                                doctorSideGraphPainList.addAll(destinationModelsDefault);


                                myPainActivity.setGraphPainData();
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


    public void getAllDocSideHydrationAssessData(final Context mContext, final DoctorSidePatientRiskActivity riskAssesmentActivity,
                                              String userEmail) {

        if (docSideHydrationForRiskList.size() > 0) {
            docSideHydrationForRiskList.clear();
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

                                riskAssesmentActivity.fetchedHydrationForRisk();
                            } else {
                                List<HydrationModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(HydrationModel.class);

                                docSideHydrationForRiskList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedHydrationForRisk();
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

    public void getAllHydrationAssessmentData(final Context mContext, final RiskAssesmentActivity riskAssesmentActivity,
                                    String userEmail) {

        if (allHydrationForRiskList.size() > 0) {
            allHydrationForRiskList.clear();
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

                                riskAssesmentActivity.fetchedHydrationForRisk();
                            } else {
                                List<HydrationModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(HydrationModel.class);

                                allHydrationForRiskList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedHydrationForRisk();
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

    public void getAllGraphHydrationData(final Context mContext, final MyHydrationVisualActivity myHydration,
                                    String userEmail) {

        if (allGraphHydrationList.size() > 0) {
            allGraphHydrationList.clear();
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
                                myHydration.setHydrationDataForGraph();

                            } else {
                                List<HydrationModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(HydrationModel.class);

                                allGraphHydrationList.addAll(destinationModelsDefault);


                                myHydration.setHydrationDataForGraph();
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

    public void doctorSideGetAllGraphHydrationData(final Context mContext, final PatientHydrationVisualActivity myHydration,
                                    String userEmail) {

        if (doctorSideGraphHydrationList.size() > 0) {
            doctorSideGraphHydrationList.clear();
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
                                myHydration.setHydrationDataForGraph();

                            } else {
                                List<HydrationModel> destinationModelsDefault = queryDocumentSnapshots.toObjects(HydrationModel.class);

                                doctorSideGraphHydrationList.addAll(destinationModelsDefault);


                                myHydration.setHydrationDataForGraph();
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


    public void getAllGraphMoodsData(final Context mContext, final MyMoodVisualActivity myMoodActivity,
                                    String userEmail) {

        if (graphMoodsList.size() > 0) {
            graphMoodsList.clear();
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
                                myMoodActivity.setGraphDataForVisualization();

                            } else {
                                List<Mood> destinationModelsDefault = queryDocumentSnapshots.toObjects(Mood.class);

                                graphMoodsList.addAll(destinationModelsDefault);


                                myMoodActivity.setGraphDataForVisualization();
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

                                myMoodActivity.setadapterForMedicinceData();
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


    public void getAllMoodsAssessmentData(final Context mContext, final RiskAssesmentActivity riskAssesmentActivity,
                                          String userEmail) {

        if (allMoodsForRiskMoodsList.size() > 0) {
            allMoodsForRiskMoodsList.clear();
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
                                riskAssesmentActivity.fetchedMoodsForRisk();

                            } else {
                                List<Mood> destinationModelsDefault = queryDocumentSnapshots.toObjects(Mood.class);

                                allMoodsForRiskMoodsList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedMoodsForRisk();
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

    public void getAllDocSideMoodsAssessData(final Context mContext, final DoctorSidePatientRiskActivity riskAssesmentActivity,
                                String userEmail) {

        if (docSideMoodsForRiskMoodsList.size() > 0) {
            docSideMoodsForRiskMoodsList.clear();
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
                                riskAssesmentActivity.fetchedMoodsForRisk();

                            } else {
                                List<Mood> destinationModelsDefault = queryDocumentSnapshots.toObjects(Mood.class);

                                docSideMoodsForRiskMoodsList.addAll(destinationModelsDefault);


                                riskAssesmentActivity.fetchedMoodsForRisk();
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

    public void doctorSideGetAllMoodsData(final Context mContext, final PatientMoodVisualActivity myMoodActivity,
                                    String userEmail) {

        if (doctorSideGraphMoodsList.size() > 0) {
            doctorSideGraphMoodsList.clear();
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
                                myMoodActivity.setDataForMoodVisualization();

                            } else {
                                List<Mood> destinationModelsDefault = queryDocumentSnapshots.toObjects(Mood.class);

                                doctorSideGraphMoodsList.addAll(destinationModelsDefault);


                                myMoodActivity.setDataForMoodVisualization();
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
