package com.example.henryproject_ffbf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class AddRestaurant extends AppCompatActivity implements View.OnClickListener {
    private EditText restaurantName, url, description;
    private Button addRestaurant;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restaurantName = findViewById(R.id.name_of_restaurant);
        url = findViewById(R.id.restaurant_url);
        description = findViewById(R.id.restaurant_description);
        addRestaurant = findViewById(R.id.add_restaurant);
        progressBar = findViewById(R.id.progressBar2);

        //initialize db
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Restaurants");

        addRestaurant.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_restaurant) {
            addNewRestaurant();
        }
    }

    private void addNewRestaurant() {
        final String restaurant_name = restaurantName.getText().toString().trim();
        final String url_link = url.getText().toString().trim();
        final String restaurant_description = description.getText().toString().trim();

        // field validations
        progressBar.setVisibility(View.VISIBLE);
        if (restaurant_name.isEmpty() || restaurant_description.isEmpty() || url_link.isEmpty() || !Patterns.WEB_URL.matcher(url_link).matches()) {
            Toast.makeText(AddRestaurant.this, "Cannot Add a Restaurant at this time", Toast.LENGTH_SHORT).show();
        } else {
            final Restaurants restaurant = new Restaurants(restaurant_name, restaurant_description, url_link);
            databaseReference
                    .push().setValue(restaurant)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Action: ", restaurant.getDescription());
                                Toast.makeText(AddRestaurant.this, "Restaurant Added", Toast.LENGTH_LONG).show();
                                // start activity to go back to list view
                                startActivity(new Intent(getApplicationContext(), AdminRestaurant.class));
                                finish();
                            } else {
                                Log.d("Event: ", task.getException().getMessage());
                                Toast.makeText(AddRestaurant.this, "Cannot Add a Restaurant at this time", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
