package com.example.serenityhealth.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.ConsultationActivity;
import com.example.serenityhealth.R;
import com.example.serenityhealth.helpers.Constants;
import com.example.serenityhealth.helpers.TimeSlot;
import com.example.serenityhealth.models.ConsultationModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.MyViewHolder> {
    Context context;
    ArrayList<ConsultationModel> consultations;

    public ConsultationAdapter(Context context, ArrayList<ConsultationModel> consultations) {
        this.context = context;
        this.consultations = consultations;
    }

    @NonNull
    @Override
    public ConsultationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConsultationAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.consultation_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationAdapter.MyViewHolder holder, int position) {
        holder.date.setText(Constants.dateFormatter.format(consultations.get(position).getDate()));
        holder.time.setText(consultations.get(position).getTime().value);
        holder.diagnosisLayout.setVisibility(View.GONE);

        Date dateNow = Calendar.getInstance().getTime();

        boolean isToday = Constants.dateFormatter.format(dateNow).equals(Constants.dateFormatter.format(consultations.get(position).getDate()));
        boolean isAtThisTimeSlot = TimeSlot.getTimeSlotNow().value.equalsIgnoreCase(consultations.get(position).getTime().value);

        holder.proceedButton.setEnabled(false);
        holder.proceedButton.setText("UPCOMING");

        if(isToday && isAtThisTimeSlot){
            holder.proceedButton.setEnabled(true);
            holder.proceedButton.setText("PROCEED");
        }

        holder.proceedButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, ConsultationActivity.class);
            intent.putExtra("theConsultation", consultations.get(position));
            intent.putExtra("theUser", consultations.get(position).getUser());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        Button proceedButton;
        LinearLayout diagnosisLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.consultation_date);
            time = itemView.findViewById(R.id.consultation_time);
            diagnosisLayout= itemView.findViewById(R.id.diagnosis_layout);
            proceedButton = itemView.findViewById(R.id.proceed_consultation);
        }
    }
}
