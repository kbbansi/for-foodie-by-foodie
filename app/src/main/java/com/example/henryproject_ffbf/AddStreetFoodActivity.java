package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStreetFoodActivity extends AppCompatActivity {

    EditText vendorName, location, description;
    Button addVendor;
    ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_street_food);

        vendorName = findViewById(R.id.name_of_vendor);
        location = findViewById(R.id.location);
        description = findViewById(R.id.vendor_description);
        progressBar = findViewById(R.id.progressBar2);
        addVendor = findViewById(R.id.add_vendor);

        //initialize db
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("StreetFood");
        firebaseAuth = FirebaseAuth.getInstance();

        addVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Vendor_name = vendorName.getText().toString().trim();
                final String Location = location.getText().toString().trim();
                final String Description = description.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                if (Vendor_name.isEmpty() || Location.isEmpty() || Description.isEmpty()) {
                    Toast.makeText(AddStreetFoodActivity.this, "Cannot Add Vendor at this moment", Toast.LENGTH_SHORT).show();
                } else {
                    StreetFood streetFood = new StreetFood(Vendor_name, Description, Location);

                    databaseReference.push().setValue(streetFood).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddStreetFoodActivity.this, "Street Vendor Added", Toast.LENGTH_SHORT).show();
                                navigateBasedOnUserRole();
                            } else {
                                Toast.makeText(AddStreetFoodActivity.this, "Cannot Add Street Vendor At this time", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });
    }

    private void navigateBasedOnUserRole() {
        databaseReference.child(firebaseAuth.getCurrentUser().getUid())
                .addValueEventListener(
                        new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userRole = dataSnapshot.child("userRole").toString();

                switch (userRole) {
                    case "admin":
                        startActivity(new Intent(AddStreetFoodActivity.this, AdminStreet.class));
                        finish();
                        break;

                    case "user":
                        startActivity(new Intent(AddStreetFoodActivity.this, UserStreetFood.class));
                        finish();
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}