package com.example.cookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuthentication;

    @BindView(R.id.email) EditText emailInput;
    @BindView(R.id.password) EditText passwordInput;
    @BindView(R.id.repeatPassword) EditText repeatPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuthentication = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onSupportNavigateUp() {
        redirectToScreen(MainActivity.class);
        finish();
        return true;
    }

    @OnClick(R.id.registerButton)
    public void onRegister() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String repeatPassword = repeatPasswordInput.getText().toString();

        if(email.isEmpty() == false
                && password.isEmpty() == false
                && repeatPassword.isEmpty() == false) {

            if (password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password should be longer than 5 symbols!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.equals(repeatPassword) == false) {
                Toast.makeText(RegisterActivity.this, "Password should match repeat password!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuthentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Successful register! Please log in to continue.",
                                        Toast.LENGTH_SHORT).show();
                                redirectToScreen(MainActivity.class);
                            } else {
                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            return;
        }

        Toast.makeText(RegisterActivity.this, "Some fields are empty!",
            Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.loginLink)
    public void redirectToToLoginScreen() {
        redirectToScreen(LoginActivity.class);
    }

    public void redirectToScreen(Class screenClass){
        startActivity(new Intent(this, screenClass));
        finish();
    }
}