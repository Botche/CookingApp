package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        firebaseAuthentication = FirebaseAuth.getInstance();
    }
}