package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ListView listView = findViewById(R.id.listView);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.restaurants_tab);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("Restaurants");

        final ArrayList<String> restaurantList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.restaurant_cell, restaurantList);
        listView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Restaurants restaurant = snapshot.getValue(Restaurants.class);

                        // get restaurant name
                        String name = restaurant.getName();
                        restaurantList.add(name);
                        Log.d("Event: ", snapshot.getValue().toString());
                        Log.d("Event: ", name);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(UserActivity.this, "No Restaurants Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error: ", databaseError.getMessage());
                Toast.makeText(UserActivity.this, "Could Not Connect to the Server", Toast.LENGTH_SHORT).show();
            }
        });

        // bottom nav operations
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.restaurants_tab:
                        finish();
                        return true;

                    case R.id.streetFood_tab:
                        startActivity(new Intent(getApplicationContext(), UserStreetFood.class));
                        overridePendingTransition(0, 0);
                        finish();
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
}
