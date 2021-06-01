package com.example.cookingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookingapp.Models.Recipe;
import com.example.cookingapp.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipes;
    private boolean isFragment;

    public RecipeAdapter(Context context, List<Recipe> recipes, boolean isFragment) {
        this.context = context;
        this.recipes = recipes;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);

        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.recipeNameOutput.setText(recipe.getName());
        holder.recipeId.setText(recipe.getId());
        holder.creatorId.setText(recipe.getUserId());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeId;
        public TextView recipeNameOutput;
        public TextView creatorId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeId = itemView.findViewById(R.id.recipeId);
            recipeNameOutput = itemView.findViewById(R.id.recipeName);
            creatorId = itemView.findViewById(R.id.creatorId);
        }
    }
}
