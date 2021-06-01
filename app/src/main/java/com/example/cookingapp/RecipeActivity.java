package com.example.cookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.Models.Recipe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeActivity extends AppCompatActivity {
    private String FIREBASE_REAL_TIME_DATABASE_ADDRESS = "https://cooking-app-android-default-rtdb.europe-west1.firebasedatabase.app/";
    private FirebaseAuth firebaseAuthentication;

    @BindView(R.id.recipeName) TextView recipeName;
    @BindView(R.id.recipeGrams) TextView grams;
    @BindView(R.id.recipeId) TextView recipeId;
    @BindView(R.id.creatorId) TextView creatorId;

    @BindView(R.id.editRecipeButton) Button editButton;
    @BindView(R.id.deleteRecipeButton) Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        String a = recipe.getUserId();
        recipeId.setText(recipe.getId());
        recipeName.setText(recipe.getName());
        grams.setText(recipe.getGrams().toString());
        creatorId.setText(recipe.getUserId());

        firebaseAuthentication = FirebaseAuth.getInstance();

        if (firebaseAuthentication.getCurrentUser().getUid().equals(creatorId.getText()) == false) {
            editButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.deleteRecipeButton)
    public void onClickDelete() {
        if (firebaseAuthentication.getCurrentUser().getUid().equals(creatorId.getText()) == false) {
            Toast.makeText(RecipeActivity.this, "Forbidden!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        FirebaseDatabase.getInstance(FIREBASE_REAL_TIME_DATABASE_ADDRESS).getReference()
                .child("recipes")
                .child(recipeId.getText() .toString())
                .removeValue();

        startActivity(new Intent(RecipeActivity.this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.editRecipeButton)
    public void onClickEdit() {
        if (firebaseAuthentication.getCurrentUser().getUid().equals(creatorId.getText()) == false) {
            Toast.makeText(RecipeActivity.this, "Forbidden!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Intent intent = new Intent(RecipeActivity.this, EditRecipeActivity.class);
        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        intent.putExtra("Recipe", recipe);

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(RecipeActivity.this, MainActivity.class);
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
        Intent intent = new Intent(RecipeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}