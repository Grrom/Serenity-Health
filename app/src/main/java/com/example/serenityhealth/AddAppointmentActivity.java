package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.serenityhealth.helpers.AppointmentDbHelper;
import com.example.serenityhealth.helpers.Constants;
import com.example.serenityhealth.helpers.TimeSlot;
import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.UserModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AddAppointmentActivity extends AppCompatActivity {

    static String TAG = "AddAppointment";
    Calendar today =  Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        UserModel user = (UserModel) getIntent().getSerializableExtra("user");

        getSupportActionBar().setTitle("Add Appointment");

        ImageView image = findViewById(R.id.patient_image);
        TextView name = findViewById(R.id.patient_name);
        TextView weight = findViewById(R.id.patient_weight);
        TextView height = findViewById(R.id.patient_height);

        Button addAppointmentButton = findViewById(R.id.add_appointment_button);
        TextView datePicker = findViewById(R.id.date_input);

        name.setText(user.getFullName());
        weight.setText(user.getWeightKg());
        height.setText(user.getHeightCm());
        image.setImageURI(Uri.parse(user.getImageUri()));

        Spinner pickTimeSlot = findViewById(R.id.time_input);

        datePicker.setOnClickListener(view->{
             DatePickerDialog dp = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> datePicker.setText(Constants.dateFormatter.format(new Date(year-1900, month, dayOfMonth))),today.get(Calendar.YEAR) , today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
             dp.show();
        });


        ArrayList<String> timeSlots = new ArrayList<>();
        for (int i = 0; i < TimeSlot.values().length; i++) {
            timeSlots.add(TimeSlot.values()[i].value);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSlots);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pickTimeSlot.setAdapter(adapter);

        addAppointmentButton.setOnClickListener(view -> {
            try {
                AppointmentDbHelper.createAppointment(this, new ConsultationModel(Constants.dateFormatter.parse(datePicker.getText().toString()), TimeSlot.toTimeSlot( pickTimeSlot.getSelectedItem().toString()), user));
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