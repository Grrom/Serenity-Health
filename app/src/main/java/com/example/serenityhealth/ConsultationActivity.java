package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ConsultationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        getSupportActionBar().setTitle("Consultation");

        Boolean isHistory = getIntent().getBooleanExtra("isHistory", false);

        Button saveConsultationButton = findViewById(R.id.save_consultation);

        if (isHistory) {
            saveConsultationButton.setVisibility(View.GONE);
        }

        saveConsultationButton.setOnClickListener(view -> {
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