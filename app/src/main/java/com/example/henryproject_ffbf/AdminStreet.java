package com.example.henryproject_ffbf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class AdminStreet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_street);

        Toast.makeText(this, "No Street Vendors Yet", Toast.LENGTH_SHORT).show();
    }
}
