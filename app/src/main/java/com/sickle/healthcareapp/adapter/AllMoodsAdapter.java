package com.sickle.healthcareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.moodsModel.Mood;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllMoodsAdapter extends RecyclerView.Adapter<AllMoodsAdapter.MyViewHolder> {

    private final Context mContext;
    //ArrayList<Mood> moodArrayList;


    public AllMoodsAdapter(Context _mContext){
        this.mContext = _mContext;
    }

    @NonNull
    @Override
    public AllMoodsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllMoodsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moods_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllMoodsAdapter.MyViewHolder holder, int position) {



        holder.mood_note_tv.setText(FireStoreDB.allMoodsList.get(position).getSelectedMoodNote());
        holder.mood_date.setText(FireStoreDB.allMoodsList.get(position).getSelectedMoodNoteDate());

        switch (FireStoreDB.allMoodsList.get(position).getSelectedMood()) {
            case "EXCITED":
                holder.mood_iv.setImageResource(R.drawable.excited);
                break;
            case "HAPPY":
                holder.mood_iv.setImageResource(R.drawable.happy);
                break;
            case "NEUTRAL":
                holder.mood_iv.setImageResource(R.drawable.neutal);
                break;
            case "SAD":
                holder.mood_iv.setImageResource(R.drawable.sad);
                break;
            case "DEPRESSED":
                holder.mood_iv.setImageResource(R.drawable.depressed);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return FireStoreDB.allMoodsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mood_iv;

        TextView mood_note_tv, mood_date;


        MyViewHolder(View view) {
            super(view);
            mood_note_tv = view.findViewById(R.id.mood_note_tv);
            mood_iv = view.findViewById(R.id.mood_iv);
            mood_date = view.findViewById(R.id.mood_date);
        }
    }
}
