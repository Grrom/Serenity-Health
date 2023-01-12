package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

        UserModel user = (UserModel) getIntent().getSerializableExtra("theUser");

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

        ArrayList<String> timeSlots = new ArrayList<>();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, timeSlots);

        datePicker.setOnClickListener(view->{

             DatePickerDialog dp = new DatePickerDialog(this, (view1, year, month, dayOfMonth) ->{
                 Date selectedDate = new Date(year-1900, month, dayOfMonth);
                 ArrayList<TimeSlot> availableTimeSlots=AppointmentDbHelper.getTimeSlotsAvailableByDate(this,selectedDate );

                 timeSlots.clear();
                 for (int i = 0; i < availableTimeSlots.size(); i++) {
                     timeSlots.add(availableTimeSlots.get(i).value);
                 }

                 adapter.notifyDataSetChanged();

                 if(availableTimeSlots.isEmpty()){
                     Toast.makeText(this, "Sorry there are no available timeslots for that day.",Toast.LENGTH_LONG ).show();
                 }else{
                     datePicker.setText(Constants.dateFormatter.format(new Date(year-1900, month, dayOfMonth)));
                 }
             } ,today.get(Calendar.YEAR) , today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

            dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dp.show();
        });

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pickTimeSlot.setAdapter(adapter);

        addAppointmentButton.setOnClickListener(view -> {
//            Log.e(TAG, TimeSlot.toTimeSlot(pickTimeSlot.getSelectedItem().toString()).value);
//            return;

            if(datePicker.getText().toString().isEmpty()|| pickTimeSlot.getSelectedItem().toString().isEmpty()){
                Toast.makeText(this,"Please fill all the fields.",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                AppointmentDbHelper.createAppointment(this, new ConsultationModel("",Constants.dateFormatter.parse(datePicker.getText().toString()), TimeSlot.toTimeSlot( pickTimeSlot.getSelectedItem().toString()), user));
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