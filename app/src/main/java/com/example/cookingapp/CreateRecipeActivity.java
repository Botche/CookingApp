package com.example.cookingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecipeActivity extends AppCompatActivity {
    private String FIREBASE_REAL_TIME_DATABASE_ADDRESS = "https://cooking-app-android-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference firebaseDatabase;
    private FirebaseAuth firebaseAuthentication;

    @BindView(R.id.name) EditText recipeNameInput;
    @BindView(R.id.grams) EditText gramsInput;
    @BindView(R.id.description) EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REAL_TIME_DATABASE_ADDRESS).getReference();
        firebaseAuthentication = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.submitRecipeButton)
    public void onCreateRecipe(){
        String recipeName = recipeNameInput.getText().toString();
        String grams = gramsInput.getText().toString();
        String description = descriptionInput.getText().toString();

        if (recipeName.equals("") || grams.equals("") || description.equals("")) {
            Toast.makeText(CreateRecipeActivity.this, "Values cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = firebaseAuthentication.getCurrentUser().getUid();
        Map<String, Object> recipe = new HashMap<>();
        recipe.put("name", recipeName);
        recipe.put("grams", grams);
        recipe.put("description", description);
        recipe.put("userId", userId);

        String recipeId = UUID.randomUUID().toString();
        firebaseDatabase.child("recipes").child(recipeId).setValue(recipe);

        Toast.makeText(CreateRecipeActivity.this, "Recipe added successfully!",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(CreateRecipeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(CreateRecipeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(CreateRecipeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}