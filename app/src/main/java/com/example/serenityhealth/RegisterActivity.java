package com.example.serenityhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenityhealth.helpers.UserDbHelper;
import com.example.serenityhealth.models.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "REGISTER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView toLogin = findViewById(R.id.to_login);
        Button register = findViewById(R.id.register_button);

        EditText firstName = findViewById(R.id.input_firstName);
        EditText lastName = findViewById(R.id.input_lastName);
        EditText weight = findViewById(R.id.input_weight);
        EditText height = findViewById(R.id.input_height);
        EditText username = findViewById(R.id.input_username);
        EditText password = findViewById(R.id.input_password);

        register.setOnClickListener(view->{
            String _firstName = firstName.getText().toString();
            String _lastName = lastName.getText().toString();
            String _weight = weight.getText().toString();
            String _height = height.getText().toString();
            String _username = username.getText().toString();
            String _password = password.getText().toString();

            if(_firstName.isEmpty()|| _lastName.isEmpty() || _weight.isEmpty()|| _height.isEmpty()||_username.isEmpty()||_password.isEmpty() ){
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserModel user = new UserModel(_firstName,_lastName, Double.parseDouble(_weight), Double.parseDouble(_height), _username, _password);

            if(UserDbHelper.createUser(this, user)){
                Intent intent =new Intent(this, MainActivity.class);
                intent.putExtra("user", user);

                startActivity(intent);
            }else{
                Toast.makeText(this, "Failed to create account.", Toast.LENGTH_SHORT).show();
            }
        });
        toLogin.setOnClickListener(view->{
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}