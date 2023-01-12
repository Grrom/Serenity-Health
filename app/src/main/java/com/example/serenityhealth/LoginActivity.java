package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenityhealth.helpers.UserDbHelper;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.login_button);
        TextView toSignup = findViewById(R.id.to_signup);

        EditText username = findViewById(R.id.input_username);
        EditText password = findViewById(R.id.input_password);

        loginButton.setOnClickListener(view -> {
            String _username = username.getText().toString();
            String _password = password.getText().toString();

            if(_username.isEmpty()|| _password.isEmpty()){
                Toast.makeText(this,"Please Fill all the fields.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                Intent intent =new Intent(this, MainActivity.class);
                intent.putExtra("theUser",UserDbHelper.loginUser(this, _username, _password));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        toSignup.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}