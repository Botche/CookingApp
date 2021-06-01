package com.example.cookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingapp.Models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditRecipeActivity extends AppCompatActivity {
    private String FIREBASE_REAL_TIME_DATABASE_ADDRESS = "https://cooking-app-android-default-rtdb.europe-west1.firebasedatabase.app/";

    @BindView(R.id.name) EditText recipeNameInput;
    @BindView(R.id.grams) EditText gramsInput;
    @BindView(R.id.description) EditText descriptionInput;
    @BindView(R.id.recipeId) TextView recipeId;
    @BindView(R.id.creatorId) TextView creatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        recipeNameInput.setText(recipe.getName());
        gramsInput.setText(recipe.getGrams().toString());
        descriptionInput.setText(recipe.getDescription());
        recipeId.setText(recipe.getId());
        creatorId.setText(recipe.getUserId());
    }

    @OnClick(R.id.editRecipeButton)
    public void onClickEdit() {

        String name = recipeNameInput.getText().toString();
        String grams = gramsInput.getText().toString();
        String description = descriptionInput.getText().toString();

        if (name.equals("") || grams.equals("") || description.equals("")) {
            Toast.makeText(EditRecipeActivity.this, "Values cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference ref = FirebaseDatabase
                .getInstance(FIREBASE_REAL_TIME_DATABASE_ADDRESS)
                .getReference("recipes")
                .child(recipeId.getText().toString());

        ref.child("name").setValue(name);
        ref.child("grams").setValue(grams);
        ref.child("description").setValue(description);

        Toast.makeText(EditRecipeActivity.this, "Successfully edit recipe " + name, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditRecipeActivity.this, RecipeActivity.class);
        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        recipe.setName(name);
        recipe.setGrams(Double.parseDouble(grams));
        recipe.setDescription(description);

        intent.putExtra("Recipe", recipe);

        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(EditRecipeActivity.this, RecipeActivity.class);
        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
        intent.putExtra("Recipe", recipe);
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
        Intent intent = new Intent(EditRecipeActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}