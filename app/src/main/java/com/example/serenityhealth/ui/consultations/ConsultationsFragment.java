package com.example.serenityhealth.ui.consultations;

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
import com.example.serenityhealth.databinding.FragmentConsutationsBinding;
import com.example.serenityhealth.models.ConsultationModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ConsultationsFragment extends Fragment {

    private FragmentConsutationsBinding binding;
    ArrayList<ConsultationModel> consultations = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConsutationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.consultationsRecyclerView;

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