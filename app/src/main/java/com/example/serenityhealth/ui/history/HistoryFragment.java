package com.example.serenityhealth.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.R;
import com.example.serenityhealth.adapters.ConsultationAdapter;
import com.example.serenityhealth.databinding.FragmentHistoryBinding;
import com.example.serenityhealth.models.ConsultationModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    ArrayList<ConsultationModel> consultations = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.historyRecyclerView;

        setupConsultations();

        ConsultationAdapter adapter = new ConsultationAdapter(getContext(), consultations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private void setupConsultations() {
        consultations.add(new ConsultationModel(R.drawable.serenity, 1, new Date(), new Time(10)));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}