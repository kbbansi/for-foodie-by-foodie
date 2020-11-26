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

public class AdminRestaurant extends AppCompatActivity implements View.OnClickListener {

    //todo::: add list view - done
    // floating action button will open an activity for adding a new restaurant - done
    // click on list view item should open more details about item,
    FloatingActionButton floatingActionButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;

    // public static ArrayList<String> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_restaurant);

        floatingActionButton = findViewById(R.id.addNewRestaurantFAB);
        ListView listView = findViewById(R.id.listView);
        floatingActionButton.setOnClickListener(this);

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
                    Toast.makeText(AdminRestaurant.this, "No Restaurants Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error: ", databaseError.getMessage());
                Toast.makeText(AdminRestaurant.this, "Could Not Connect to the Server", Toast.LENGTH_SHORT).show();
            }
        });


        // bottom nav operations
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
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
        if (v.getId() == R.id.addNewRestaurantFAB) {
            startActivity(new Intent(this, AddRestaurant.class));
        }
    }
}
