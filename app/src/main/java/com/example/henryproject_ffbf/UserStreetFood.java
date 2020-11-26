package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserStreetFood extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_street_food);

        floatingActionButton = findViewById(R.id.addStreetVendor);
        ListView listView = findViewById(R.id.listView);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        floatingActionButton.setOnClickListener(this);

        bottomNavigationView.setSelectedItemId(R.id.streetFood_tab);


        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("StreetFood");

        final ArrayList<String> streetVendorList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.restaurant_cell, streetVendorList);
        listView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                streetVendorList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        StreetFood streetFood = snapshot.getValue(StreetFood.class);

                        // get restaurant name
                        String name = streetFood.getName();
                        streetVendorList.add(name);
                        Log.d("Event: ", snapshot.getValue().toString());
                        Log.d("Event: ", name);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserStreetFood.this, "No Street Vendors Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error: ", databaseError.getMessage());
                Toast.makeText(UserStreetFood.this, "Could Not Connect to the Server", Toast.LENGTH_SHORT).show();
            }
        });

        // bottom nav operations
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.restaurants_tab:
                        startActivity(new Intent(getApplicationContext(), UserActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.streetFood_tab:
                        return true;

                    case R.id.users_tab:
                        startActivity(new Intent(getApplicationContext(), UserViewUsers.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profile_tab:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
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
        if (v.getId() == R.id.addStreetVendor) {
            startActivity(new Intent(this, AddStreetFoodActivity.class));
        }
    }
}