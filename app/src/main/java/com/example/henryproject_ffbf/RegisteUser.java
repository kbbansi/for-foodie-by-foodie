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
import com.google.firebase.database.FirebaseDatabase;

public class RegisteUser extends AppCompatActivity implements View.OnClickListener {

    private TextView app_title, register;
    private EditText first_name, surname, email, password;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe_user);

        mAuth = FirebaseAuth.getInstance();

        app_title = findViewById(R.id.app_title);
        app_title.setOnClickListener(this);

        register = (Button) findViewById(R.id.registerUser);
        register.setOnClickListener(this);

        first_name = findViewById(R.id.first_name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_title:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.registerUser:
                Log.d("TAG", "Register User ");
                registerUser();
                break;
        }
    }

    private void registerUser() {
        // variables
        final String firstName = first_name.getText().toString().trim();
        final String Surname = surname.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        final String Password = password.getText().toString().trim();

        // field validations
        if (firstName.isEmpty()) {
            first_name.setError("First name is required");
            first_name.requestFocus();
            return;
        }

        if (Surname.isEmpty()) {
            surname.setError("Surname is required");
            surname.requestFocus();
            return;
        }

        if (Email.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        // further email validation
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("A valid Email address is required");
            email.requestFocus();
            return;
        }

        // password validations
        if (Password.isEmpty()) {
            password.setError("A password is required");
            password.requestFocus();
            return;
        }

        if (Password.length() < 6) {
            password.setError("Password length should be 8 characters long");
            password.requestFocus();
        }

        // progress bar
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // check if user has been successfully registered
                if (task.isSuccessful()) {
                    User user = new User(firstName, Surname, Email, Password);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(
                                    FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisteUser.this, "User Registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                                // redirect to login layout
                            } else {
                                Toast.makeText(RegisteUser.this, "Cannot Register User at this time", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisteUser.this, "Cannot Register User at this time", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
