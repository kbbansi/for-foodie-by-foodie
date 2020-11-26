package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRestaurant, btnStreetFood, btnUsers;
    BottomNavigationView bottomNavigationView;
    static final String TAG = "BUTTON_PRESSED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnRestaurant = findViewById(R.id.restaurantButton);
        btnStreetFood = findViewById(R.id.streetFoodButton);
        btnUsers = findViewById(R.id.usersButton);

        // initialize bottom nav
        bottomNavigationView = findViewById(R.id.bottom_nav);

        // obsolete code
        btnRestaurant.setOnClickListener(this);
        btnStreetFood.setOnClickListener(this);
        btnUsers.setOnClickListener(this);

        // set dashboard as selected view
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        return true;

                    case R.id.streetFood_tab:
                        startActivity(new Intent(getApplicationContext(), AdminStreet.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profile_tab:
                        startActivity(new Intent(getApplicationContext(), AdminProfile.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.restaurants_tab:
                        startActivity(new Intent(getApplicationContext(), AdminRestaurant.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.users_tab:
                        startActivity(new Intent(getApplicationContext(), AdminViewUsers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurantButton:
                Log.d(TAG, "Starting AdminRestaurant Activity");
                startActivity(new Intent(getApplicationContext(), AdminRestaurant.class));
                break;

            case R.id.streetFoodButton:
                Log.d(TAG, "Starting AdminStreetFood Activity");
                // start an activity
                startActivity(new Intent(this, AdminStreet.class));
                break;

            case R.id.usersButton:
                Log.d(TAG, "Starting ViewUsers Activity");
                // start an activity
                startActivity(new Intent(this, AdminViewUsers.class));
                finish();
                break;
        }
    }
}
