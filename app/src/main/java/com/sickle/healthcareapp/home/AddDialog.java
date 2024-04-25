package com.sickle.healthcareapp.home;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.AlarmActivity;
import com.sickle.healthcareapp.BarCodeActivity;
import com.sickle.healthcareapp.HomeActivity;
import com.sickle.healthcareapp.db.DatabaseHelper;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.home.time.TimeAdapter;
import com.sickle.healthcareapp.home.time.TimeSelectorItem;
import com.sickle.healthcareapp.model.medicineNameModels.MedicineFireStoreModel;
import com.sickle.healthcareapp.model.medicineNameModels.Result;
import com.sickle.healthcareapp.model.medicineNameModels.Root;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public class AddDialog extends DialogFragment implements Toolbar.OnMenuItemClickListener {

    public static final String TAG = "Add_Dialog";
    private static final String FDA_API_KEY = "tvCQTJlyexutBbdOX2MUkftYYWPzaqD6pQDz1b3K";
    private MaterialToolbar toolbar;
    private MaterialTextView textViewDate;
    private EditText editTextMedicineName;

    private FireStoreDB fireStoreDB;

    private TextView generic_name_tv, labeler_name_tv, brand_name_tv, active_ingred_name_tv, active_ingred_strength_tv;

    private LinearLayout generic_name_ll, laberler_name_ll, brand_name_ll, ingredents_name_ll, ingredents_strength_ll;
    private ChipGroup chipGroupScheduleTimes, chipGroupAlertType;

    private Button get_api_btn;
    public static String barCodeScanned = "";
    private Chip chipSelected;
    private int[] chipArrayIds = {R.id.chip1, R.id.chip2, R.id.chip3, R.id.chip4, R.id.chip5};
    private int[] chipAlertArrayIds = {R.id.chip_notification, R.id.chip_alarm};

    private List<TimeSelectorItem> timeSelectorItems;
    private int mPerDay = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private NumberPicker numberPicker;
    private int noOfTotalTimes;
    private String alertType;

    private Calendar calendar;

    public MedicineActivity medicineActivity;
    ImageView barcode;

    public AddDialog(MedicineActivity medicineActivity) {
        this.medicineActivity = medicineActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onResume() {
        super.onResume();

        editTextMedicineName.setText(barCodeScanned);
    }

    public void onMedicineInserted(boolean inserted, String errorMessage) {
        if (inserted) {
            dismiss();
            Toast.makeText(getActivity(), "Medicine Inserted Successfully ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Error Inserting Medicine Data " + errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public interface FdaApi {
        @GET("drug/ndc.json")
        Call<Root> getSubstanceData(@Query("search") String searchQuery, @Header("Authorization") String authorization);
    }

    private void getFdaData(String searchCode) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.fda.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FdaApi fdaApi = retrofit.create(FdaApi.class);

        Call<Root> call = fdaApi.getSubstanceData("packaging.package_ndc:" + searchCode, "Bearer " + FDA_API_KEY);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful()) {

                    showLinearLayouts(true);


                    Root rootObj = response.body();
                    if (rootObj != null && rootObj.results != null && !rootObj.results.isEmpty()) {

                        Result result = rootObj.results.get(0);

                        generic_name_tv.setText(result.generic_name);
                        labeler_name_tv.setText(result.labeler_name);
                        brand_name_tv.setText(result.brand_name);
                        active_ingred_name_tv.setText(result.active_ingredients.get(0).name);
                        active_ingred_strength_tv.setText(result.active_ingredients.get(0).strength);

                        Toast.makeText(getActivity(), "FDA API Substance Code: " + result.product_ndc, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "FDA API Data not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to get FDA API data with code: " + response.code(), Toast.LENGTH_SHORT).show();

                    showLinearLayouts(false);
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(getActivity(), "FDA API Network error", Toast.LENGTH_SHORT).show();

                showLinearLayouts(false);
            }
        });
    }

    private void showLinearLayouts(boolean show) {

        if (show) {
            brand_name_ll.setVisibility(View.VISIBLE);
            ingredents_strength_ll.setVisibility(View.VISIBLE);
            ingredents_name_ll.setVisibility(View.VISIBLE);
            laberler_name_ll.setVisibility(View.VISIBLE);
            generic_name_ll.setVisibility(View.VISIBLE);

        } else {
            brand_name_ll.setVisibility(View.GONE);
            ingredents_strength_ll.setVisibility(View.GONE);
            ingredents_name_ll.setVisibility(View.GONE);
            laberler_name_ll.setVisibility(View.GONE);
            generic_name_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.add_medicine_dialog, container, false);

        fireStoreDB = new FireStoreDB();
        toolbar = root.findViewById(R.id.toolbar);
        textViewDate = root.findViewById(R.id.text_view_select_date);
        brand_name_ll = root.findViewById(R.id.brand_name_ll);
        ingredents_strength_ll = root.findViewById(R.id.ingredents_strength_ll);
        get_api_btn = root.findViewById(R.id.get_api_btn);
        ingredents_name_ll = root.findViewById(R.id.ingredents_name_ll);
        laberler_name_ll = root.findViewById(R.id.laberler_name_ll);
        generic_name_ll = root.findViewById(R.id.generic_name_ll);
        generic_name_tv = root.findViewById(R.id.generic_name_tv);
        labeler_name_tv = root.findViewById(R.id.labeler_name_tv);
        brand_name_tv = root.findViewById(R.id.brand_name_tv);
        active_ingred_name_tv = root.findViewById(R.id.active_ingred_name_tv);
        active_ingred_strength_tv = root.findViewById(R.id.active_ingred_strength_tv);
        editTextMedicineName = root.findViewById(R.id.editText_medicine_name);
        editTextMedicineName.setText(barCodeScanned);
        chipGroupScheduleTimes = root.findViewById(R.id.chip_group_times);
        recyclerView = root.findViewById(R.id.recycler_view_time);
        numberPicker = root.findViewById(R.id.number_picker_number_doses);
        barcode = root.findViewById(R.id.bar_code);
        chipGroupAlertType = root.findViewById(R.id.chip_group_alert_type);
        chipSelected = root.findViewById(chipGroupAlertType.getCheckedChipId());

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(AddDialog.this.getContext(), "Close Pressed", Toast.LENGTH_SHORT).show();
            dismiss();
        });
        toolbar.setTitle("Add Medicine");
        toolbar.inflateMenu(R.menu.add_dialog_menu);
        toolbar.setOnMenuItemClickListener(this);

        get_api_btn.setOnClickListener(View -> {
            if (!editTextMedicineName.getText().toString().trim().isEmpty()) {
                getFdaData(editTextMedicineName.getText().toString().trim());
            } else {
                Toast.makeText(getActivity(), "Scan or rrite NDC to proceed ahead", Toast.LENGTH_SHORT).show();
            }
        });


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        textViewDate.setText(format.format(calendar.getTime()));

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BarCodeActivity.class));
            }
        });
        textViewDate.setOnClickListener(view1 -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view2, year, monthOfYear, dayOfMonth) -> {
                        // set day of month , month and year value in the edit text
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
                        textViewDate.setText(format1.format(calendar.getTime()));


                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        format = new SimpleDateFormat("h:mm a");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        timeSelectorItems = new ArrayList<>();

        medicineActivity.timeItems.clear();
        if (mPerDay > 0) {
            numberPicker.setMinValue(mPerDay);
        } else {
            numberPicker.setMinValue(0);
        }
        timeSelectorItems.clear();
        for (int i = 0; i < mPerDay; i++) {
            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Pick a Time");
            timeSelectorItems.add(timeSelectorItem);
        }
        adapter = new TimeAdapter(timeSelectorItems, getActivity());
        recyclerView.setAdapter(adapter);
        chipGroupScheduleTimes.setOnCheckedChangeListener((chipGroup, id) -> {
            Chip chip = chipGroup.findViewById(id);
            if (chip != null) {
                for (int iTmp = 0; iTmp < chipArrayIds.length; iTmp++) {
                    if (chipGroup.getCheckedChipId() == chipArrayIds[iTmp]) {
                        mPerDay = iTmp + 1;
//                        Toast.makeText(getContext(), String.valueOf(mPerDay), Toast.LENGTH_LONG).show();
                        medicineActivity.timeItems.clear();
                        if (mPerDay > 0) {
                            numberPicker.setMinValue(mPerDay);
                        } else {
                            numberPicker.setMinValue(0);
                        }
                        timeSelectorItems.clear();
                        for (int i = 0; i < mPerDay; i++) {
                            TimeSelectorItem timeSelectorItem = new TimeSelectorItem("Pick a Time");
                            timeSelectorItems.add(timeSelectorItem);
                        }
                        adapter = new TimeAdapter(timeSelectorItems, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }
        });

        numberPicker.setMaxValue(50);
        numberPicker.setMinValue(mPerDay);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                noOfTotalTimes = numberPicker.getValue();
                Log.d("picker value", String.valueOf(noOfTotalTimes));
            }
        });

        chipGroupAlertType.setOnCheckedChangeListener((chipGroup, id) -> {
            chipSelected = chipGroup.findViewById(id);
            if (chipSelected != null)
                alertType = chipSelected.getText().toString();
            else
                showAlertDialog("Alert Type");
        });
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        MedicineActivity medicineActivity = (MedicineActivity) getActivity();
        String medicineNDC = editTextMedicineName.getText().toString();
        String medicineName = brand_name_tv.getText().toString();
        String activeIngredientStrength = active_ingred_strength_tv.getText().toString();
        String activeIngredientName = active_ingred_name_tv.getText().toString();

        if (medicineNDC.isEmpty()) {
            editTextMedicineName.setError("Enter a name");
            return false;
        }

        if (medicineName.isEmpty()) {
            brand_name_tv.setError("Enter a name");
            return false;
        }
        if (medicineActivity.timeItems.size() != mPerDay) {
            showAlertDialog("Time");
            return false;
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int noOfTimesPerDay = mPerDay;
        int noOfDoses = noOfTotalTimes = numberPicker.getValue();
        String reminderAlterType = alertType = chipSelected.getText().toString();


        ArrayList<String> takeTime = new ArrayList<>();
        for (int i = 0; i < medicineActivity.timeItems.size(); i++) {
            takeTime.add(medicineActivity.timeItems.get(i).getHour() + ":" + medicineActivity.timeItems.get(i).getMinute());
        }

        JSONObject json = new JSONObject();
        try {
            json.put("timingArrays", new JSONArray(takeTime));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String timingList = json.toString();
        Log.d(TAG, "arrayList:" + timingList);


        MedicineFireStoreModel medicineFireStoreModel = new MedicineFireStoreModel();
        medicineFireStoreModel.setMedicineNDC(medicineNDC);
        medicineFireStoreModel.setMedicineName(medicineName);
        medicineFireStoreModel.setTotalDoses(noOfDoses);
        medicineFireStoreModel.setActiveIngredientName(activeIngredientName);
        medicineFireStoreModel.setActiveIngredientStrength(activeIngredientStrength);
        medicineFireStoreModel.setAlertType(reminderAlterType);
        medicineFireStoreModel.setTimings(timingList);
        medicineFireStoreModel.setDay(day);
        medicineFireStoreModel.setMonth(month);
        medicineFireStoreModel.setYear(year);
        medicineFireStoreModel.setNoOfTimesPerDay(noOfTimesPerDay);


        fireStoreDB.insertNewMedicine(getActivity(), AddDialog.this, medicineFireStoreModel,
                FirebaseAuth.getInstance().getCurrentUser().getEmail());
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());


        databaseHelper.insertNewMedicine(medicineName, day, month, year, noOfTimesPerDay,
                noOfDoses, timingList, reminderAlterType, medicineNDC, activeIngredientName, activeIngredientStrength);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, medicineActivity.timeItems.get(0).getHour());
        Toast.makeText(medicineActivity, "" + medicineActivity.timeItems.get(0).getHour(), Toast.LENGTH_SHORT).show();
        Toast.makeText(medicineActivity, "" + medicineActivity.timeItems.get(0).getMinute(), Toast.LENGTH_SHORT).show();
        calendar.set(Calendar.MINUTE, medicineActivity.timeItems.get(0).getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        switch (alertType) {
            case "Notification":
                setNotification(calendar, medicineName);
                break;
            case "Alarm":
                setAlarm(calendar, medicineName);
                break;
            default:
                setAlarm(calendar, medicineName);
                setNotification(calendar, medicineName);
                break;
        }

        //Log.i("AddDialog.java", calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        return true;
    }

    public void setAlarm(Calendar mAlarmTime, String medicineName) {
        Intent intent = new Intent(getActivity(), AlarmActivity.class);
        intent.putExtra("medicineName", medicineName);

//        PendingIntent operation = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        PendingIntent operation;

// For immutable PendingIntent
        operation = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

// For mutable PendingIntent (only if necessary)
        operation = PendingIntent.getActivity(getContext(), 0, intent, PendingIntent.FLAG_MUTABLE);

        /** Getting a reference to the System Service ALARM_SERVICE */
        AlarmManager alarmManagerNew = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

//        alarmManagerNew.setRepeating(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY * 7, operation);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManagerNew.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);
        } else
            alarmManagerNew.setExact(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);

    }

    private void setNotification(Calendar mNotificationTime, String medicineName) {

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        Intent notificationIntent = new Intent(getContext(), AlarmReceiver.class);
        notificationIntent.putExtra("medicineName", medicineName);
        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, mNotificationTime.getTimeInMillis(), broadcast);
        Toast.makeText(getContext(), mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        Log.d(TAG, mNotificationTime.get(Calendar.HOUR_OF_DAY) + ":" + mNotificationTime.get(Calendar.MINUTE));
    }

    public void showAlertDialog(String nonSelectedItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Please select all the fields or enter all the values to move forward.\n\nNon-selected item(s) found: \n" + nonSelectedItem)
                .setTitle("Select all fields to continue");

        builder.setNeutralButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
