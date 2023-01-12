package com.example.serenityhealth.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.ConsultationActivity;
import com.example.serenityhealth.R;
import com.example.serenityhealth.helpers.Constants;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.HistoryModel;

import java.util.ArrayList;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<HistoryModel> histories;

    public HistoryAdapter(Context context, ArrayList<HistoryModel> histories) {
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.consultation_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyViewHolder holder, int position) {
        holder.date.setText(Constants.dateFormatter.format(histories.get(position).getDate()));
        holder.time.setText(histories.get(position).getTime().value);

        holder.proceedButton.setText("View");
        holder.proceedButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, ConsultationActivity.class);
            intent.putExtra("isHistory", true);
            intent.putExtra("theConsultation", histories.get(position));
            intent.putExtra("theHistory", histories.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView time;
        Button proceedButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.consultation_date);
            time = itemView.findViewById(R.id.consultation_time);
            proceedButton = itemView.findViewById(R.id.proceed_consultation);
        }
    }
}
