package com.sickle.healthcareapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;
import com.sickle.healthcareapp.model.painModels.PainAreasResponse;

import java.util.ArrayList;
import java.util.List;

public class MyPainAdapter extends RecyclerView.Adapter<MyPainAdapter.MyViewHolder> {

    private Context mContext;
    public MyPainAdapter(Context _context){
        this.mContext = _context;
    }

    @NonNull
    @Override
    public MyPainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPainAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pain_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPainAdapter.MyViewHolder holder, int position) {

        holder.pain_note_tv.setText(FireStoreDB.allPainList.get(position).getPainNotes());
        holder.pain_date.setText(FireStoreDB.allPainList.get(position).getPainNoteDate());


        Gson gson = new Gson();
        PainAreasResponse response = gson.fromJson(FireStoreDB.allPainList.get(position).getPainAreasList(), PainAreasResponse.class);

        List<String> painAreasList = response.getPainAreasList();

        StringBuilder stringBuilder = new StringBuilder();
        for (String str : painAreasList) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(str);
        }
        String combinedString = stringBuilder.toString();
        holder.pain_areas_tv.setText(combinedString);


        switch (FireStoreDB.allPainList.get(position).getCurrentPainEmotion()) {

            case "NO_PAIN":
                holder.pain_iv.setImageResource(R.drawable.happy);
                break;
            case "LITTLE_PAIN":
                holder.pain_iv.setImageResource(R.drawable.neutal);
                break;
            case "PAIN":
                holder.pain_iv.setImageResource(R.drawable.pain);
                break;
            case "PAINFULL":
                holder.pain_iv.setImageResource(R.drawable.very_painful);
                break;
            case "SEVERE_PAIN":
                holder.pain_iv.setImageResource(R.drawable.severe_pain);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return FireStoreDB.allPainList.size();
    }


    public class Root{
        public ArrayList<String> painAreasList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView pain_iv;

        TextView pain_note_tv, pain_date,pain_areas_tv;


        MyViewHolder(View view) {
            super(view);
            pain_areas_tv = view.findViewById(R.id.pain_areas_tv);
            pain_note_tv = view.findViewById(R.id.pain_note_tv);
            pain_iv = view.findViewById(R.id.pain_iv);
            pain_date = view.findViewById(R.id.pain_date);
        }
    }
}
