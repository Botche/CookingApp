package com.example.cookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuthentication;

    @BindView(R.id.email) EditText emailInput;
    @BindView(R.id.password) EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    @OnClick(R.id.loginButton)
    public void onLogin() {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(email.isEmpty() == false && password.isEmpty() == false) {

            if (password.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password should be longer than 5 symbols!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuthentication.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Successfully login!",
                                        Toast.LENGTH_SHORT).show();
                                redirectToScreen(MainActivity.class);
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid credentials!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            return;
        }

        Toast.makeText(LoginActivity.this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.registerLink)
    public void redirectToToLoginScreen() {
        redirectToScreen(RegisterActivity.class);
    }

    public void redirectToScreen(Class screenClass){
        startActivity(new Intent(this, screenClass));
        finish();
    }
}