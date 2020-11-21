package com.example.henryproject_ffbf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRestaurant, btnStreetFood, btnUsers;
    static final String TAG = "BUTTON_PRESSED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnRestaurant = findViewById(R.id.restaurantButton);
        btnStreetFood = findViewById(R.id.streetFoodButton);
        btnUsers = findViewById(R.id.usersButton);

        btnRestaurant.setOnClickListener(this);
        btnStreetFood.setOnClickListener(this);
        btnUsers.setOnClickListener(this);
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
                startActivity(new Intent(getApplicationContext(), AdminStreet.class));
                break;

            case R.id.usersButton:
                Log.d(TAG, "Starting ViewUsers Activity");
                // start an activity
                finish();
                break;
        }
    }
}
