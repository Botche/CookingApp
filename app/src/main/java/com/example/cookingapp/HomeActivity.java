package com.example.cookingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cookingapp.Fragments.RecipeFragment;
import com.example.cookingapp.Models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    private final String FIREBASE_REAL_TIME_DATABASE_ADDRESS = "https://cooking-app-android-default-rtdb.europe-west1.firebasedatabase.app/";

    private DatabaseReference firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REAL_TIME_DATABASE_ADDRESS).getReference();
        getAllRecipes();
    }

    public void getAllRecipes() {
        firebaseDatabase
                .child("recipes")
                .orderByValue()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Recipe> recipes = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String id = snapshot.getKey();
                            String name = snapshot.child("name").getValue().toString();
                            Number grams = Integer.parseInt(snapshot.child("grams").getValue().toString());
                            String userId = snapshot.child("userId").getValue().toString();

                            Recipe recipe = new Recipe(id, name, grams, userId);

                            recipes.add(recipe);
                        }

                        RecipeFragment selectorFragment = new RecipeFragment(recipes);

                        getSupportFragmentManager().beginTransaction().replace(R.id.recipesContainer, selectorFragment).commitAllowingStateLoss();
                    }

                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {
                        Toast.makeText(HomeActivity.this, "Something went wrong!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
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

    @OnClick(R.id.createRecipeButton)
    public void onCreateRecipe() {
        startActivity(new Intent(HomeActivity.this, CreateRecipeActivity.class));
        finish();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}