package com.example.serenityhealth;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.serenityhealth.models.UserModel;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.serenityhealth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AddAppointmentActivity.class)));
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        LinearLayout navHeaderView= (LinearLayout) navigationView.getHeaderView(0);

        ImageView image= (ImageView) navHeaderView.findViewById(R.id.user_image);
        TextView name= (TextView) navHeaderView.findViewById(R.id.user_name);
        TextView weight= (TextView) navHeaderView.findViewById(R.id.user_weight);
        TextView height= (TextView) navHeaderView.findViewById(R.id.user_height);

        Log.e(TAG, name.getText().toString() );

        UserModel user = (UserModel)getIntent().getSerializableExtra("user");

        image.setImageURI(Uri.parse(user.getImageUri()));
        name.setText(user.getFullName());
        weight.setText(user.getWeightKg());
        height.setText(user.getHeightCm());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}