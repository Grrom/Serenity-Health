package com.example.serenityhealth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenityhealth.helpers.UserDbHelper;
import com.example.serenityhealth.models.UserModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "REGISTER";

    ImageView profilePicture;
    String imageUri = "";

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

         profilePicture = findViewById(R.id.profile_picture);


        profilePicture.setOnClickListener(view->{
            ImagePicker.with(this)
                    .crop(9f, 9f )
                    .compress(100)
                    .start();
        });


        register.setOnClickListener(view->{
            String _firstName = firstName.getText().toString();
            String _lastName = lastName.getText().toString();
            String _weight = weight.getText().toString();
            String _height = height.getText().toString();
            String _username = username.getText().toString();
            String _password = password.getText().toString();

            if(_firstName.isEmpty()|| _lastName.isEmpty() || _weight.isEmpty()|| _height.isEmpty()||_username.isEmpty()||_password.isEmpty()|| imageUri.isEmpty() ){
                Toast.makeText(this, "Please fill all the fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            UserModel user = new UserModel(_firstName,_lastName, Double.parseDouble(_weight), Double.parseDouble(_height), _username, _password, imageUri,-1);
            double userId = UserDbHelper.createUser(this, user);

            if(userId >-1){
                user.setId(userId);
                Intent intent =new Intent(this, MainActivity.class);
                intent.putExtra("theUser", user);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Failed to create account.", Toast.LENGTH_SHORT).show();
            }
        });

        toLogin.setOnClickListener(view->{
            startActivity(new Intent(this, LoginActivity.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getDataString();
            profilePicture.setImageURI(android.net.Uri.parse(imageUri));
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

}