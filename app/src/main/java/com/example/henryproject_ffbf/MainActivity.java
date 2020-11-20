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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText email, password;
    private Button signin;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        signin = findViewById(R.id.signin);
        signin.setOnClickListener(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisteUser.class));
                break;

            case R.id.signin:
                login();
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser() != null){
//            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, UserProfile.class));
//            finish();
//        }
//    }

    private void login() {
        // login function
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        // perform validations
        if (Email.isEmpty()) {
            email.setError("Please Enter your email to Log In");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please Enter a Valid Email Address");
            email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Please Enter your password to Log In");
            password.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            password.setError("Password length should be 8 characters long");
            password.requestFocus();
            return;
        }

        // progress bar
        progressBar.setVisibility(View.VISIBLE);

        // firebase login auth
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // redirect to next activity
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);

                            // perform user role check here
                            final String userID = mAuth.getCurrentUser().getUid();
                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String userRole = dataSnapshot.child(userID).child("userRole").getValue(String.class);
                                    Log.d("TAG", "The value is :" + userRole);
                                    Toast.makeText(MainActivity.this, "Logged in as " + userRole, Toast.LENGTH_SHORT).show();
                                    displayUserActivity(userRole);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            // startActivity(new Intent(MainActivity.this, UserProfile.class));
                            // finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to Login User at This Time", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void displayUserActivity(String userRole) {
        switch(userRole){
            case "admin":
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();
                break;

            case "critic":
                startActivity(new Intent(getApplicationContext(), CriticActivity.class));
                finish();
                break;

            case "user":
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                finish();
                break;
        }
    }
}
