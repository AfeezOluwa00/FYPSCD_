package com.sickle.healthcareapp.home;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mysicklecellapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.Utilities;
import com.sickle.healthcareapp.MyHydration;
import com.sickle.healthcareapp.db.DatabaseHelper;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.hydrationModel.HydrationModel;

public class AddHydrationDialog extends DialogFragment {


    MyHydration myHydration;
    private DatabaseHelper databaseHelper;

    FireStoreDB fireStoreDB;

    private EditText hyd_bottle_oz_edt,hyd_glass_oz_edt,hyd_manual_entry_oz_edt;
    private MaterialToolbar toolbar;
    private Button add_hydration_btn;

    public AddHydrationDialog(MyHydration _myHydration) {
        this.myHydration = _myHydration;
    }

    private void calculateTotal() {
        // Get values from EditTexts and sum them up
        int bottleOz = parseEditTextValue(hyd_bottle_oz_edt);
        double totalBottleOz = bottleOz * 16.9;
        int glassOz = parseEditTextValue(hyd_glass_oz_edt);
        double totalGlassOz = glassOz * 8;
        double totalOz = totalBottleOz + totalGlassOz;

        hyd_manual_entry_oz_edt.setText(String.valueOf(totalOz));
    }

    private int parseEditTextValue(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(text);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View frgView = inflater.inflate(R.layout.add_hydration_dialog, container, false);

        fireStoreDB = new FireStoreDB();

        hyd_manual_entry_oz_edt = frgView.findViewById(R.id.hyd_manual_entry_oz_edt);

        hyd_bottle_oz_edt = frgView.findViewById(R.id.hyd_bottle_oz_edt);
        hyd_bottle_oz_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        hyd_glass_oz_edt = frgView.findViewById(R.id.hyd_glass_oz_edt);
        hyd_glass_oz_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        add_hydration_btn = frgView.findViewById(R.id.add_hydration_btn);
        add_hydration_btn.setOnClickListener(view->{

            if(hyd_glass_oz_edt.getText().toString().trim().isEmpty() || hyd_bottle_oz_edt.getText().toString().trim().isEmpty()){
                Toast.makeText(getActivity(),"Please enter water ounces in bottle or glass",Toast.LENGTH_SHORT).show();
                return;
            }

            if(Double.parseDouble(hyd_manual_entry_oz_edt.getText().toString().trim())==0.0){
                Toast.makeText(getActivity(),"Total water ounces cannot be 0\nEnter water or glass ounces",Toast.LENGTH_SHORT).show();
                return;
            }

            HydrationModel hydrationModel = new HydrationModel();
            hydrationModel.setHydrationDate(Utilities.getCurrentDate());
            hydrationModel.setOunceBottleValue(Integer.parseInt(hyd_bottle_oz_edt.getText().toString().trim()));
            hydrationModel.setOunceGlassValue(Integer.parseInt(hyd_glass_oz_edt.getText().toString().trim()));
            hydrationModel.setOunceValue(Double.parseDouble(hyd_manual_entry_oz_edt.getText().toString().trim()));


            fireStoreDB.insertNewHydrationData(getActivity(),AddHydrationDialog.this,hydrationModel,
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());


//            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//
//            if(databaseHelper.insertNewHydration(hydrationModel) == -1){
//                Toast.makeText(getActivity(),"error while inserting Hydration values",Toast.LENGTH_SHORT).show();
//            }else{
//
//                hyd_glass_oz_edt.setText("0");
//                hyd_bottle_oz_edt.setText("0");
//                hyd_manual_entry_oz_edt.setText("0");
//
//
//                Toast.makeText(getActivity(),"Mood values inserted successfully",Toast.LENGTH_SHORT).show();
//            }


        });

        toolbar = frgView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        return frgView;
    }

    public void onHydrationInserted(boolean inserted, String errorMessage) {
        if(inserted){

            hyd_glass_oz_edt.setText("0");
            hyd_bottle_oz_edt.setText("0");
            hyd_manual_entry_oz_edt.setText("0");

            dismiss();
            Toast.makeText(getActivity(),"Mood values inserted successfully",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Error Inserting Medicine Data " + errorMessage, Toast.LENGTH_LONG).show();
        }
    }
}
