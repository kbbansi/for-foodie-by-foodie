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

public class AdminViewUsers extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        ListView listView = findViewById(R.id.listView);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setSelectedItemId(R.id.users_tab);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("Users");

        final ArrayList<String> userList = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.restaurant_cell, userList);
        listView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        // get restaurant name
                        String name = user.getFullName();
                        userList.add(name);
                        Log.d("Event: ", snapshot.getValue().toString());
                        Log.d("Event: ", name);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AdminViewUsers.this, "No Users Yet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Error: ", databaseError.getMessage());
                Toast.makeText(AdminViewUsers.this, "Could Not Connect to the Server", Toast.LENGTH_SHORT).show();
            }
        });

        // bottom nav operations
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        startActivity(new Intent(getApplicationContext(), AdminRestaurant.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.users_tab:
                        return true;
                }
                return false;
            }
        });
    }
}
