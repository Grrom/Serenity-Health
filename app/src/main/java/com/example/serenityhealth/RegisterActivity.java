package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView toLogin = findViewById(R.id.to_login);
        Button register = findViewById(R.id.register_button);

        register.setOnClickListener(view->{
            startActivity(new Intent(this, MainActivity.class));
        });
        toLogin.setOnClickListener(view->{
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}