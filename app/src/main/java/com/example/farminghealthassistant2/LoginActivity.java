package com.example.farminghealthassistant2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_btn);
        loginEmail = findViewById(R.id.login_email_input);
        loginPassword = findViewById(R.id.login_password_input);
        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> loginUser());

    }

    private void loginUser() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();

        //Validation checks
        if (email.isEmpty()){
            loginEmail.setError("Email cannot be empty.");
            loginEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.setError("Please provide a valid email address.");
            loginEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            loginPassword.setError("Password cannot be empty.");
            loginPassword.requestFocus();
            return;
        }

        //Logging in user with email & password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "You successfully logged in.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, TakingPicturesActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Log in unsuccessful " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
