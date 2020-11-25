package com.example.henryproject_ffbf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ViewUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        Toast.makeText(this, "No Users Created Yet", Toast.LENGTH_SHORT).show();
    }
}
