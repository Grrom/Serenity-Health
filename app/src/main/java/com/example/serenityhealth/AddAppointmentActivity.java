package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class AddAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        getSupportActionBar().setTitle("Add Appointment");

        Button addAppointmentButton = findViewById(R.id.add_appointment_button);

        addAppointmentButton.setOnClickListener(view -> {
            onBackPressed();
            Toast.makeText(this, "This feature is not ready yet", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}