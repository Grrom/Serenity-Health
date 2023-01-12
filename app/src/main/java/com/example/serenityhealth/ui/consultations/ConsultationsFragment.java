package com.example.serenityhealth.ui.consultations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serenityhealth.AddAppointmentActivity;
import com.example.serenityhealth.ConsultationActivity;
import com.example.serenityhealth.MainActivity;
import com.example.serenityhealth.adapters.ConsultationAdapter;
import com.example.serenityhealth.databinding.FragmentConsutationsBinding;
import com.example.serenityhealth.helpers.AppointmentDbHelper;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class ConsultationsFragment extends Fragment {

    private static final String TAG ="Consultations" ;
    private FragmentConsutationsBinding binding;
    ArrayList<ConsultationModel> consultations = new ArrayList<>();
    ConsultationAdapter adapter ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConsutationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.consultationsRecyclerView;
        Button addWalkin = binding.addWalkInButton;
        FloatingActionButton fab = binding.fab;
        MainActivity main = (MainActivity) getActivity();

        fab.setOnClickListener(view ->{
           Intent intent =  new Intent(getContext(), AddAppointmentActivity.class);
           intent.putExtra("theUser", main.getUser());
           startActivity(intent);
        });

        setupConsultations(main.getUser());

        adapter = new ConsultationAdapter(getContext(), consultations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addWalkin.setOnClickListener(view -> {
            int timeNow = Integer.parseInt(new SimpleDateFormat("k").format(Calendar.getInstance().getTime()));
            Log.e(TAG, String.valueOf(timeNow));

            if(timeNow<9){
                Toast.makeText(getActivity(), "It's too early to walk in, the clinic opens at 9am",Toast.LENGTH_LONG ).show();
                return;
            }
            if(timeNow>=17){
                Toast.makeText(getActivity(), "Sorry the clinic is closed, please come back tomorrow.",Toast.LENGTH_LONG ).show();
                return;
            }

            Intent intent = new Intent(getActivity(), ConsultationActivity.class);
            intent.putExtra("theUser", main.getUser());
            intent.putExtra("isWalkIn", true);
            startActivity(intent);
        });

        return root;
    }

    private void setupConsultations(UserModel user) {
        consultations.clear();
        consultations.addAll(AppointmentDbHelper.getAppointmentsByUserId(getContext(),user));
        if(adapter!=null){
            adapter.notifyItemInserted(consultations.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity main = (MainActivity) getActivity();
        setupConsultations(main.getUser());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}