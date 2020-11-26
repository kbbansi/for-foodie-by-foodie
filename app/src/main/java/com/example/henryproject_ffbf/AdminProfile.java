package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText first_name, surname, email;
    Button updateProfile;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    String userRole, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        reference = firebaseDatabase.getReference().child("Users");

        first_name = findViewById(R.id.first_name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);

        updateProfile = findViewById(R.id.profileUpdate);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile_tab);

        getUserProfile();
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = first_name.getText().toString();
                String surName = surname.getText().toString();
                String Email = email.getText().toString();

                // todo::: update details in firebase db

                getUserProfile();
                updateProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firstName = first_name.getText().toString();
                        String surName = surname.getText().toString();
                        String Email = email.getText().toString();


                        // todo::: update details in firebase db
                        User updateUser = new User(firstName, surName, Email, userRole, password);
                        reference.child(firebaseAuth.getCurrentUser().getUid())
                                .setValue(updateUser)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(AdminProfile.this, "Profile Updated",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AdminProfile.this, "Unable to Update Profile at this time",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });
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

    private void getUserProfile() {
        // String fName, lName, email;

        reference.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                new User();
                User user;

                user = dataSnapshot.getValue(User.class);

                String fName = user.getFirst_name();
                String lName = user.getSurname();
                String Email = user.getEmail();
                userRole = user.getUserRole();
                password = user.getPassword();

                first_name.setText(fName);
                surname.setText(lName);
                email.setText(Email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Could not connect to the database", Toast.LENGTH_LONG).show();
            }
        });
    }
}
