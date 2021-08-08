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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button signupBtn, loginLinkBtn;
    EditText signupEmail, signupPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupBtn = findViewById(R.id.signup_btn);
        loginLinkBtn = findViewById(R.id.login_link_btn);
        signupEmail = findViewById(R.id.signup_email_input);
        signupPassword = findViewById(R.id.signup_password_input);
        mAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(view -> signupUser());

        loginLinkBtn.setOnClickListener(view -> startActivity(new Intent(SignUpActivity.this, LoginActivity.class)));
    }

    private void signupUser() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        //Validation checks
        if (email.isEmpty()){
            signupEmail.setError("Email cannot be empty.");
            signupEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signupEmail.setError("Please provide a valid email address.");
            signupEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            signupPassword.setError("Password cannot be empty.");
            signupPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            signupPassword.setError("Password should not be less than 6 characters.");
            signupPassword.requestFocus();
            return;
        }

        //Signing up user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "You have successfully signed up.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign up unsuccessful " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
