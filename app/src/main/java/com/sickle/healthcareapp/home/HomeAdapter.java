package com.sickle.healthcareapp.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysicklecellapp.R;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textview.MaterialTextView;
import com.sickle.healthcareapp.Interface.ItemClickListener;
import com.sickle.healthcareapp.db.DatabaseHelper;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<HomeItem> homeItems;
    private Context context;
    private Activity activity;
    ItemClickListener itemClickListener;
    public HomeAdapter(List<HomeItem> homeItems, Context context,Activity activity1,ItemClickListener itemClickListener) {
        this.homeItems = homeItems;
        this.context = context;
        activity=activity1;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        HomeItem homeItem = homeItems.get(position);

        holder.textMedicine.setText(homeItem.getMedicineName());
        holder.textDosageSummary.setText("Dose  "+homeItem.getDose() );
        holder.mdose.setText(homeItem.getDose()+ " times a day");

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)

                    holder.imageButtonDelete.setVisibility(View.VISIBLE);
                else
                    holder.imageButtonDelete.setVisibility(View.GONE);
            }
        });
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.deleteMedicine(holder.textMedicine.getText().toString());
                holder.cardView.setVisibility(View.GONE);
//                Toast.makeText(context, holder.textMedicine.getText().toString() + " deleted", Toast.LENGTH_LONG).show();
            }
        });
        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rect displayRectangle = new Rect();
                Window window = activity.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);

                View mView = activity.getLayoutInflater().inflate(R.layout.fragment_update, null);
                mView.setMinimumWidth((int)(displayRectangle.width() * 1f));
                mView.setMinimumHeight((int)(displayRectangle.height() * 1f));
                alert.setView(mView);
                EditText name = (EditText) mView.findViewById(R.id.etName);
                TextView update = (TextView) mView.findViewById(R.id.update);
                EditText dose = (EditText) mView.findViewById(R.id.noofDose);
                EditText schedule = (EditText) mView.findViewById(R.id.schedule);
                EditText time = (EditText) mView.findViewById(R.id.etTime);
                time.setText(homeItem.getTimings());
                String nameMedicine=homeItem.getMedicineName();
                dose.setText(homeItem.getDose());
                schedule.setText(homeItem.getDosageSummary());
                name.setText(nameMedicine);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);


                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(mView.getContext(),"updated..!!",Toast.LENGTH_SHORT).show();
                        int mdose= 1;
                        int day=1;
                        int month=12;
                        int year=2023;
                        int noOfTimesPerDay =2;

                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.updateMedicine(homeItem.getMedicineName(),name.getText().toString(), day, month, year, Integer.parseInt(schedule.getText().toString()), Integer.parseInt(dose.getText().toString()), time.getText().toString().trim(), "reminderAlterType");

                        itemClickListener.onClick(position);

                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return homeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView textMedicine, textDosageSummary,mdose;
        CardView cardView;
        MaterialCheckBox checkBox;
        ImageButton imageButtonDelete;
        Button editbutton;

        ViewHolder(View itemView) {
            super(itemView);
            mdose=itemView.findViewById(R.id.dose);
            editbutton = itemView.findViewById(R.id.editbutton);
            textDosageSummary = itemView.findViewById(R.id.dosage_text_view);
            textMedicine = itemView.findViewById(R.id.medicine_name_text_view);
            cardView = itemView.findViewById(R.id.card_view_medicine);
            checkBox = itemView.findViewById(R.id.medicine_checkbox);
            imageButtonDelete = itemView.findViewById(R.id.medicine_delete_button);

        }
    }
}
