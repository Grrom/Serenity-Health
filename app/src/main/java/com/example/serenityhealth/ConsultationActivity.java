package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenityhealth.helpers.AppointmentDbHelper;
import com.example.serenityhealth.helpers.Constants;
import com.example.serenityhealth.helpers.HistoryDbHelper;
import com.example.serenityhealth.helpers.TimeSlot;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.HistoryModel;
import com.example.serenityhealth.models.UserModel;

import java.text.ParseException;
import java.util.Date;

public class ConsultationActivity extends AppCompatActivity {

    private static final String TAG ="ConsultationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        getSupportActionBar().setTitle("Consultation");

        ConsultationModel consultation = (ConsultationModel) getIntent().getSerializableExtra("theConsultation");
        UserModel user = (UserModel) getIntent().getSerializableExtra("theUser");

        ImageView image = findViewById(R.id.patient_image);
        TextView name = findViewById(R.id.patient_name);
        TextView weight = findViewById(R.id.patient_weight);
        TextView height = findViewById(R.id.patient_height);
        TextView date = findViewById(R.id.consultation_date);
        TextView time = findViewById(R.id.consultation_time);

        EditText bpSys = findViewById(R.id.blood_pressure_systolic_input);
        EditText bpDias = findViewById(R.id.blood_pressure_diastolic_input);
        EditText temperature = findViewById(R.id.temperature_input);
        EditText symptoms = findViewById(R.id.symptoms_input);
        EditText diagnosis = findViewById(R.id.diagnosis_input);

        Boolean isHistory = getIntent().getBooleanExtra("isHistory", false);
        Boolean isWalkIn = getIntent().getBooleanExtra("isWalkIn", false);

        Button saveConsultationButton = findViewById(R.id.save_consultation);

        image.setImageURI(Uri.parse(user.getImageUri()));
        name.setText(user.getFullName());
        weight.setText(user.getWeightKg());
        height.setText(user.getHeightCm());

        if(isWalkIn){
            date.setText(Constants.dateFormatter.format(new Date()));
            time.setText(TimeSlot.getTimeSlotNow().value);
        }else{
            date.setText(Constants.dateFormatter.format(consultation.getDate()));
            time.setText(consultation.getTime().value);
        }

        if (isHistory && !isWalkIn) {
            HistoryModel history = (HistoryModel) getIntent().getSerializableExtra("theHistory");
            saveConsultationButton.setVisibility(View.GONE);

            bpSys.setText(history.getBloodPressureSystolic());
            bpDias.setText(history.getBloodPressureDiastolic());
            temperature.setText(history.getTemperature());
            symptoms.setText(history.getSymptoms());
            diagnosis.setText(history.getDiagnosis());

            bpSys.setEnabled(false);
            bpDias.setEnabled(false);
            temperature.setEnabled(false);
            symptoms.setEnabled(false);
            diagnosis.setEnabled(false);
        }

        saveConsultationButton.setOnClickListener(view -> {
            String _bpSys = String.valueOf(bpSys.getText());
            String _bpDias = String.valueOf(bpDias.getText());
            String _temperature = String.valueOf(temperature.getText());
            String _symptoms = String.valueOf(symptoms.getText());
            String _diagnosis = String.valueOf(diagnosis.getText());

            if(_bpSys.isEmpty()|| _bpDias.isEmpty()|| _temperature.isEmpty()||_symptoms.isEmpty()||_diagnosis.isEmpty()){
                Toast.makeText(this, "Please Fill all the fields.", Toast.LENGTH_SHORT).show();
            }

            try {
                HistoryDbHelper.createHistory(this, new HistoryModel("",Constants.dateFormatter.parse(date.getText().toString()), TimeSlot.toTimeSlot(time.getText().toString()), user, _bpSys, _bpDias, _temperature,_symptoms, _diagnosis));
                if(!isWalkIn){
                    AppointmentDbHelper.deleteAppointment(this, consultation.getId());
                }
                onBackPressed();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}