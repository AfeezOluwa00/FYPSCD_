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

import com.sickle.healthcareapp.fireStoreApi.FireStoreDB;


public class AllHydrationAdapter extends RecyclerView.Adapter<AllHydrationAdapter.MyViewHolder> {

    private final Context mContext;

    public AllHydrationAdapter(Context _mContext) {
        this.mContext = _mContext;
    }

    @NonNull
    @Override
    public AllHydrationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllHydrationAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hydration_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllHydrationAdapter.MyViewHolder holder, int position) {

        holder.hyd_bottle_oz_tv.setText(FireStoreDB.allHydrationList.get(position).getOunceBottleValue()+"");
        holder.hyd_glass_oz_tv.setText(FireStoreDB.allHydrationList.get(position).getOunceGlassValue()+"");
        holder.hyd_total_tv.setText(FireStoreDB.allHydrationList.get(position).getOunceValue()+"");
        holder.hyd_date.setText(FireStoreDB.allHydrationList.get(position).getHydrationDate());
    }

    @Override
    public int getItemCount() {
        return FireStoreDB.allHydrationList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mood_iv;

        TextView hyd_glass_oz_tv, hyd_bottle_oz_tv,hyd_total_tv,hyd_date;


        MyViewHolder(View view) {
            super(view);
            hyd_glass_oz_tv = view.findViewById(R.id.hyd_glass_oz_tv);
            hyd_total_tv = view.findViewById(R.id.hyd_total_tv);
            hyd_date = view.findViewById(R.id.hyd_date);
            hyd_bottle_oz_tv = view.findViewById(R.id.hyd_bottle_oz_tv);
        }
    }
}
