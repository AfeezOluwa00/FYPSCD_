package com.sickle.healthcareapp.home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.mysicklecellapp.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.sickle.healthcareapp.Common.Utilities;
import com.sickle.healthcareapp.MyMoodActivity;
import com.sickle.healthcareapp.db.DatabaseHelper;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.moodsModel.Mood;

import java.util.Calendar;

public class AddMoodDialog extends DialogFragment {


    Emotion currentEmotion;
    FireStoreDB fireStoreDB;

    public void onMoodInserted(boolean inserted, String errorMessage) {
        if(inserted){
            notes_edt.setText("");
            currentEmotion = null;
            unSelectedBackgroundOfSmiles();
            emotion_level_skb.setProgress(0);
            level_count_tv.setText("0");

            Toast.makeText(getActivity(),"Mood values inserted successfully",Toast.LENGTH_SHORT).show();
            dismiss();
        }else{
            Toast.makeText(getActivity(), "Error Inserting Mood Data " + errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    public enum Emotion {
        EXCITED,
        HAPPY,
        NEUTRAL,
        SAD,
        DEPRESSED
    }
    private TextView level_count_tv,selected_emotion_tv;


    private MaterialToolbar toolbar;
    private SeekBar emotion_level_skb;
    private ImageView select_depressed_iv, select_sad_iv, select_neutral_iv, select_happy_iv, select_excited_iv;
    private ImageView depressed_iv, sad_iv, neutral_iv, happy_iv, excited_iv;
    public MyMoodActivity moodActivity;

    private Button add_mood_btn;
    private EditText notes_edt;



    public AddMoodDialog(MyMoodActivity myMoodActivity) {
        this.moodActivity = myMoodActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View frgView = inflater.inflate(R.layout.add_mood_dialog, container, false);

        fireStoreDB = new FireStoreDB();

        selected_emotion_tv = frgView.findViewById(R.id.selected_emotion_tv);
        toolbar = frgView.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> {

            dismiss();
        });


        emotion_level_skb = frgView.findViewById(R.id.emotion_level_skb);
        emotion_level_skb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                level_count_tv.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        level_count_tv = frgView.findViewById(R.id.level_count_tv);


        notes_edt = frgView.findViewById(R.id.notes_edt);
        add_mood_btn = frgView.findViewById(R.id.add_mood_btn);

        depressed_iv = frgView.findViewById(R.id.depressed_iv);
        sad_iv = frgView.findViewById(R.id.sad_iv);
        neutral_iv = frgView.findViewById(R.id.neutral_iv);
        happy_iv = frgView.findViewById(R.id.happy_iv);
        excited_iv = frgView.findViewById(R.id.excited_iv);


        select_depressed_iv = frgView.findViewById(R.id.select_depressed_iv);
        select_sad_iv = frgView.findViewById(R.id.select_sad_iv);
        select_neutral_iv = frgView.findViewById(R.id.select_neutral_iv);
        select_happy_iv = frgView.findViewById(R.id.select_happy_iv);
        select_excited_iv = frgView.findViewById(R.id.select_excited_iv);


        depressed_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_depressed_iv.setVisibility(View.VISIBLE);
            selected_emotion_tv.setText("Selected Mood: Depressed" );
            currentEmotion = Emotion.DEPRESSED;
        });

        sad_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_sad_iv.setVisibility(View.VISIBLE);
            selected_emotion_tv.setText("Selected Mood: Sad" );
            currentEmotion = Emotion.SAD;
        });

        neutral_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_neutral_iv.setVisibility(View.VISIBLE);
            selected_emotion_tv.setText("Selected Mood: Neutral" );
            currentEmotion = Emotion.NEUTRAL;
        });

        happy_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_happy_iv.setVisibility(View.VISIBLE);
            selected_emotion_tv.setText("Selected Mood: Happy" );
            currentEmotion = Emotion.HAPPY;
        });

        excited_iv.setOnClickListener(view -> {
            unSelectedBackgroundOfSmiles();
            select_excited_iv.setVisibility(View.VISIBLE);
            selected_emotion_tv.setText("Selected Mood: Excited" );
            currentEmotion = Emotion.EXCITED;
        });


        add_mood_btn.setOnClickListener(view->{

            //Toast.makeText(getActivity(),level_count_tv.getText().toString(),Toast.LENGTH_SHORT).show();

            if(currentEmotion==null){
                Toast.makeText(getActivity(),"Select any emotion to proceed",Toast.LENGTH_SHORT).show();
                return;
            }

            if(notes_edt.getText().toString().trim().isEmpty()){
                Toast.makeText(getActivity(),"Write your notes to proceed",Toast.LENGTH_SHORT).show();
                return;
            }


            Mood moodModel = new Mood();
            moodModel.setSelectedMood(currentEmotion.toString());
            moodModel.setSelectedMoodLevel(Integer.parseInt(level_count_tv.getText().toString()));
            moodModel.setSelectedMoodNoteDate(Utilities.getCurrentDate());
            moodModel.setSelectedMoodNote(notes_edt.getText().toString().trim());


            fireStoreDB.insertNewMoodData(getActivity(),AddMoodDialog.this,moodModel,
                    FirebaseAuth.getInstance().getCurrentUser().getEmail());


//            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//
//            if(databaseHelper.insertNewMood(moodModel) == -1){
//                Toast.makeText(getActivity(),"error while inserting Mood values",Toast.LENGTH_SHORT).show();
//            }else{
//
//                notes_edt.setText("");
//                currentEmotion = null;
//                unSelectedBackgroundOfSmiles();
//                emotion_level_skb.setProgress(0);
//                level_count_tv.setText("0");
//
//                Toast.makeText(getActivity(),"Mood values inserted successfully",Toast.LENGTH_SHORT).show();
//            }
        });


        return frgView;
    }


    private void unSelectedBackgroundOfSmiles() {
        select_depressed_iv.setVisibility(View.GONE);
        select_sad_iv.setVisibility(View.GONE);
        select_neutral_iv.setVisibility(View.GONE);
        select_happy_iv.setVisibility(View.GONE);
        select_excited_iv.setVisibility(View.GONE);
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
}
