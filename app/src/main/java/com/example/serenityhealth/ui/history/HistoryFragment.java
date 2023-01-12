package com.example.serenityhealth.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.MainActivity;
import com.example.serenityhealth.R;
import com.example.serenityhealth.adapters.ConsultationAdapter;
import com.example.serenityhealth.adapters.HistoryAdapter;
import com.example.serenityhealth.databinding.FragmentHistoryBinding;
import com.example.serenityhealth.helpers.AppointmentDbHelper;
import com.example.serenityhealth.helpers.HistoryDbHelper;
import com.example.serenityhealth.helpers.TimeSlot;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.HistoryModel;
import com.example.serenityhealth.models.UserModel;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    ArrayList<HistoryModel> histories = new ArrayList<>();
    HistoryAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.historyRecyclerView;
        MainActivity main = (MainActivity) getActivity();

        setupHistories(main.getUser());

        adapter = new HistoryAdapter(getContext(), histories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private void setupHistories(UserModel user) {
        histories.clear();
        histories.addAll(HistoryDbHelper.getHistoryByUserUserId(getContext(),user));
        if(adapter!=null){
            adapter.notifyItemInserted(histories.size());
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}