package com.example.serenityhealth.ui.consultations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.R;
import com.example.serenityhealth.adapters.ConsultationAdapter;
import com.example.serenityhealth.adapters.PatientsSpinnerAdapter;
import com.example.serenityhealth.databinding.FragmentConsutationsBinding;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.PatientModel;

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
        Button addWalkin = binding.addWalkInButton;

        setupConsultations();

        ConsultationAdapter adapter = new ConsultationAdapter(getContext(), consultations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        ArrayList<PatientModel> patients = new ArrayList<>();
        patients.add(new PatientModel(R.drawable.serenity, "Fatima", "Maulion", 30, 140, "Asthma", "BSIT-4.1A"));
        patients.add(new PatientModel(R.drawable.serenity, "Jessa", "Falsado", 30, 140, "Stroke", "BSIT-4.1A"));

        addWalkin.setOnClickListener(view -> {

            final PatientsSpinnerAdapter adp = new PatientsSpinnerAdapter(getActivity(),
                    R.layout.patient_item_spinner, patients);

            final Spinner sp = new Spinner(getContext());
            sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            sp.setAdapter(adp);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(sp);
            builder.create().show();
        });

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