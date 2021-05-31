package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        firebaseAuthentication = FirebaseAuth.getInstance();
        redirectToHomeScreenIfLoggedIn();
    }

    @OnClick(R.id.register)
    public void onRegister(){
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        finish();
    }

    @OnClick(R.id.logIn)
    public void onLogin(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    public void redirectToHomeScreenIfLoggedIn() {
        boolean isLoggedIn = firebaseAuthentication.getCurrentUser() != null;

        if (isLoggedIn) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }
}