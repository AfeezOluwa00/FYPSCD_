package com.sickle.healthcareapp.home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mysicklecellapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.Utilities;
import com.sickle.healthcareapp.MyMoodActivity;
import com.sickle.healthcareapp.MyPainActivity;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.painModels.PainModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddPainDialog extends DialogFragment implements CompoundButton.OnCheckedChangeListener {


    FireStoreDB fireStoreDB;
    private CheckBox filter_no_pain_chb, filter_front_head_chb, filter_back_head_chb, filter_whole_head_chb,
            filter_neck_pain_chb, filter_throat_chb, filter_left_ear_chb, filter_right_ear_chb, filter_lips_pain_chb,
            filter_nose_chb, filter_left_eye_chb, filter_right_eye_chb, filter_left_shoulder_pain_chb, filter_right_shoulder_pain_chb,
            filter_left_chest_chb, filter_right_chest_pain_chb, filter_right_arm_pain_chb, filter_left_arm_chb, filter_chest_pain_chb,
            filter_abdominal_pain_chb, filter_right_abdomin_pain_chb, filter_left_abdomin_pain_chb, filter_upper_back_pain_chb,
            filter_lower_back_pain_chb, filter_whole_back_pain_chb, filter_right_leg_chb, filter_left_leg_chb, filter_right_knee_chb,
            filter_left_knee_chb, filter_right_foot_chb, filter_left_foot_chb;

    ArrayList<String> selectedPainChb = new ArrayList<>();

    private ImageView select_severe_pain_iv, select_very_pain_iv, select_pain_iv, select_little_pain_iv, select_no_pain_iv;
    private ImageView severe_pain_iv, very_pain_iv, pain_iv, little_pain_iv, no_pain_iv;
    private TextView selected_pain_tv, pain_level_count_tv;
    MyPainActivity myPainActivity;
    private Button add_pain_btn;
    private MaterialToolbar toolbar;
    private EditText pain_notes_edt;

    private SeekBar pain_level_skb;
    PainEmotion currentPainEmotion;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.filter_no_pain_chb:

                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;
            case R.id.filter_front_head_chb:
                // Handle checkbox2 state change
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_back_head_chb:

                if (isChecked) {
                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_neck_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }

            case R.id.filter_throat_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;
            case R.id.filter_left_ear_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_ear_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_lips_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_nose_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_eye_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_eye_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_shoulder_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_shoulder_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;
            case R.id.filter_left_chest_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_chest_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_arm_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_arm_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_chest_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_abdominal_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;


            case R.id.filter_right_abdomin_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_abdomin_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_upper_back_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_lower_back_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_whole_back_pain_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;


            case R.id.filter_right_leg_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_leg_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_knee_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_knee_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_right_foot_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_left_foot_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;

            case R.id.filter_whole_head_chb:
                if (isChecked) {

                    selectedPainChb.add(((CheckBox) buttonView).getText().toString());
                } else {
                    selectedPainChb.remove(((CheckBox) buttonView).getText().toString());
                }
                break;
            // Add cases for more checkboxes as needed...
        }
    }

    public void onPainValueInserted(boolean inserted, String errorMessage) {
        if(inserted){
            selectedPainChb.clear();
            currentPainEmotion = null;
            pain_level_count_tv.setText("0");
            pain_notes_edt.setText("");
            dismiss();
            Toast.makeText(getActivity(), "Pain data inserted successfully ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getActivity(), "Error inserting pain data " + errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public enum PainEmotion {
        NO_PAIN,
        LITTLE_PAIN,
        PAIN,
        PAINFULL,
        SEVERE_PAIN
    }


    private void unSelectedBackgroundOfSmiles() {
        select_severe_pain_iv.setVisibility(View.GONE);
        select_very_pain_iv.setVisibility(View.GONE);
        select_pain_iv.setVisibility(View.GONE);
        select_little_pain_iv.setVisibility(View.GONE);
        select_no_pain_iv.setVisibility(View.GONE);
    }


    public AddPainDialog(MyPainActivity _myPainActivity) {
        this.myPainActivity = _myPainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
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
        View frgView = inflater.inflate(R.layout.add_pain_dialog, container, false);

        fireStoreDB = new FireStoreDB();
        selectedPainChb.clear();

        Toast.makeText(getActivity(), FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();

        filter_no_pain_chb = frgView.findViewById(R.id.filter_no_pain_chb);
        filter_no_pain_chb.setOnCheckedChangeListener(this);
        filter_front_head_chb = frgView.findViewById(R.id.filter_front_head_chb);
        filter_front_head_chb.setOnCheckedChangeListener(this);
        filter_back_head_chb = frgView.findViewById(R.id.filter_back_head_chb);
        filter_back_head_chb.setOnCheckedChangeListener(this);
        filter_whole_head_chb = frgView.findViewById(R.id.filter_whole_head_chb);
        filter_whole_head_chb.setOnCheckedChangeListener(this);
        filter_neck_pain_chb = frgView.findViewById(R.id.filter_neck_pain_chb);
        filter_neck_pain_chb.setOnCheckedChangeListener(this);
        filter_throat_chb = frgView.findViewById(R.id.filter_throat_chb);
        filter_throat_chb.setOnCheckedChangeListener(this);
        filter_left_ear_chb = frgView.findViewById(R.id.filter_left_ear_chb);
        filter_left_ear_chb.setOnCheckedChangeListener(this);
        filter_right_ear_chb = frgView.findViewById(R.id.filter_right_ear_chb);
        filter_right_ear_chb.setOnCheckedChangeListener(this);
        filter_lips_pain_chb = frgView.findViewById(R.id.filter_lips_pain_chb);
        filter_lips_pain_chb.setOnCheckedChangeListener(this);
        filter_nose_chb = frgView.findViewById(R.id.filter_nose_chb);
        filter_nose_chb.setOnCheckedChangeListener(this);
        filter_left_eye_chb = frgView.findViewById(R.id.filter_left_eye_chb);
        filter_left_eye_chb.setOnCheckedChangeListener(this);
        filter_right_eye_chb = frgView.findViewById(R.id.filter_right_eye_chb);
        filter_right_eye_chb.setOnCheckedChangeListener(this);
        filter_left_shoulder_pain_chb = frgView.findViewById(R.id.filter_left_shoulder_pain_chb);
        filter_left_shoulder_pain_chb.setOnCheckedChangeListener(this);
        filter_right_shoulder_pain_chb = frgView.findViewById(R.id.filter_right_shoulder_pain_chb);
        filter_right_shoulder_pain_chb.setOnCheckedChangeListener(this);
        filter_left_chest_chb = frgView.findViewById(R.id.filter_left_chest_chb);
        filter_left_chest_chb.setOnCheckedChangeListener(this);
        filter_right_chest_pain_chb = frgView.findViewById(R.id.filter_right_chest_pain_chb);
        filter_right_chest_pain_chb.setOnCheckedChangeListener(this);
        filter_right_arm_pain_chb = frgView.findViewById(R.id.filter_right_arm_pain_chb);
        filter_right_arm_pain_chb.setOnCheckedChangeListener(this);
        filter_left_arm_chb = frgView.findViewById(R.id.filter_left_arm_chb);
        filter_left_arm_chb.setOnCheckedChangeListener(this);
        filter_chest_pain_chb = frgView.findViewById(R.id.filter_chest_pain_chb);
        filter_chest_pain_chb.setOnCheckedChangeListener(this);
        filter_abdominal_pain_chb = frgView.findViewById(R.id.filter_abdominal_pain_chb);
        filter_abdominal_pain_chb.setOnCheckedChangeListener(this);
        filter_right_abdomin_pain_chb = frgView.findViewById(R.id.filter_right_abdomin_pain_chb);
        filter_right_abdomin_pain_chb.setOnCheckedChangeListener(this);
        filter_left_abdomin_pain_chb = frgView.findViewById(R.id.filter_left_abdomin_pain_chb);
        filter_left_abdomin_pain_chb.setOnCheckedChangeListener(this);
        filter_upper_back_pain_chb = frgView.findViewById(R.id.filter_upper_back_pain_chb);
        filter_upper_back_pain_chb.setOnCheckedChangeListener(this);
        filter_lower_back_pain_chb = frgView.findViewById(R.id.filter_lower_back_pain_chb);
        filter_lower_back_pain_chb.setOnCheckedChangeListener(this);
        filter_whole_back_pain_chb = frgView.findViewById(R.id.filter_whole_back_pain_chb);
        filter_whole_back_pain_chb.setOnCheckedChangeListener(this);
        filter_right_leg_chb = frgView.findViewById(R.id.filter_right_leg_chb);
        filter_right_leg_chb.setOnCheckedChangeListener(this);
        filter_left_leg_chb = frgView.findViewById(R.id.filter_left_leg_chb);
        filter_left_leg_chb.setOnCheckedChangeListener(this);
        filter_right_knee_chb = frgView.findViewById(R.id.filter_right_knee_chb);
        filter_right_knee_chb.setOnCheckedChangeListener(this);
        filter_left_knee_chb = frgView.findViewById(R.id.filter_left_knee_chb);
        filter_left_knee_chb.setOnCheckedChangeListener(this);
        filter_right_foot_chb = frgView.findViewById(R.id.filter_right_foot_chb);
        filter_right_foot_chb.setOnCheckedChangeListener(this);
        filter_left_foot_chb = frgView.findViewById(R.id.filter_left_foot_chb);
        filter_left_foot_chb.setOnCheckedChangeListener(this);


        pain_notes_edt = frgView.findViewById(R.id.pain_notes_edt);
        add_pain_btn = frgView.findViewById(R.id.add_pain_btn);
        add_pain_btn.setOnClickListener(view -> {
            if (currentPainEmotion == null) {
                Toast.makeText(getActivity(), "Please select any of the Pain situation currently ", Toast.LENGTH_LONG).show();
                return;
            }


            if (pain_notes_edt.getText().toString().trim().isEmpty()) {
                Toast.makeText(getActivity(), "Please write the pain notes to proceed ", Toast.LENGTH_LONG).show();
                return;
            }

            if(selectedPainChb.size()==0){
                Toast.makeText(getActivity(), "Please select the pain areas to proceed ", Toast.LENGTH_LONG).show();
                return;
            }

            Toast.makeText(myPainActivity,selectedPainChb.toString() , Toast.LENGTH_SHORT).show();


            JSONObject json = new JSONObject();
            try {
                json.put("painAreasList", new JSONArray(selectedPainChb));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String painAreasList = json.toString();
            Log.d("AddPainDialog", "arrayList:" + painAreasList);

            PainModel painModel = new PainModel();
            painModel.setPainLevel(Integer.parseInt(pain_level_count_tv.getText().toString().toString().trim()));
            painModel.setPainNotes(pain_notes_edt.getText().toString().trim());
            painModel.setCurrentPainEmotion(currentPainEmotion.toString());
            painModel.setPainAreasList(painAreasList);
            painModel.setPainNoteDate(Utilities.getCurrentDate());


            fireStoreDB.insertPainData(getActivity(),AddPainDialog.this,painModel,
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());

        });


        pain_level_count_tv = frgView.findViewById(R.id.pain_level_count_tv);
        pain_level_skb = frgView.findViewById(R.id.pain_level_skb);
        pain_level_skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pain_level_count_tv.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        selected_pain_tv = frgView.findViewById(R.id.selected_pain_tv);

        severe_pain_iv = frgView.findViewById(R.id.severe_pain_iv);
        severe_pain_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_severe_pain_iv.setVisibility(View.VISIBLE);
            selected_pain_tv.setText("Selected Pain: Severe");
            currentPainEmotion = PainEmotion.SEVERE_PAIN;
        });
        very_pain_iv = frgView.findViewById(R.id.very_pain_iv);
        very_pain_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_very_pain_iv.setVisibility(View.VISIBLE);
            selected_pain_tv.setText("Selected Pain: Painfull");
            currentPainEmotion = PainEmotion.PAINFULL;
        });


        pain_iv = frgView.findViewById(R.id.pain_iv);
        pain_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_pain_iv.setVisibility(View.VISIBLE);
            selected_pain_tv.setText("Selected Pain: Pain");
            currentPainEmotion = PainEmotion.PAIN;
        });

        little_pain_iv = frgView.findViewById(R.id.little_pain_iv);
        little_pain_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_little_pain_iv.setVisibility(View.VISIBLE);
            selected_pain_tv.setText("Selected Pain: Little Pain");
            currentPainEmotion = PainEmotion.LITTLE_PAIN;
        });

        no_pain_iv = frgView.findViewById(R.id.no_pain_iv);
        no_pain_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_no_pain_iv.setVisibility(View.VISIBLE);
            selected_pain_tv.setText("Selected Pain: No Pain");
            currentPainEmotion = PainEmotion.NO_PAIN;
        });


        select_severe_pain_iv = frgView.findViewById(R.id.select_severe_pain_iv);
        select_very_pain_iv = frgView.findViewById(R.id.select_very_pain_iv);
        select_pain_iv = frgView.findViewById(R.id.select_pain_iv);
        select_little_pain_iv = frgView.findViewById(R.id.select_little_pain_iv);
        select_no_pain_iv = frgView.findViewById(R.id.select_no_pain_iv);


        toolbar = frgView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {

            dismiss();
        });


        return frgView;
    }
}
